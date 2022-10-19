package com.cn.dto;

import com.cn.entity.OrderDetail;
import com.cn.entity.Orders;
import lombok.Data;

import java.util.List;
@Data
public class OrdersDto extends Orders {
    private List<OrderDetail> orderDetails;
}
