package com.cg.qingcheng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.cg.qingcheng.dao.*;
import com.cg.qingcheng.entity.Goods;
import com.cg.qingcheng.entity.PageResult;
import com.cg.qingcheng.pojo.goods.*;
import com.cg.qingcheng.service.goods.SpuService;
import com.cg.qingcheng.utils.IdWorker;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @program: qingcheng_parent->SpuServiceImpl
 * @description:
 * @author: cg
 * @create: 2020-02-21 14:21
 **/
@Service(interfaceClass = SpuService.class)
public class SpuServiceImpl implements SpuService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    @Autowired
    private SpuCheckMapper spuCheckMapper;

    @Autowired
    private SpuLogMapper spuLogMapper;

    /**
     * 返回全部记录
     *
     * @return
     */
    @Override
    public List<Spu> findAll() {
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDelete", "0");
        return spuMapper.selectByExample(example);
    }

    /**
     * 分页查询
     *
     * @param page 页码
     * @param size 每页记录数
     * @return 分页结果
     */
    @Override
    public PageResult<Spu> findPage(int page, int size) {
        PageHelper.startPage(page, size);
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDelete", "0");
        Page<Spu> spus = (Page<Spu>) spuMapper.selectByExample(example);
        return new PageResult<Spu>(spus.getTotal(), spus.getResult());
    }

    /**
     * 条件查询
     *
     * @param searchMap 查询条件
     * @return
     */
    @Override
    public List<Spu> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return spuMapper.selectByExample(example);
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
    public PageResult<Spu> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = createExample(searchMap);
        Page<Spu> spus = (Page<Spu>) spuMapper.selectByExample(example);
        return new PageResult<Spu>(spus.getTotal(), spus.getResult());
    }

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    @Override
    public Spu findById(String id) {
        return spuMapper.selectByPrimaryKey(id);
    }

    /**
     * 新增
     *
     * @param spu
     */
    @Override
    public void add(Spu spu) {
        spuMapper.insert(spu);
    }

    /**
     * 修改
     *
     * @param spu
     */
    @Override
    public void update(Spu spu) {
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * 逻辑删除
     *
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        spu.setIsDelete("1");
        spuMapper.updateByPrimaryKeySelective(spu);
        log(id, "isDelete", "0", "1", null);
    }

    /**
     * 物理删除
     *
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int completeDelete(String id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if ("1".equals(spu.getIsDelete())) {
            int i = spuMapper.deleteByPrimaryKey(id);
            Example example = new Example(SpuLog.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("spuId", id);
            spuLogMapper.deleteByExample(example);
            return i;
        } else {
            return 0;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int recover(String id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if ("1".equals(spu.getIsDelete())) {
            spu.setIsDelete("0");
            int i = spuMapper.updateByPrimaryKeySelective(spu);
            if (i != 0) {
                log(id, "isDelete", "1", "0", null);
            }
            return i;
        } else {
            return 0;
        }
    }

    /**
     * 保存商品 Spu+Sku列表
     *
     * @param goods
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveGoods(Goods goods) {
        //保存一个spu的信息
        Spu spu = goods.getSpu();

        if (spu.getId() == null) {
            //新增
            //Id
            spu.setId(idWorker.nextId() + "");
            spuMapper.insertSelective(spu);
        } else {
            //修改
            //删除原来的sku列表
            Example example = new Example(Sku.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("spuId", spu.getId());
            skuMapper.deleteByExample(example);
            Spu oldSpu = spuMapper.selectByPrimaryKey(spu.getId());
            //执行spu修改
            spuMapper.updateByPrimaryKeySelective(spu);
            editLog(spu.getId(), oldSpu, spu);
        }

        //时间
        Date date = new Date();
        //分类对象
        Category category = categoryMapper.selectByPrimaryKey(spu.getCategory3Id());

        //保存sku列表信息
        List<Sku> skuList = goods.getSkuList();
        for (Sku sku : skuList) {

            if (sku.getId() == null) {
                //新增
                //Id
                sku.setId(idWorker.nextId() + "");
                //创建时间
                sku.setCreateTime(date);
            }

            //构建SKU名称，采用SPU+规格值组装
            if (sku.getSpec() == null || "".equals(sku.getSpec())) {
                sku.setSpec("{}");
            }

            //设置spuId
            sku.setSpuId(spu.getId());
            //sku名称 = spu名称+规格列表
            String name = spu.getName();
            //sku.getSpec()  {"颜色":"哄","机身内存":"64G"}
            Map<String, String> specMap = JSON.parseObject(sku.getSpec(), Map.class);
            for (String value : specMap.values()) {
                name += value + " ";
            }
            //名称
            sku.setName(name);
            //品牌名称
            sku.setBrandName(brandMapper.selectByPrimaryKey(spu.getBrandId()).getName());
            //修改时间
            sku.setUpdateTime(date);
            //分类id
            sku.setCategoryId(category.getId());
            //分类名称
            sku.setCategoryName(category.getName());
            //评论数初始值
            sku.setCommentNum(0);
            //销售数量初始值
            sku.setSaleNum(0);

            skuMapper.insertSelective(sku);
        }

        //建立品牌和分类的关联
        CategoryBrand categoryBrand = new CategoryBrand();
        categoryBrand.setCategoryId(spu.getCategory3Id());
        categoryBrand.setBrandId(spu.getBrandId());
        //查询是否已存在
        int count = categoryBrandMapper.selectCount(categoryBrand);
        if (count == 0) {
            categoryBrandMapper.insert(categoryBrand);
        }

    }

    @Override
    public Goods findGoodsById(String id) {
        //查询spu
        Spu spu = spuMapper.selectByPrimaryKey(id);
        //查询sku
        Example example = new Example(Sku.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("spuId", id);
        List<Sku> skuList = skuMapper.selectByExample(example);
        //封装
        Goods goods = new Goods();
        goods.setSpu(spu);
        goods.setSkuList(skuList);
        return goods;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void audit(String id, String status, String message) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        //修改状态 审核状态和上架状态
        spu.setId(id);
        spu.setStatus(status);
        if ("1".equals(status)) {
            //审核通过
            //修改上架状态
            spu.setIsMarketable("1");
        }
        spuMapper.updateByPrimaryKeySelective(spu);

        //记录商品审核记录
        SpuCheck spuCheck = new SpuCheck();
        spuCheck.setSpuId(id);
        spuCheck.setCheckName(null);
        spuCheck.setStatus(status);
        spuCheck.setDetails(message);
        spuCheck.setCheckTime(new Date());
        spuCheckMapper.insertSelective(spuCheck);

        //记录商品修改日志 修改之后
        log(id, "status", "0", status, null);
        log(id, "isMarketable", "0", "1", null);

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void pull(String id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        //修改状态
        spu.setIsMarketable("0");
        spuMapper.updateByPrimaryKeySelective(spu);

        //记录商品修改日志 修改之后
        log(id, "isMarketable", "1", "0", null);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void put(String id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        //如果未通过审核
        if (!"1".equals(spu.getStatus())) {
            throw new RuntimeException("此商品未通过审核!");
        }
        //修改状态
        spu.setIsMarketable("1");
        spuMapper.updateByPrimaryKeySelective(spu);

        //记录商品修改日志 修改之后
        log(id, "isMarketable", "0", "1", null);
    }

    @Override
    public int putMany(String[] ids) {
        Spu spu = new Spu();
        //修改状态
        spu.setIsMarketable("1");

        Example example = createExample(ids, "1");
        //修改前记录
        List<Spu> spus = spuMapper.selectByExample(example);

        //更新
        int i = spuMapper.updateByExampleSelective(spu, example);

        if (i != 0) {
            for (Spu spu1 : spus) {
                log(spu1.getId(), "isMarketable", "0", "1", null);
            }
        }
        return i;
    }

    @Override
    public int pullMany(String[] ids) {
        Spu spu = new Spu();
        //修改状态
        spu.setIsMarketable("0");

        Example example = createExample(ids, "0");
        //修改前记录
        List<Spu> spus = spuMapper.selectByExample(example);

        //更新
        int i = spuMapper.updateByExampleSelective(spu, example);

        if (i != 0) {
            for (Spu spu1 : spus) {
                log(spu1.getId(), "isMarketable", "1", "0", null);
            }
        }
        return i;
    }

    @Override
    public Map<String, Integer> getTotal() {
        Map map = new HashMap();
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isMarketable", "0");
        criteria.andEqualTo("isDelete", "0");
        //未上架
        int size = spuMapper.selectByExample(example).size();

        //总-未上架=已上架    未上架-待审核=未通过
        Example example1 = new Example(Spu.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("status", "2");
        criteria1.andEqualTo("isDelete", "0");
        //未通过
        int size1 = spuMapper.selectByExample(example1).size();

        Example example2 = new Example(Spu.class);
        Example.Criteria criteria2 = example2.createCriteria();
        criteria2.andEqualTo("isDelete", "0");

        map.put("noMarketable", size);
        map.put("notPass", size1);
        map.put("notCheck", size - size1);
        map.put("total", spuMapper.selectByExample(example2).size());
        return map;
    }

    @Override
    public List<SpuCheck> getCheckLog(String id) {
        Example example = new Example(SpuCheck.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("spuId", id);
        return spuCheckMapper.selectByExample(example);
    }

    @Override
    public List<SpuLog> getSpuLog(String id) {
        Example example = new Example(SpuLog.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("spuId", id);
        return spuLogMapper.selectByExample(example);
    }

    private Example createExample(String[] ids, String index) {
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", Arrays.asList(ids));
        //判断是上架还是下架
        if ("1".equals(index)) {
            //上架
            //必须是下架的才符合条件被上架
            criteria.andEqualTo("isMarketable", "0");
        } else if ("0".equals(index)) {
            //下架
            //必须是上架的才符合条件被下架
            criteria.andEqualTo("isMarketable", "1");
        }
        //必须是审核通过的
        criteria.andEqualTo("status", "1");
        return example;
    }

    /**
     * 记录修改日志
     *
     * @param spuId    spuId
     * @param column   列名
     * @param oldValue 修改前的值
     * @param newValue 修改后的值
     * @param operator 操作人员
     */
    private void log(String spuId, String column, Object oldValue, Object newValue, String operator) {
        SpuLog spuLog = new SpuLog();
        spuLog.setSpuId(spuId);
        System.out.println("修改前的值" + oldValue.toString());
        System.out.println("修改后的值" + newValue.toString());
        spuLog.setOldValue(oldValue.toString());
        spuLog.setNewValue(newValue.toString());
        spuLog.setColumnName(column);
        spuLog.setOperator(operator == null ? "" : operator);
        spuLog.setCreatTime(new Date());
        spuLogMapper.insert(spuLog);
    }

    private void editLog(String id, Spu oldSpu, Spu newSpu) {
        if (oldSpu.getSn().equals(newSpu.getSn())) {
            log(id, "sn", oldSpu.getSn(), newSpu.getSn(), null);
        }
        if (oldSpu.getName().equals(newSpu.getName())) {
            log(id, "name", oldSpu.getName(), newSpu.getName(), null);
        }
        if (oldSpu.getCaption().equals(newSpu.getCaption())) {
            log(id, "caption", oldSpu.getCaption(), newSpu.getCaption(), null);
        }
        if (oldSpu.getBrandId().equals(newSpu.getBrandId())) {
            log(id, "brandId", oldSpu.getBrandId(), newSpu.getBrandId(), null);
        }
        if (oldSpu.getCategory1Id().equals(newSpu.getCategory1Id())) {
            log(id, "category1Id", oldSpu.getCategory1Id(), newSpu.getCategory1Id(), null);
        }
        if (oldSpu.getCategory2Id().equals(newSpu.getCategory2Id())) {
            log(id, "category2Id", oldSpu.getCategory2Id(), newSpu.getCategory2Id(), null);
        }
        if (oldSpu.getCategory3Id().equals(newSpu.getCategory3Id())) {
            log(id, "category3Id", oldSpu.getCategory3Id(), newSpu.getCategory3Id(), null);
        }
        if (oldSpu.getTemplateId().equals(newSpu.getTemplateId())) {
            log(id, "templateId", oldSpu.getTemplateId(), newSpu.getTemplateId(), null);
        }
        if (oldSpu.getFreightId().equals(newSpu.getFreightId())) {
            log(id, "freightId", oldSpu.getFreightId(), newSpu.getFreightId(), null);
        }
        if (oldSpu.getImage().equals(newSpu.getImage())) {
            log(id, "image", oldSpu.getImage(), newSpu.getImage(), null);
        }
        if (oldSpu.getImages().equals(newSpu.getImages())) {
            log(id, "images", oldSpu.getImages(), newSpu.getImages(), null);
        }
        if (oldSpu.getSaleService().equals(newSpu.getSaleService())) {
            log(id, "saleService", oldSpu.getSaleService(), newSpu.getSaleService(), null);
        }
        if (oldSpu.getIntroduction().equals(newSpu.getIntroduction())) {
            log(id, "introduction", oldSpu.getIntroduction(), newSpu.getIntroduction(), null);
        }
        if (oldSpu.getSpecItems().equals(newSpu.getSpecItems())) {
            log(id, "specItems", oldSpu.getSpecItems(), newSpu.getSpecItems(), null);
        }
        if (oldSpu.getParaItems().equals(newSpu.getParaItems())) {
            log(id, "paraItems", oldSpu.getParaItems(), newSpu.getParaItems(), null);
        }
        if (oldSpu.getSaleNum().equals(newSpu.getSaleNum())) {
            log(id, "saleNum", oldSpu.getSaleNum(), newSpu.getSaleNum(), null);
        }
        if (oldSpu.getCommentNum().equals(newSpu.getCommentNum())) {
            log(id, "commentNum", oldSpu.getCommentNum(), newSpu.getCommentNum(), null);
        }
        if (oldSpu.getIsMarketable().equals(newSpu.getIsMarketable())) {
            log(id, "isMarketable", oldSpu.getIsMarketable(), newSpu.getIsMarketable(), null);
        }
        if (oldSpu.getIsEnableSpec().equals(newSpu.getIsEnableSpec())) {
            log(id, "isEnableSpe", oldSpu.getIsEnableSpec(), newSpu.getIsEnableSpec(), null);
        }
        if (oldSpu.getIsDelete().equals(newSpu.getIsDelete())) {
            log(id, "isDelete", oldSpu.getIsDelete(), newSpu.getIsDelete(), null);
        }
        if (oldSpu.getStatus().equals(newSpu.getStatus())) {
            log(id, "status", oldSpu.getStatus(), newSpu.getStatus(), null);
        }
    }

    /**
     * 构建查询条件
     *
     * @param searchMap
     * @return
     */
    private Example createExample(Map<String, Object> searchMap) {
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if (searchMap != null) {
            //未删除
            criteria.andEqualTo("isDelete", "0");
            // 货号
            if (searchMap.get("sn") != null && !"".equals(searchMap.get("sn"))) {
                criteria.andLike("sn", "%" + searchMap.get("sn") + "%");
            }
            // SPU名
            if (searchMap.get("name") != null && !"".equals(searchMap.get("name"))) {
                criteria.andLike("name", "%" + searchMap.get("name") + "%");
            }
            // 是否上架
            if (searchMap.get("isMarketable") != null && !"".equals(searchMap.get("isMarketable"))) {
                criteria.andEqualTo("isMarketable", searchMap.get("isMarketable"));
            }
            // 审核状态
            if (searchMap.get("status") != null && !"".equals(searchMap.get("status"))) {
                criteria.andEqualTo("status", searchMap.get("status"));
            }
            //商品分类
            if (searchMap.get("category3Id") != null && !"".equals(searchMap.get("category3Id"))) {
                criteria.andEqualTo("category3Id", searchMap.get("category3Id"));
            }
            //商品品牌
            if (searchMap.get("brandId") != null && !"".equals(searchMap.get("brandId"))) {
                criteria.andEqualTo("brandId", searchMap.get("brandId"));
            }
        }
        return example;
    }

}
