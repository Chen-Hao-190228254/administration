package com.skm.exa.service.biz;

import com.skm.exa.domain.bean.UserBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.dto.UserDto;
import com.skm.exa.persistence.qo.UserQO;
import com.skm.exa.service.BaseService;

/**
 * @author dhc
 * 2019-03-07 15:50
 */
public interface UserService extends BaseService<UserBean> {
    Page<UserDto> selectDtoPage(PageParam<UserQO> pageParam);
}
