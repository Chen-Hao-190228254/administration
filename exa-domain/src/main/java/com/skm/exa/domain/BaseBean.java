package com.skm.exa.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dhc
 * 2019-03-07 11:59
 */
public abstract class BaseBean implements Serializable {
    private Long id;
    private Date entryDt;
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
