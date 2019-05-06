package com.skm.exa.service.biz.impl;

import com.skm.exa.common.object.UnifyUser;
import com.skm.exa.common.service.UnifyUserService;
import com.skm.exa.domain.bean.UserManagementBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.dao.UserManagementDao;
import com.skm.exa.persistence.dto.UserManagementDto;
import com.skm.exa.persistence.qo.UserManagementLikeQO;
import com.skm.exa.service.BaseServiceImpl;
import com.skm.exa.service.biz.UserManagementService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserManagementImpl extends BaseServiceImpl<UserManagementBean , UserManagementDao> implements UserManagementService , UnifyUserService {
    @Override   //loadUserByUsername  按用户名加载用户
    public UnifyUser loadUserByUsername(String username) {
        return null;
    }

    /**
     *  UserManagement 分页查询
     * @param userManagementLikeQoPage  //分页
     * @return
     */
    @Override
    public Page<UserManagementDto> selectDtoPage(PageParam<UserManagementLikeQO> userManagementLikeQoPage) {
        System.out.println("数据传输到Impl");
        return dao.getManagementDtoPage(userManagementLikeQoPage);
    }

    @Override
    public List<UserManagementDto> add(List<UserManagementBean> userManagementBeans, UnifyUser unifyUser) {
        for (UserManagementBean userManagementBean : userManagementBeans){
            userManagementBean.setEntryDt(new Date());
            userManagementBean.setEntryId(unifyUser.getId());
            userManagementBean.setEntryName(unifyUser.getUsername());
        }
        return null;
    }

}
