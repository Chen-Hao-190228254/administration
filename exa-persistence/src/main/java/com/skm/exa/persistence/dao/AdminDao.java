package com.skm.exa.persistence.dao;

import com.skm.exa.domain.bean.AdminBean;
import com.skm.exa.domain.bean.RoleBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.BaseDao;
import com.skm.exa.persistence.dto.AdminRoleDto;
import com.skm.exa.persistence.qo.AdminQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminDao extends BaseDao<AdminBean> {




//<-----------------------管理员角色的获取及操作---------------------------->


    /**
     * 通过管理员ID，查询管理员角色
     * @param adminId
     * @return
     */
    List<RoleBean> getAdminRoleAdminId(Long adminId);


    /**
     * 通过管理员ID列表，获得管理员权限
     * @param adminId
     * @return
     */
    List<AdminRoleDto> getAdminRole(@Param("adminId") List<Long> adminId);



    /**
     * 通过角色列表和管理员ID添加管理员角色
     * @param roleBean
     * @param adminId
     * @return
     */
    int addAdminRole(@Param("roleBean") List<RoleBean> roleBean,@Param("adminId") Long adminId);


    /**
     * 删除指定ID的管理员角色
     * @param adminId
     * @return
     */
    int deleteAdminRole(Long adminId);

}
