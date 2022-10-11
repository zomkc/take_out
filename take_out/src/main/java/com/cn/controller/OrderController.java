package com.cn.controller;

import com.cn.common.R;
import com.cn.entity.Orders;
import com.cn.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //用户下单
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        //调用微信支付接口

        //支付成功后添加数据库,清空购物车
        orderService.submit(orders);
        return R.success("下单成功");
    }


}
