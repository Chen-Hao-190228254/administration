package com.skm.exa.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dhc
 * 2019-03-07 11:59                 //  Serializable 序列化
 */
@Data
public abstract class BaseBean implements Serializable {
    private Long id;

    /*创建人id*/
    private Long entryId;
    /*创建人名称*/
    private String entryName;
    /*创建时间*/
    private Date entryDt;
    /*跟新人id*/
    private Long updateId;
    /*跟新人名称*/
    private String updateName;
    /*更新时间*/
    private Date updateDt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEntryDt() {
        return entryDt;
    }

    public void setEntryDt(Date entryDt) {
        this.entryDt = entryDt;
    }

    public Date getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }
}
