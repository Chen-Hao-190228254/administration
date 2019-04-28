package com.skm.exa.service.biz.impl;

import com.skm.exa.common.object.UnifyUser;
import com.skm.exa.common.service.UnifyUserService;
import com.skm.exa.domain.bean.UserBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.dao.UserDao;
import com.skm.exa.persistence.dto.UserDto;
import com.skm.exa.persistence.qo.UserQO;
import com.skm.exa.service.BaseServiceImpl;
import com.skm.exa.service.biz.UserService;
import org.springframework.stereotype.Service;

/**
 * @author dhc
 * 2019-03-07 15:47
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserBean, UserDao> implements UserService, UnifyUserService {

    @Override
    public Page<UserDto> selectDtoPage(PageParam<UserQO> pageParam) {
        return dao.getDtoPage(pageParam);
    }

    @Override
    public UnifyUser loadUserByUsername(String username) {
        UserQO qo = new UserQO();
        qo.setUsername(username);
        UserBean user = super.find(qo);
        if (user == null) return null;

        UnifyUser unifyUser = new UnifyUser(user.getUsername(), user.getPassword());
        unifyUser.setId(user.getId());
        unifyUser.setRealname(user.getRealname());
        unifyUser.setAvatar(user.getAvatar());
        return unifyUser;
    }
}
