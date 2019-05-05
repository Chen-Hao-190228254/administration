package com.skm.exa.service.biz;

import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.domain.bean.AuthorityBean;

import java.util.List;

public interface AuthorityService {

    List<AuthorityBean> getAuthorityList();

    AuthorityBean addAuthority(AuthorityBean authorityBean, UnifyAdmin unifyAdmin);

}
