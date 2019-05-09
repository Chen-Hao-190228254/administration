package com.skm.exa.service.biz;

import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.domain.bean.AdminBean;
import com.skm.exa.domain.bean.RoleBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.dto.AdminSaveDto;
import com.skm.exa.persistence.dto.AdminUpdateDto;
import com.skm.exa.persistence.qo.AdminQO;
import com.skm.exa.service.BaseService;

import java.util.List;

public interface AdminService extends BaseService<AdminBean> {


    /**
     * 获得所有管理员
     * @return
     */
    List<AdminBean> getAdminList();


    /**
     * 获得指定ID的管理员
     * @param id
     * @return
     */
    AdminBean getAdmin(Long id);


    /**
     * 分页获得管理员，可加条件
     * @param pageParam
     * @return
     */
    Page<AdminBean> getAdminPage(PageParam<AdminQO> pageParam);



    /**
     * 添加管理员
     * @param adminSaveDto
     * @param unifyAdmin
     * @return
     */
    Result<AdminBean> addAdmin(AdminSaveDto adminSaveDto, UnifyAdmin unifyAdmin);


    /**
     * 更新管理员
     * @param adminUpDto
     * @param unifyAdmin
     * @return
     */
    AdminBean upAdmin(AdminUpdateDto adminUpDto, UnifyAdmin unifyAdmin);









//<-----------------------管理员角色的获取及操作---------------------------->

    /**
     * 添加管理员角色
     * @param roleId
     * @param adminId
     * @param unifyAdmin
     * @return
     */
    Result<List<RoleBean>> addAdminRole(List<Long> roleId, Long adminId, UnifyAdmin unifyAdmin);

    /**
     * 根据管理员列表，获得管理员角色
     * @param adminBeans
     * @return
     */
    List<AdminBean> getAdminRoleList(List<AdminBean> adminBeans);

}
