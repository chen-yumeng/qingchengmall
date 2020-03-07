package com.cg.qingcheng.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @program: qingcheng_parent->PageResult
 * @description:
 * @author: cg
 * @create: 2020-02-21 17:14
 **/

public class PageResult<T> implements Serializable {

    private List<T> rows;

    private Long total;

    public PageResult() {
    }

    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
