package com.skm.exa.service.biz;

import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.domain.bean.AdminBean;
import com.skm.exa.domain.bean.RoleBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.dto.AdminDto;
import com.skm.exa.persistence.dto.AdminSaveDto;
import com.skm.exa.persistence.dto.AdminUpdateDto;
import com.skm.exa.persistence.qo.AdminQO;
import com.skm.exa.service.BaseService;

import java.util.List;

public interface AdminService extends BaseService<AdminBean> {

    /**
     * 获得指定ID的管理员
     * @param id
     * @return
     */
    AdminDto getAdmin(Long id);

    /**
     * 获得所有管理员
     * @return
     */
    List<AdminDto> getAdminList();





    /**
     * 分页获得管理员，可加条件
     * @param pageParam
     * @return
     */
    Page<AdminDto> getAdminPage(PageParam<AdminQO> pageParam);


    /**
     * 根据账号判断该账号是否存在
     * @param username
     * @return
     */
    boolean getAdminUsername(String username);


    /**
     * 添加管理员
     * @param adminSaveDto
     * @param unifyAdmin
     * @return
     */
    Boolean addAdmin(AdminSaveDto adminSaveDto, UnifyAdmin unifyAdmin);


    /**
     * 更新管理员
     * @param adminUpdateDto
     * @param unifyAdmin
     * @return
     */
    Boolean updateAdmin(AdminUpdateDto adminUpdateDto, UnifyAdmin unifyAdmin);


    /**
     * 删除指定管理员
     * @param id
     * @return
     */
    Boolean deleteAdmin(Long id);





//<-----------------------管理员角色的获取及操作---------------------------->



    AdminDto getAdminRole(AdminBean adminBean);


    /**
     * 根据管理员列表，获得管理员角色
     * @param adminBeans
     * @return
     */
    List<AdminDto> getAdminRole(List<AdminBean> adminBeans);



    /**
     * 添加管理员角色
     * @param roleId
     * @param adminId
     * @param unifyAdmin
     * @return
     */
    Boolean addAdminRole(List<Long> roleId, Long adminId, UnifyAdmin unifyAdmin);


    /**
     * 更新管理员角色
     * @param roleId
     * @param adminId
     * @param unifyAdmin
     * @return
     */
    Boolean updateAdminRole(List<Long> roleId,Long adminId,UnifyAdmin unifyAdmin);




    /**
     * 删除管理员角色
     * @param adminId
     * @return
     */
    Boolean deleteAdminRole(Long adminId);


}
