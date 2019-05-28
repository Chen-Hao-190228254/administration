package com.skm.exa.common.utils;

import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.domain.BaseBean;

import java.util.Date;

public class SetCommonElement {
    public <T extends BaseBean> T setAdd(T bean, UnifyAdmin unifyAdmin){
        bean.setEntryId(unifyAdmin.getId());
        bean.setEntryName(unifyAdmin.getName());
        bean.setEntryDt(new Date());
        bean.setUpdateId(unifyAdmin.getId());
        bean.setUpdateName(unifyAdmin.getName());
        bean.setUpdateDt(new Date());
        return bean;
    }

    public <T extends BaseBean> T setupdate(T bean, UnifyAdmin unifyAdmin){
        bean.setUpdateId(unifyAdmin.getId());
        bean.setUpdateName(unifyAdmin.getName());
        bean.setUpdateDt(new Date());
        return bean;
    }


}
