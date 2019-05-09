package com.skm.exa.service.biz;

import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.domain.bean.UserCodesBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.dto.UserCodesDto;
import com.skm.exa.persistence.qo.UserCodesLikeQO;
import com.skm.exa.service.BaseService;

public interface UserCodesService extends BaseService<UserCodesBean> {
    /**
     * 代码分页
     * @param qoPageParam
     * @return
     */
    Page<UserCodesDto> page (PageParam<UserCodesLikeQO> qoPageParam);

    /**
     * 添加
     * @param userCodesBean
     * @param unifyAdmin
     * @return
     */
    UserCodesBean add (UserCodesBean userCodesBean , UnifyAdmin unifyAdmin);
}
