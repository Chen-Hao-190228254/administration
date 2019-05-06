package com.skm.exa.service.biz;

import com.skm.exa.common.object.Result;
import com.skm.exa.domain.bean.AdminBean;
import com.skm.exa.persistence.dto.AdminSaveDto;
import com.skm.exa.service.BaseService;

import java.util.List;

public interface AdminService extends BaseService<AdminBean> {

    List<AdminBean> getAdminList();

    Result<AdminBean> addAdmin(AdminSaveDto adminSaveDto);

}
