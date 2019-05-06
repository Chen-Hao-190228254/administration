package com.skm.exa.service.biz;

import com.skm.exa.common.object.UnifyUser;
import com.skm.exa.domain.bean.UserManagementBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.dto.UserManagementDto;
import com.skm.exa.persistence.qo.UserManagementLikeQO;
import com.skm.exa.service.BaseService;

import java.util.List;

public interface UserManagementService extends BaseService<UserManagementBean> {
    /**
     *   分页
     * @param userManagementLikeQoPage
     * @return
     */
    Page<UserManagementDto> selectDtoPage(PageParam<UserManagementLikeQO> userManagementLikeQoPage);

    /**
     *   添加用户
     * @param userManagementBeans
     * @param unifyUser
     * @return
     */
    List<UserManagementDto> add (List<UserManagementBean> userManagementBeans , UnifyUser unifyUser);
}
