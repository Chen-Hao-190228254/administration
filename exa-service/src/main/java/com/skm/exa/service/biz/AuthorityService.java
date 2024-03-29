package com.skm.exa.service.biz;

import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.domain.bean.AuthorityBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.qo.AuthorityQO;

import java.util.List;

public interface AuthorityService {


    List<AuthorityBean> getAuthorityList();

    AuthorityBean getAuthority(Long id);

    Page<AuthorityBean> getAuthorityPage(PageParam<AuthorityQO> pageParam);

    Boolean getAuthorityCode(String code);

    Boolean addAuthority(AuthorityBean authorityBean, UnifyAdmin unifyAdmin);

    Boolean updateAuthority(AuthorityBean authorityBean, UnifyAdmin unifyAdmin);

    Boolean deleteAuthority(Long id);

    Boolean setStatus(AuthorityBean authorityBean, UnifyAdmin unifyAdmin);


}
