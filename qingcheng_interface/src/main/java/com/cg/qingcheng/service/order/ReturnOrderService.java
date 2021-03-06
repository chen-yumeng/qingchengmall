package com.cg.qingcheng.service.order;

import com.cg.qingcheng.entity.PageResult;
import com.cg.qingcheng.pojo.order.ReturnOrder;

import java.util.List;
import java.util.Map;

/**
 * returnOrder业务逻辑层
 */
public interface ReturnOrderService {

    public List<ReturnOrder> findAll();

    public PageResult<ReturnOrder> findPage(int page, int size);

    public List<ReturnOrder> findList(Map<String, Object> searchMap);

    public PageResult<ReturnOrder> findPage(Map<String, Object> searchMap, int page, int size);

    public ReturnOrder findById(Long id);

    public void add(ReturnOrder returnOrder);

    public void update(ReturnOrder returnOrder);

    public void delete(Long id);

}
