package com.cg.qingcheng.service.order;

import com.cg.qingcheng.entity.PageResult;
import com.cg.qingcheng.pojo.order.OrderItem;

import java.util.List;
import java.util.Map;

/**
 * orderItem业务逻辑层
 */
public interface OrderItemService {

    public List<OrderItem> findAll();

    public PageResult<OrderItem> findPage(int page, int size);

    public List<OrderItem> findList(Map<String, Object> searchMap);

    public PageResult<OrderItem> findPage(Map<String, Object> searchMap, int page, int size);

    public OrderItem findById(String id);

    public void add(OrderItem orderItem);

    public void update(OrderItem orderItem);

    public void delete(String id);

}
