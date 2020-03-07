package com.cg.qingcheng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cg.qingcheng.dao.ParaMapper;
import com.cg.qingcheng.dao.TemplateMapper;
import com.cg.qingcheng.entity.PageResult;
import com.cg.qingcheng.pojo.goods.Para;
import com.cg.qingcheng.pojo.goods.Template;
import com.cg.qingcheng.service.goods.ParaService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * @program: qingcheng_parent->ParaServiceImpl
 * @description:
 * @author: cg
 * @create: 2020-02-21 14:21
 **/
@Service(interfaceClass = ParaService.class)
public class ParaServiceImpl implements ParaService {

    @Autowired
    private ParaMapper paraMapper;

    @Autowired
    private TemplateMapper templateMapper;

    /**
     * 返回全部记录
     *
     * @return
     */
    @Override
    public List<Para> findAll() {
        return paraMapper.selectAll();
    }

    /**
     * 分页查询
     *
     * @param page 页码
     * @param size 每页记录数
     * @return 分页结果
     */
    @Override
    public PageResult<Para> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        Page<Para> paras = (Page<Para>) paraMapper.selectAll();
        return new PageResult<Para>(paras.getTotal(), paras.getResult());
    }

    /**
     * 条件查询
     *
     * @param searchMap 查询条件
     * @return
     */
    @Override
    public List<Para> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return paraMapper.selectByExample(example);
    }

    /**
     * 分页+条件查询
     *
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageResult<Para> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(searchMap);
        Page<Para> paras = (Page<Para>) paraMapper.selectByExample(example);
        return new PageResult<Para>(paras.getTotal(), paras.getResult());
    }

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    @Override
    public Para findById(Integer id) {
        return paraMapper.selectByPrimaryKey(id);
    }

    /**
     * 新增
     *
     * @param para
     */
    @Override
    @Transactional
    public void add(Para para) {
        paraMapper.insert(para);
        //将模板中的规格数量+1
        Template template = templateMapper.selectByPrimaryKey(para.getTemplateId());
        template.setParaNum(template.getParaNum() + 1);
        templateMapper.updateByPrimaryKeySelective(template);
    }

    /**
     * 修改
     *
     * @param para
     */
    @Override
    public void update(Para para) {
        paraMapper.updateByPrimaryKeySelective(para);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    @Transactional
    public void delete(Integer id) {
        //将模板中的规格数量减一
        Para para = paraMapper.selectByPrimaryKey(id);
        Template template = templateMapper.selectByPrimaryKey(para.getTemplateId());
        template.setParaNum(template.getParaNum() - 1);
        templateMapper.updateByPrimaryKeySelective(template);
        paraMapper.deleteByPrimaryKey(id);
    }

    public List<Para> findParaByTemplateId(String id) {
        Example example = new Example(Para.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("templateId", id);
        List<Para> paras = paraMapper.selectByExample(example);
        return paras;
    }

    /**
     * 构建查询条件
     *
     * @param searchMap
     * @return
     */
    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(Para.class);
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            // 名称
            if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
                criteria.andLike("name", "%" + searchMap.get("name") + "%");
            }
            // 选项
            if (searchMap.get("options") != null && !"".equals(searchMap.get("options"))) {
                criteria.andLike("options", "%" + searchMap.get("options") + "%");
            }

            // id
            if (searchMap.get("id") != null) {
                criteria.andEqualTo("id", searchMap.get("id"));
            }
            // 排序
            if (searchMap.get("seq") != null) {
                criteria.andEqualTo("seq", searchMap.get("seq"));
            }
            // 模板ID
            if (searchMap.get("templateId") != null) {
                criteria.andEqualTo("templateId", searchMap.get("templateId"));
            }

        }
        return example;
    }

}
