package com.skm.exa.service.biz;


import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.domain.bean.RoleBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.dto.RoleSaveDto;
import com.skm.exa.persistence.dto.RoleUpdateDto;
import com.skm.exa.persistence.qo.RoleQO;

import java.util.List;

public interface RoleService {

    RoleBean getRole(Long id);

    List<RoleBean> getRoleList();

    Page<RoleBean> getRolePage(PageParam<RoleQO> qo);

    Result<RoleBean> addRole(RoleSaveDto roleSaveDto, UnifyAdmin unifyAdmin);

    Result<RoleBean> updateRole(RoleUpdateDto roleUpdateDto, UnifyAdmin unifyAdmin);

    Boolean deleteRole(Long id);



    List<RoleBean> getRoleAuthority(List<RoleBean> roleBeans);
}
