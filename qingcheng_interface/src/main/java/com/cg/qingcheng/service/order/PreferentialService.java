package com.cg.qingcheng.service.order;

import com.cg.qingcheng.entity.PageResult;
import com.cg.qingcheng.pojo.order.Preferential;

import java.util.List;
import java.util.Map;

/**
 * preferential业务逻辑层
 */
public interface PreferentialService {

    public List<Preferential> findAll();

    public PageResult<Preferential> findPage(int page, int size);

    public List<Preferential> findList(Map<String, Object> searchMap);

    public PageResult<Preferential> findPage(Map<String, Object> searchMap, int page, int size);

    public Preferential findById(Integer id);

    public void add(Preferential preferential);

    public void update(Preferential preferential);

    public void delete(Integer id);

}
