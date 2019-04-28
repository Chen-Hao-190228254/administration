package com.skm.exa.common.service;

import com.skm.exa.common.object.UnifyUser;

/**
 * @author dhc
 * 2019-03-13 14:11
 */
public interface UnifyUserService {
    /**
     * 根据用户名获取用户基本信息
     *
     * @param username 用户名
     * @return 用户基本信息对象或 null
     */
    UnifyUser loadUserByUsername(String username);
}
