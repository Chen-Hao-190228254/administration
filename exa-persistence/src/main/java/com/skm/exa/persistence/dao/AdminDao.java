package com.skm.exa.persistence.dao;

import com.skm.exa.domain.bean.AdminBean;
import com.skm.exa.persistence.BaseDao;

import java.util.List;

public interface AdminDao extends BaseDao<AdminBean> {

    List<AdminBean> getAdminList();

}
