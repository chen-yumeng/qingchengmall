package com.cg.qingcheng.service.order;

import com.cg.qingcheng.entity.Orders;
import com.cg.qingcheng.entity.PageResult;
import com.cg.qingcheng.pojo.order.Order;

import java.util.List;
import java.util.Map;

/**
 * order业务逻辑层
 */
public interface OrderService {

    public List<Order> findAll();

    public PageResult<Order> findPage(int page, int size);

    public List<Order> findList(Map<String, Object> searchMap);

    public PageResult<Order> findPage(Map<String, Object> searchMap, int page, int size);

    public Order findById(String id);

    public void add(Order order);

    public void update(Order order);

    public void delete(String id);

    public void save(Orders orders);

    public Orders findOrderById(String id);

}
