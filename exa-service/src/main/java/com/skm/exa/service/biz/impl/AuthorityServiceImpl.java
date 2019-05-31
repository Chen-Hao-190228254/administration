package com.skm.exa.service.biz.impl;

import com.skm.exa.common.enums.Msg;
import com.skm.exa.common.enums.StatusEnum;
import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.utils.SetCommonElement;
import com.skm.exa.domain.bean.AuthorityBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.dao.AuthorityDao;
import com.skm.exa.persistence.qo.AuthorityQO;
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
        return dao.select(null);
    }


    /**
     * 获得指定ID的权限
     * @param id
     * @return
     */
    @Override
    public AuthorityBean getAuthority(Long id) {
        return dao.get(id);
    }


    /**
     * 分页查询
     * @param pageParam
     * @return
     */
    @Override
    public Page<AuthorityBean> getAuthorityPage(PageParam<AuthorityQO> pageParam){
        return dao.selectPage(pageParam);
    }

    /**
     * 根据账号判读该权限是否已经存在
     * @param code
     * @return
     */
    @Override
    public Boolean getAuthorityCode(String code) {
        return super.has(new AuthorityQO(code));
    }

    /**
     * 添加权限
     * @param authorityBean
     * @return
     */
    @Override
    @Transactional
    public Boolean addAuthority(AuthorityBean authorityBean, UnifyAdmin unifyAdmin) {
        authorityBean = new SetCommonElement().setAdd(authorityBean,unifyAdmin);
        return dao.insert(authorityBean) > 0;
    }


    /**
     * 更新权限
     * @param authorityBean
     * @param unifyAdmin
     * @return
     */
    @Override
    @Transactional
    public Boolean updateAuthority(AuthorityBean authorityBean, UnifyAdmin unifyAdmin) {
        authorityBean = new SetCommonElement().setupdate(authorityBean,unifyAdmin);
        return dao.update(authorityBean) > 0;
    }

    /**
     * 删除权限
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Boolean deleteAuthority(Long id) {
        return dao.delete(id) > 0;
    }

    /**
     * 更改权限状态
     * @param authorityBean
     * @return
     */
    @Override
    @Transactional
    public Boolean setStatus(AuthorityBean authorityBean,UnifyAdmin unifyAdmin){
        authorityBean = new SetCommonElement().setupdate(authorityBean,unifyAdmin);
        return dao.update(authorityBean) > 0;
    }

}
