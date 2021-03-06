package com.cg.qingcheng.pojo.order;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.crypto.Data;
import java.io.Serializable;

/**
 * CategoryReport实体类
 *
 * @author Administrator
 */
@Table(name = "category_report")
public class CategoryReport implements Serializable {

    /**
     * 订单id
     */
    @Id
    private String id;

    /**
     * 1级分类
     */
    private Integer categoryId1;
    /**
     * 2级分类
     */
    private Integer categoryId2;
    /**
     * 3级分类
     */
    private Integer categoryId3;
    /**
     * 统计日期
     */
    private Data countDate;
    /**
     * 销售数量
     */
    private Integer num;
    /**
     * 销售额
     */
    private Integer money;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCategoryId1() {
        return categoryId1;
    }

    public void setCategoryId1(Integer categoryId1) {
        this.categoryId1 = categoryId1;
    }

    public Integer getCategoryId2() {
        return categoryId2;
    }

    public void setCategoryId2(Integer categoryId2) {
        this.categoryId2 = categoryId2;
    }

    public Integer getCategoryId3() {
        return categoryId3;
    }

    public void setCategoryId3(Integer categoryId3) {
        this.categoryId3 = categoryId3;
    }

    public Data getCountDate() {
        return countDate;
    }

    public void setCountDate(Data countDate) {
        this.countDate = countDate;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }
}
