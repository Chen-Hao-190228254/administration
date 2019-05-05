package com.skm.exa.service.biz.impl;

import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.domain.bean.AuthorityBean;
import com.skm.exa.persistence.dao.AuthorityDao;
import com.skm.exa.service.BaseServiceImpl;
import com.skm.exa.service.biz.AuthorityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AuthorityServiceImpl extends BaseServiceImpl<AuthorityBean, AuthorityDao> implements AuthorityService {


    /**
     * 查询所有权限
     * @return
     */
    @Override
    public List<AuthorityBean> getAuthorityList() {
        return dao.getAuthorityList();
    }

    /**
     * 添加权限
     * @param authorityBean
     * @return
     */
    @Override
    @Transactional
    public AuthorityBean addAuthority(AuthorityBean authorityBean, UnifyAdmin unifyAdmin) {
        authorityBean.setEntryId(unifyAdmin.getId());
        authorityBean.setEntryName(unifyAdmin.getName());
        authorityBean.setEntryDt(new Date());
        authorityBean.setUpdateId(unifyAdmin.getId());
        authorityBean.setUpdateName(unifyAdmin.getName());
        authorityBean.setUpdateDt(new Date());
        int i = dao.insert(authorityBean);
        return authorityBean;
    }


}
