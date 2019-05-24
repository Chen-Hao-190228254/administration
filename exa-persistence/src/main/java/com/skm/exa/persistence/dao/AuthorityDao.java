package com.skm.exa.persistence.dao;

import com.skm.exa.domain.bean.AuthorityBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthorityDao extends BaseDao<AuthorityBean> {


    List<AuthorityBean> getAuthorityList();



    Page<AuthorityBean> getAuthorityPage(PageParam<?> pageParam);


    AuthorityBean getAuthorityCode(String code);


    int setStatus(@Param("id") Long id, @Param("status") Byte status);


}
