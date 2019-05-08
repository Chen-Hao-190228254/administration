package com.skm.exa.service.biz.impl;


import com.skm.exa.common.enums.Msg;
import com.skm.exa.common.enums.StatusEnum;
import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.domain.bean.AuthorityBean;
import com.skm.exa.domain.bean.RoleBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.dao.RoleDao;
import com.skm.exa.persistence.dto.RoleSaveDto;
import com.skm.exa.persistence.dto.RoleUpdateDto;
import com.skm.exa.persistence.qo.RoleQO;
import com.skm.exa.service.BaseServiceImpl;
import com.skm.exa.service.biz.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleBean, RoleDao> implements RoleService {




    /**
     * 通过指定ID，获得角色
     * @param id
     * @return
     */
    @Override
    public RoleBean getRole(Long id) {
        RoleBean roleBean = super.get(id);
        roleBean.setAuthorityBeans(dao.getAuthorityIn(id));
        System.out.println(roleBean.toString());
        return roleBean;
    }


    /**
     * 获得所有的角色
     * @return
     */
    @Override
    public List<RoleBean> getRoleList(){
        List<RoleBean> roleBeans = dao.getRoleList();
        roleBeans = getRoleAuthority(roleBeans);
        return roleBeans;
    }


    /**
     * 分页查询，可加条件
     * @param qo
     * @return
     */
    @Override
    public Page<RoleBean> getRolePage(PageParam<RoleQO> qo){
        Page<RoleBean> page = dao.getRolePage(qo);
        List<RoleBean> list = page.getContent();
        list = getRoleAuthority(list);
        page.setContent(list);
        return page;
    }

    /**
     * 添加角色
     * @param roleSaveDto
     * @return
     */
    @Override
    @Transactional
    public Result<RoleBean> addRole(RoleSaveDto roleSaveDto, UnifyAdmin unifyAdmin) {
        RoleBean roleBean = BeanMapper.map(roleSaveDto,RoleBean.class);
        roleBean.setEntryId(unifyAdmin.getId());
        roleBean.setEntryName(unifyAdmin.getName());
        roleBean.setEntryDt(new Date());
        roleBean.setUpdateId(unifyAdmin.getId());
        roleBean.setUpdateName(unifyAdmin.getName());
        roleBean.setUpdateDt(new Date());
        int is = dao.addRole(roleBean);
        if(is<=0){
            Result result = new Result(Msg.E40000);
            result.setMessage("添加角色时错误");
            return result;
        }else {
            List<Long> authorityId = roleSaveDto.getAuthorityId();
            Result<List<AuthorityBean>> addRoleAuthority = addRoleAuthority(authorityId,roleBean.getId(),unifyAdmin);
            List<AuthorityBean> authorityBeanList = addRoleAuthority.getContent();
            roleBean.setAuthorityBeans(authorityBeanList);
            Result<RoleBean> result = BeanMapper.map(addRoleAuthority,Result.class);
            result.setContent(roleBean);
            return result;

        }
    }


    /**
     * 更新角色
     * @param roleUpdateDto
     * @param unifyAdmin
     * @return
     */
    @Override
    @Transactional
    public Result<RoleBean> updateRole(RoleUpdateDto roleUpdateDto,UnifyAdmin unifyAdmin){
        RoleBean roleBean = BeanMapper.map(roleUpdateDto,RoleBean.class);
        roleBean.setUpdateId(unifyAdmin.getId());
        roleBean.setUpdateName(unifyAdmin.getName());
        roleBean.setUpdateDt(new Date());
        int is = dao.updateRole(roleBean);
        if(is<=0){
            Result result = new Result(Msg.E40000);
            result.setMessage("更新角色时发生错误");
            return result;
        }else {
            List<Long> authorityId = roleUpdateDto.getAuthorityId();
            Result<List<AuthorityBean>> updateRoleAuthority = updateRoleAuthority(authorityId,roleBean.getId(),unifyAdmin);
            List<AuthorityBean> authorityBeanList = updateRoleAuthority.getContent();
            roleBean.setAuthorityBeans(authorityBeanList);
            Result<RoleBean> result = BeanMapper.map(updateRoleAuthority,Result.class);
            result.setContent(roleBean);
            return result;
        }

    }

    @Override
    @Transactional
    public Boolean deleteRole(Long id) {
        int i = dao.deleteRoleAuthority(id);
        if(i<=0){
            return false;
        }else {
            int is = dao.delete(id);
            if (is<=0){
                return false;
            }else {
                return true;
            }
        }
    }

    @Override
    public Result<RoleBean> setStatus(Long id) {
        RoleBean roleBean = getRole(id);
        if(roleBean == null){
            return Result.error(-1,"指定ID的数据不存在");
        }else {
            if(roleBean.getStatus() == StatusEnum.NORMAL.getIndex()){
                dao.setStatus(id,StatusEnum.FORBIDDEN.getIndex());
            }else if (roleBean.getStatus() == StatusEnum.FORBIDDEN.getIndex()){
                dao.setStatus(id,StatusEnum.NORMAL.getIndex());
            }else {
                return Result.error(-1,"数据库状态数据有误");
            }
        }
        return Result.success(getRole(id));
    }


//<-----------------------角色权限的获取及操作---------------------------->


    /**
     * 通过角色列表，获得列表中角色的权限
     * @param roleBeans
     * @return
     */
    @Override
    public List<RoleBean> getRoleAuthority(List<RoleBean> roleBeans){
        for(RoleBean roleBean : roleBeans){
            roleBean.setAuthorityBeans(dao.getAuthorityIn(roleBean.getId()));
        }
        return roleBeans;
    }


    /**
     * 添加角色权限
     * @param authorityId
     * @param roleId
     * @param unifyAdmin
     * @return
     */
    public Result<List<AuthorityBean>> addRoleAuthority(List<Long> authorityId,Long roleId,UnifyAdmin unifyAdmin){
        List<AuthorityBean> authorityBeans = new ArrayList<>();
        for (Long i:authorityId){
            AuthorityBean authorityBean = new AuthorityBean();
            authorityBean.setId(i);
            authorityBean.setEntryId(unifyAdmin.getId());
            authorityBean.setEntryName(unifyAdmin.getName());
            authorityBean.setEntryDt(new Date());
            authorityBean.setUpdateId(unifyAdmin.getId());
            authorityBean.setUpdateName(unifyAdmin.getName());
            authorityBean.setUpdateDt(new Date());
            authorityBeans.add(authorityBean);
        }
        int i = dao.addRoleAuthority(authorityBeans,roleId);
        if(i<=0){
            Result result = new Result(Msg.E40000);
            result.setMessage("添加角色权限时发生错误");
            return result;
        }else {
            List<AuthorityBean> authorityBeanList = dao.getAuthorityIn(roleId);
            return Result.success(authorityBeanList);
        }

    }

    /**
     * 更新角色权限     需要配合（添加角色权限)addRoleAuthority使用
     * @param authorityId
     * @param roleId
     * @param unifyAdmin
     * @return
     */
    public Result<List<AuthorityBean>> updateRoleAuthority(List<Long> authorityId,Long roleId,UnifyAdmin unifyAdmin){
        int i = dao.deleteRoleAuthority(roleId);
        if(i<=0){
            Result result = new Result(Msg.E40000);
            result.setMessage("删除角色权限时发生错误");
            return result;
        }else {
            return addRoleAuthority(authorityId,roleId,unifyAdmin);
        }

    }

}
