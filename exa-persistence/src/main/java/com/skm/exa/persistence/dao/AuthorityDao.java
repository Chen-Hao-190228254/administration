package com.skm.exa.persistence.dao;

import com.skm.exa.domain.bean.AuthorityBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.BaseDao;

import java.util.List;

public interface AuthorityDao extends BaseDao<AuthorityBean> {


    List<AuthorityBean> getAuthorityList();



    Page<AuthorityBean> getAuthorityPage(PageParam<?> pageParam);




}
