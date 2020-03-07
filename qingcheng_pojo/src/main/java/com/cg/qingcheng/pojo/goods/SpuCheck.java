package com.cg.qingcheng.pojo.goods;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * tb_spu_check
 * @author 
 */
@Table(name = "tb_spu_check")
public class SpuCheck implements Serializable {

    @Id
    private Integer id;

    /**
     * spuId
     */
    private String spuId;

    /**
     * 审核人员
     */
    private String checkName;

    /**
     * 审核状态
     */
    private String status;

    /**
     * 审核时间
     */
    private Date checkTime;

    /**
     * 反馈详情
     */
    private String details;

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

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}