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
