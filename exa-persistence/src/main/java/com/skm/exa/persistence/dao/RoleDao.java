package com.skm.exa.persistence.dao;

import com.skm.exa.domain.bean.AuthorityBean;
import com.skm.exa.domain.bean.RoleBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.BaseDao;
import com.skm.exa.persistence.dto.RoleAuthorityDto;
import com.skm.exa.persistence.qo.RoleQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleDao extends BaseDao<RoleBean> {


    /**
     * 获得指定ID的角色
     * @param id
     * @return
     */
    RoleBean getRole(Long id);



    /**
     * 获得全部角色
     * @return
     */
    List<RoleBean> getRoleList();



    /**
     * 分页获得角色，可加条件
     * @param qo
     * @return
     */
    Page<RoleBean> getRolePage(PageParam<RoleQO> qo);



    /**
     * 添加角色   Service层还需要调用下面（添加角色权限）addRoleAuthority方法一起使用
     * @param roleBean
     * @return
     */
    int addRole(RoleBean roleBean);



    /**
     * 修改角色
     * @param roleBean
     * @return
     */
    int updateRole(RoleBean roleBean);


    /**
     * 修改角色的状态
     * @param id
     * @param status
     * @return
     */
    int setStatus(@Param("id") Long id,@Param("status") byte status);



//<-----------------------角色权限的获取及操作---------------------------->


    /**
     * 通过角色的ID获得角色的权限
     * @param id
     * @return
     */
    List<AuthorityBean> getRoleAuthorityRoleId(Long id);



    /**
     * 通过角色的ID列表获得角色的权限
     * @param roleIdList
     * @return
     */
    List<RoleAuthorityDto> getRoleAuthority(@Param("roleIdList") List<Long> roleIdList);




    /**
     * 添加角色权限
     * @param authorityBeans
     * @param roleId
     * @return
     */
    int addRoleAuthority(@Param("authorityBeans") List<AuthorityBean> authorityBeans, @Param("roleId") Long roleId);



    /**
     * 删除角色权限
     * @param roleId
     * @return
     */
    int deleteRoleAuthority(@Param("roleId") Long roleId);
}
