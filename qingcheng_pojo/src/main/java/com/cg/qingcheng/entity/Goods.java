package com.cg.qingcheng.entity;

import com.cg.qingcheng.pojo.goods.Sku;
import com.cg.qingcheng.pojo.goods.Spu;

import java.io.Serializable;
import java.util.List;

/**
 * @program: qingcheng_parent->Goods
 * @description:
 * @author: cg
 * @create: 2020-02-25 09:47
 **/

public class Goods implements Serializable {

    private Spu spu;

    private List<Sku> skuList;

    public Spu getSpu() {
        return spu;
    }

    public void setSpu(Spu spu) {
        this.spu = spu;
    }

    public List<Sku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<Sku> skuList) {
        this.skuList = skuList;
    }
}
