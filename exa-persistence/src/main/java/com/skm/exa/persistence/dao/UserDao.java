package com.skm.exa.persistence.dao;

import com.skm.exa.domain.bean.UserBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.BaseDao;
import com.skm.exa.persistence.dto.UserDto;
import com.skm.exa.persistence.qo.UserQO;

/**
 * @author dhc
 * 2019-03-07 12:04
 */
public interface UserDao extends BaseDao<UserBean> {
    Page<UserDto> getDtoPage(PageParam<UserQO> pageParam);
}
