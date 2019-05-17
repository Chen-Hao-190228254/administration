package com.skm.exa.service.biz.impl;

import com.skm.exa.common.enums.Msg;
import com.skm.exa.common.enums.StatusEnum;
import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
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
        return dao.getAuthorityList();
    }


    /**
     * 获得指定ID的权限
     * @param id
     * @return
     */
    @Override
    public AuthorityBean getAuthority(Long id) {
        AuthorityBean authorityBean = super.get(id);
        return authorityBean;
    }


    /**
     * 分页查询
     * @param pageParam
     * @return
     */
    @Override
    public Page<AuthorityBean> getAuthorityPage(PageParam<AuthorityQO> pageParam){
        Page<AuthorityBean> page = dao.getAuthorityPage(pageParam);
        return page;
    }

    /**
     * 添加权限
     * @param authorityBean
     * @return
     */
    @Override
    @Transactional
    public Result<AuthorityBean> addAuthority(AuthorityBean authorityBean, UnifyAdmin unifyAdmin) {
        AuthorityQO authorityQO = new AuthorityQO();
        authorityQO.setCode(authorityBean.getCode());
        boolean is = super.has(authorityQO);
        if(is){
            Result<AuthorityBean> result = new Result<>(-1,"编码已经存在，请重新输入编码");
            result.setContent(authorityBean);
            return result;
        }
        authorityBean.setEntryId(unifyAdmin.getId());
        authorityBean.setEntryName(unifyAdmin.getName());
        authorityBean.setEntryDt(new Date());
        authorityBean.setUpdateId(unifyAdmin.getId());
        authorityBean.setUpdateName(unifyAdmin.getName());
        authorityBean.setUpdateDt(new Date());
        int i = dao.insert(authorityBean);
        if(i<=0){
            Result<AuthorityBean> result = new Result<>(Msg.E40000);
            result.setContent(authorityBean);
            return result;
        }else {
            Result<AuthorityBean> result = new Result<>();
            result.setContent(authorityBean);
            return result;
        }
    }


    /**
     * 更新权限
     * @param authorityBean
     * @param unifyAdmin
     * @return
     */
    @Override
    @Transactional
    public Result<AuthorityBean> updateAuthority(AuthorityBean authorityBean, UnifyAdmin unifyAdmin) {
        authorityBean.setUpdateId(unifyAdmin.getId());
        authorityBean.setUpdateName(unifyAdmin.getName());
        authorityBean.setUpdateDt(new Date());
        System.out.println(authorityBean.getId());
        int i = dao.update(authorityBean);
        if(i<=0){
            return new Result<AuthorityBean>(Msg.E40000).setContent(authorityBean);
        }else {
            return Result.success(getAuthority(authorityBean.getId()));
        }
    }

    /**
     * 删除权限
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Boolean deleteAuthority(Long id) {
        int i = dao.delete(id);
        if(i<=0){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 更改权限状态
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Result<AuthorityBean> setStatus(Long id){
        AuthorityBean authorityBean = getAuthority(id);
        if(authorityBean == null)
            return Result.error(-1,"权限ID有误");
        if(authorityBean.getStatus() == StatusEnum.NORMAL.getIndex()){
            int is = dao.setStatus(id,StatusEnum.FORBIDDEN.getIndex());
            if(is<=0){
                return Result.error(-1,"状态更改失败");
            }
        }else if(authorityBean.getStatus() == StatusEnum.FORBIDDEN.getIndex()){
            int is = dao.setStatus(id,StatusEnum.NORMAL.getIndex());
            if(is<=0){
                return Result.error(-1,"状态更改失败");
            }
        }else {
            return Result.error(-1,"数据库权限状态有误");
        }

        return Result.success(getAuthority(id));

    }


}
