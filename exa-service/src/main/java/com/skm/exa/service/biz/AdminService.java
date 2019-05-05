package com.skm.exa.service.biz;

import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.domain.bean.AdminBean;
import com.skm.exa.persistence.dto.AdminSaveDto;
import com.skm.exa.persistence.dto.AdminUpdateDto;
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
     * 添加管理员
     * @param adminSaveDto
     * @param unifyAdmin
     * @return
     */
    Result<AdminBean> addAdmin(AdminSaveDto adminSaveDto, UnifyAdmin unifyAdmin);


    AdminBean upAdmin(AdminUpdateDto adminUpDto, UnifyAdmin unifyAdmin);

}
