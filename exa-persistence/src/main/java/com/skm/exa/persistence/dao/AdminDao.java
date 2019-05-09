package com.skm.exa.persistence.dao;

import com.skm.exa.domain.bean.AdminBean;
import com.skm.exa.domain.bean.RoleBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.BaseDao;
import com.skm.exa.persistence.qo.AdminQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminDao extends BaseDao<AdminBean> {


    /**
     * 获得所有的管理员列表
     * @return
     */
    List<AdminBean> getAdminList();


    /**
     * 根据管理员ID获得管理员
     * @param id
     * @return
     */
    AdminBean getAdmin(Long id);


    /**
     * 分页查询，可加条件
     * @param param
     * @return
     */
    Page<AdminBean> getAdminPage(PageParam<AdminQO> param);




//<-----------------------管理员角色的获取及操作---------------------------->


    /**
     * 通过角色列表和管理员ID添加管理员角色
     * @param roleBean
     * @param adminId
     * @return
     */
    int addAdminRole(@Param("roleBean") List<RoleBean> roleBean,@Param("adminId") Long adminId);


    /**
     * 通过管理员ID获得管理员角色
     * @param id
     * @return
     */
    List<RoleBean> getRoleIn(Long id);

}
