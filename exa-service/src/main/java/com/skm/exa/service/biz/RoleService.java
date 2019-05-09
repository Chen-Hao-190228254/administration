package com.skm.exa.service.biz;


import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.domain.bean.AuthorityBean;
import com.skm.exa.domain.bean.RoleBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.dto.RoleDto;
import com.skm.exa.persistence.dto.RoleSaveDto;
import com.skm.exa.persistence.dto.RoleUpdateDto;
import com.skm.exa.persistence.qo.RoleQO;

import java.util.List;

public interface RoleService {


    /**
     * 通过指定ID，获得角色
     * @param id
     * @return
     */
    RoleDto getRole(Long id);


    /**
     * 获得所有的角色
     * @return
     */
    List<RoleDto> getRoleList();

    /**
     * 分页查询，可加条件
     * @param qo
     * @return
     */
    Page<RoleDto> getRolePage(PageParam<RoleQO> qo);

    /**
     * 添加角色
     * @param roleSaveDto
     * @return
     */
    Result<RoleDto> addRole(RoleSaveDto roleSaveDto, UnifyAdmin unifyAdmin);

    /**
     * 更新角色
     * @param roleUpdateDto
     * @param unifyAdmin
     * @return
     */
    Result<RoleDto> updateRole(RoleUpdateDto roleUpdateDto, UnifyAdmin unifyAdmin);

    /**
     * 删除角色
     * @param id
     * @return
     */
    Boolean deleteRole(Long id);

    /**
     * 更改权限状态
     * @param id
     * @return
     */
    Result<RoleBean> setStatus(Long id);





//<-----------------------角色权限的获取及操作---------------------------->


    /**
     * 通过角色ID，获得角色的权限
     * @param roleBean
     * @return
     */
    RoleDto getRoleAuthority(RoleBean roleBean);



    /**
     * 通过角色列表，获得列表中角色的权限
     * @param roleBeans
     * @return
     */
    List<RoleDto> getRoleAuthority(List<RoleBean> roleBeans);



    /**
     * 添加角色权限
     * @param authorityId
     * @param roleId
     * @param unifyAdmin
     * @return
     */
    Result<List<AuthorityBean>> addRoleAuthority(List<Long> authorityId, Long roleId, UnifyAdmin unifyAdmin);



    /**
     * 更新角色权限     需要配合（添加角色权限)addRoleAuthority使用
     * @param authorityId
     * @param roleId
     * @param unifyAdmin
     * @return
     */
    Result<List<AuthorityBean>> updateRoleAuthority(List<Long> authorityId,Long roleId,UnifyAdmin unifyAdmin);


    Boolean deleteRoleAuthority(Long roleId);


}
