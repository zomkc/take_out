package com.cn.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cn.common.BaseContext;
import com.cn.common.R;
import com.cn.entity.ShoppingCart;
import com.cn.service.ShoppingCartService;
import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.ValueConverter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
//@CrossOrigin
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    //查看购物车
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(String userId){
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,userId)
                .orderByAsc(ShoppingCart::getCreateTime);

        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);

        return R.success(list);
    }

    //添加购物车
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        //设置用户id
//        Long currentId = BaseContext.getCurrentId();
//        shoppingCart.setUserId(currentId);

        //查询当前菜品是否在购物车中
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,shoppingCart.getUserId());
                            //.eq(ShoppingCart::getDishFlavor,shoppingCart.getDishFlavor())//口味是否重复
        if (shoppingCart.getDishId() != null){
            queryWrapper.eq(ShoppingCart::getDishId,shoppingCart.getDishId());//添加到购物车的是菜品
        }else {
            //添加到购物车的是套餐
            queryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }
        ShoppingCart one = shoppingCartService.getOne(queryWrapper);

        if (one != null){
            //如果已经存在,在当前的数量上+1
            Integer number = one.getNumber();
            one.setNumber(number+1);
            shoppingCartService.updateById(one);
        }else {
            //如果不存在,则添加到购物车,数量默认1
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            one = shoppingCart;
        }

        return R.success(one);
    }

    //清空购物车
    @PostMapping("/clean")
    public R<String> clean(@RequestBody ShoppingCart shoppingCart){
//        Long id = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,shoppingCart.getUserId());

        shoppingCartService.remove(queryWrapper);

        return R.success("清空购物车成功");
    }

    //减少数量
    @PostMapping("/sub")
    public R<String> sub(@RequestBody ShoppingCart shoppingCart){
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getDishId,shoppingCart.getDishId());

        ShoppingCart one = shoppingCartService.getOne(queryWrapper);

        if (one == null){
            LambdaQueryWrapper<ShoppingCart> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
            one = shoppingCartService.getOne(queryWrapper1);
        }

        if (one.getNumber() > 1){
            Integer number = one.getNumber();
            one.setNumber(number-1);
            shoppingCartService.updateById(one);
        }else {
            shoppingCartService.removeById(one.getId());
        }

        return R.success("减少成功");
    }



}
