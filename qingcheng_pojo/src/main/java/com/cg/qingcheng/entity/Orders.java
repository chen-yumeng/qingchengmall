package com.cg.qingcheng.entity;

import com.cg.qingcheng.pojo.order.Order;
import com.cg.qingcheng.pojo.order.OrderItem;

import java.io.Serializable;
import java.util.List;

/**
 * @program: qingcheng_parent->Orders
 * @description:
 * @author: cg
 * @create: 2020-02-26 16:04
 **/

public class Orders implements Serializable {

    private Order order;

    private List<OrderItem> orderItems;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
