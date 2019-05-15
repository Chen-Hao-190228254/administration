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









    /**
     * 根据管理员ID获得管理员
     * @param id
     * @return
     */
    AdminBean getAdmin(Long id);


    /**
     * 获得所有的管理员列表
     * @return
     */
    List<AdminBean> getAdminList();


    /**
     * 分页查询，可加条件
     * @param param
     * @return
     */
    Page<AdminBean> getAdminPage(PageParam<AdminQO> param);


    /**
     * 添加管理员
     * @param adminBean
     * @return
     */
    int addAdmin(AdminBean adminBean);


    /**
     * 更新管理员
     * @param adminBean
     * @return
     */
    int updateAdmin(AdminBean adminBean);

    /**
     * 更改管理员密码
     * @param password
     * @param adminId
     * @return
     */
    int updatePassword(String password, Long adminId);


    int updateStatus(@Param("id")Long id,@Param("status")Byte status);



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
