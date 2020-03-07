package com.cg.qingcheng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cg.qingcheng.dao.BrandMapper;
import com.cg.qingcheng.entity.PageResult;
import com.cg.qingcheng.pojo.goods.Brand;
import com.cg.qingcheng.service.goods.BrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * @program: qingcheng_parent->BrandServiceImpl
 * @description:
 * @author: cg
 * @create: 2020-02-21 14:21
 **/
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Brand> findAll() {
        return brandMapper.selectAll();
    }

    @Override
    public PageResult<Brand> findByPage(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        PageInfo<Brand> pageInfo = new PageInfo<>(brandMapper.selectAll());
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public List<Brand> findListByNameAndLetter(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return brandMapper.selectByExample(example);
    }

    @Override
    public PageResult<Brand> findByPageAndNameAndLetter(int page, int pageSize, Map<String, Object> searchMap) {
        PageHelper.startPage(page, pageSize);
        Example example = createExample(searchMap);
        PageInfo<Brand> pageInfo = new PageInfo<> (brandMapper.selectByExample(example));
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public Brand findById(Integer id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addBrand(Brand brand) {
        brandMapper.insertSelective(brand);
    }

    @Override
    public void updateBrande(Brand brand) {
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public void deleteById(Integer id) {
        brandMapper.deleteByPrimaryKey(id);
    }

    private Example createExample(Map<String, Object> searchMap) {
        /*
        "name": 品牌名称,
        "letter": 品牌的首字母
        */
        String KEY_NAME = "name";
        String KEY_LETTER = "letter";
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        if (searchMap != null) {
            if (searchMap.get(KEY_NAME) != null && !"".equals(searchMap.get(KEY_NAME))) {
                criteria.andLike(KEY_NAME, "%" + searchMap.get(KEY_NAME) + "%");
            }
            if (searchMap.get(KEY_LETTER) != null && !"".equals(searchMap.get(KEY_LETTER))) {
                criteria.andEqualTo(KEY_LETTER, searchMap.get(KEY_LETTER));
            }
        }
        return example;
    }
}
