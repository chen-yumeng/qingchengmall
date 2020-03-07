package com.cg.qingcheng.pojo.goods;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * tb_spu_log
 *
 * @author
 */
@Table(name = "tb_spu_log")
public class SpuLog implements Serializable {
    /**
     * 主键
     */
    @Id
    private Integer id;

    /**
     * spu_id
     */
    private String spuId;

    /**
     * 列名
     */
    private String columnName;

    /**
     * 修改前的值
     */
    private String oldValue;

    /**
     * 修改后的值
     */
    private String newValue;

    /**
     * 操作人员
     */
    private String operator;

    /**
     * 操作时间
     */
    private Date creatTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpuId() {
        return spuId;
    }

    public void setSpuId(String spuId) {
        this.spuId = spuId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }
}