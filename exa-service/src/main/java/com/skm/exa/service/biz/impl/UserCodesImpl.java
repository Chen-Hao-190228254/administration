package com.skm.exa.service.biz.impl;

import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.object.UnifyUser;
import com.skm.exa.common.service.UnifyUserService;
import com.skm.exa.domain.bean.UserCodesBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.dao.UserCodesDao;
import com.skm.exa.persistence.dto.UserCodesDto;
import com.skm.exa.persistence.qo.UserCodesLikeQO;
import com.skm.exa.service.BaseServiceImpl;
import com.skm.exa.service.biz.UserCodesService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserCodesImpl extends BaseServiceImpl<UserCodesBean , UserCodesDao> implements UserCodesService , UnifyUserService {
    @Override
    public UnifyUser loadUserByUsername(String username) {
        return null;
    }

    /**
     * 代码分页
     * @param qoPageParam
     * @return
     */
    @Override
    public Page<UserCodesDto> page(PageParam<UserCodesLikeQO> qoPageParam) {
        return dao.pageCodes(qoPageParam);
    }

    /**
     * 添加
     * @param userCodesBean
     * @param unifyAdmin
     * @return
     */
    @Override
    public UserCodesBean add(UserCodesBean userCodesBean, UnifyAdmin unifyAdmin) {
        userCodesBean.setEntryId(unifyAdmin.getId());
        userCodesBean.setEntryName(unifyAdmin.getName());
        userCodesBean.setEntryDt(new Date());
        userCodesBean.setUpdateId(unifyAdmin.getId());
        userCodesBean.setUpdateName(unifyAdmin.getName());
        userCodesBean.setUpdateDt(new Date());
        dao.addCodes(userCodesBean);
        return userCodesBean;
    }
}
