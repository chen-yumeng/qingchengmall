package com.cg.qingcheng.service.goods;

import com.cg.qingcheng.entity.PageResult;
import com.cg.qingcheng.pojo.goods.Spec;

import java.util.List;
import java.util.Map;

/**
 * spec业务逻辑层
 */
public interface SpecService {

    public List<Spec> findAll();

    public PageResult<Spec> findPage(int page, int size);

    public List<Spec> findList(Map<String, Object> searchMap);

    public PageResult<Spec> findPage(Map<String, Object> searchMap, int page, int size);

    public Spec findById(Integer id);

    public void add(Spec spec);

    public void update(Spec spec);

    public void delete(Integer id);

    List<Spec> findSpecByTemplateId(String id);
}
