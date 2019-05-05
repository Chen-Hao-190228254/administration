package com.skm.exa.persistence.dao;

import com.skm.exa.domain.bean.RoleBean;
import com.skm.exa.persistence.BaseDao;

public interface RoleDao extends BaseDao<RoleBean> {


    RoleBean getRole(Long id);


}
