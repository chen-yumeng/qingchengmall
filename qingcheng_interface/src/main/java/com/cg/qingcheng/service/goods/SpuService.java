package com.cg.qingcheng.service.goods;

import com.cg.qingcheng.entity.Goods;
import com.cg.qingcheng.entity.PageResult;
import com.cg.qingcheng.pojo.goods.Spu;
import com.cg.qingcheng.pojo.goods.SpuCheck;
import com.cg.qingcheng.pojo.goods.SpuLog;

import java.util.List;
import java.util.Map;

/**
 * spu业务逻辑层
 */
public interface SpuService {

    public List<Spu> findAll();

    public PageResult<Spu> findPage(int page, int size);

    public List<Spu> findList(Map<String, Object> searchMap);

    public PageResult<Spu> findPage(Map<String, Object> searchMap, int page, int size);

    public Spu findById(String id);

    public void add(Spu spu);

    public void update(Spu spu);

    /**
     * 逻辑删除
     *
     * @param id
     */
    public void delete(String id);

    /**
     * 物理删除
     *
     * @param id
     */
    public int completeDelete(String id);

    /**
     * 回收
     *
     * @param id
     */
    public int recover(String id);

    public void saveGoods(Goods goods);

    public Goods findGoodsById(String id);

    /**
     * 商品审核
     *
     * @param id
     * @param status
     * @param message
     */
    public void audit(String id, String status, String message);

    /**
     * 商品下架
     *
     * @param id
     */
    public void pull(String id);

    /**
     * 商品上架
     *
     * @param id
     */
    public void put(String id);

    /**
     * 批量上架
     *
     * @param ids
     * @return
     */
    public int putMany(String[] ids);

    /**
     * 批量下架
     *
     * @param ids
     * @return
     */
    public int pullMany(String[] ids);

    public Map<String, Integer> getTotal();

    public List<SpuCheck> getCheckLog(String id);

    public List<SpuLog> getSpuLog(String id);
}
