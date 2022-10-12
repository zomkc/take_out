package com.cn.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.entity.Orders;

public interface OrderService extends IService<Orders> {

    //用户下单
    public void submit(Orders orders);

}
