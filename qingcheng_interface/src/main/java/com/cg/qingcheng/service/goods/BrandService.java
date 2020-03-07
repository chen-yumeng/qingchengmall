package com.cg.qingcheng.service.goods;

import com.cg.qingcheng.entity.PageResult;
import com.cg.qingcheng.pojo.goods.Brand;

import java.util.List;
import java.util.Map;

/**
 * @program: qingcheng_parent->BrandService
 * @description:
 * @author: cg
 * @create: 2020-02-21 14:18
 **/

public interface BrandService {

    public List<Brand> findAll();

    public PageResult<Brand> findByPage(int page, int pageSize);

    public List<Brand> findListByNameAndLetter(Map<String, Object> searchMap);

    public PageResult<Brand> findByPageAndNameAndLetter(int page, int pageSize, Map<String, Object> searchMap);

    public Brand findById(Integer id);

    public void addBrand(Brand brand);

    public void updateBrande(Brand brand);

    public void deleteById(Integer id);

}
