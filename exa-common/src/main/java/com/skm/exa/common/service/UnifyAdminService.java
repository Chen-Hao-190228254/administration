package com.skm.exa.common.service;

import com.skm.exa.common.object.UnifyAdmin;

public interface UnifyAdminService {

    /**
     * 根据用户名获取用户基本信息
     *
     * @param username 用户名
     * @return 用户基本信息对象或 null
     */
    UnifyAdmin loadAdminByUsername(String username);



}
