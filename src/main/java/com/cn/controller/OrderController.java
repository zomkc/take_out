package com.cn.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.common.R;
import com.cn.dto.OrdersDto;
import com.cn.entity.OrderDetail;
import com.cn.entity.Orders;
import com.cn.entity.ShoppingCart;
import com.cn.service.OrderDetailService;
import com.cn.service.OrderService;
import com.cn.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    //用户下单
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        //调用微信支付接口

        //支付成功后添加数据库,清空购物车
        orderService.submit(orders);
        return R.success("下单成功");
    }

    //用户分页查询订单
    @GetMapping("/userPage")
    public R<Page> userPage(int page, int pageSize, HttpSession session) {
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        Page<OrdersDto> pageInfoDto = new Page<>();
        //获取用户id
        Long userId = (Long) session.getAttribute("user");
        //根据id查询分页数据
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getUserId,userId)
                //根据订单时间排序
                .orderByDesc(Orders::getOrderTime);
        orderService.page(pageInfo, wrapper);
        //将pageInfo的数据拷贝到pageInfoDto
        BeanUtils.copyProperties(pageInfo,pageInfoDto,"records");

        List<Orders> records = pageInfo.getRecords();
        List<OrdersDto> ordersDtoList = records.stream().map(i -> {
            OrdersDto ordersDto = new OrdersDto();
            BeanUtils.copyProperties(i,ordersDto);
            //订单id
            Long id = i.getId();
            //根据订单id查询订单菜品
            LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(OrderDetail::getOrderId,id);
            List<OrderDetail> list = orderDetailService.list(queryWrapper);
            ordersDto.setOrderDetails(list);

            return ordersDto;
        }).collect(Collectors.toList());
        //查询用户订单菜品
        pageInfoDto.setRecords(ordersDtoList);

        return R.success(pageInfoDto);
    }


    //分页查询订单
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize,Long number,String beginTime,String endTime) {
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        Page<OrdersDto> pageInfoDto = new Page<>();
        LambdaQueryWrapper<Orders> ordersLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (beginTime != null) {
            //大于等于beginTime时间的订单
            ordersLambdaQueryWrapper.ge(Orders::getOrderTime, beginTime)
                    //小于等于endTime时间的订单
                    .le(Orders::getOrderTime, endTime);
                }
        if (number != null){
            ordersLambdaQueryWrapper.eq(Orders::getNumber,number);
        }
        orderService.page(pageInfo,ordersLambdaQueryWrapper);
        //将pageInfo的数据拷贝到pageInfoDto
        BeanUtils.copyProperties(pageInfo,pageInfoDto,"records");

        List<Orders> records = pageInfo.getRecords();
        List<OrdersDto> ordersDtoList = records.stream().map(i -> {
            OrdersDto ordersDto = new OrdersDto();
            BeanUtils.copyProperties(i,ordersDto);
            //订单id
            Long id = i.getId();
            //根据订单id查询订单菜品
            LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(OrderDetail::getOrderId,id);
            List<OrderDetail> list = orderDetailService.list(queryWrapper);
            ordersDto.setOrderDetails(list);

            return ordersDto;
        }).collect(Collectors.toList());
        //查询用户订单菜品
        pageInfoDto.setRecords(ordersDtoList);

        return R.success(pageInfoDto);
    }

    //修改订单状态
    @PutMapping
    public R<String> put(@RequestBody Orders orders){
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getId,orders.getId());
        orderService.update(orders,wrapper);

        return R.success("修改成功");
    }

    //再来一单
    @PostMapping("/again")
    public R<String> again(@RequestBody Orders orders,HttpSession session){
        //清空购物车
        Long userId = (Long) session.getAttribute("user");
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,userId);
        shoppingCartService.remove(queryWrapper);

        LambdaQueryWrapper<OrderDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderDetail::getOrderId,orders.getId());
        List<OrderDetail> list = orderDetailService.list(wrapper);
        list.forEach(i -> {
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setName(i.getName());//菜品名字
            shoppingCart.setImage(i.getImage());//菜品图片
            shoppingCart.setNumber(i.getNumber());//菜品数量
            shoppingCart.setUserId(userId);//用户id
            shoppingCart.setAmount(i.getAmount());//金额
            shoppingCart.setCreateTime(LocalDateTime.now());
            //菜品/套餐 id
            if (i.getDishId() != null){
                shoppingCart.setDishId(i.getDishId());
            }else {
                shoppingCart.setSetmealId(i.getSetmealId());
            }
            shoppingCartService.save(shoppingCart);
        });


        return R.success("再来一单");
    }

}
