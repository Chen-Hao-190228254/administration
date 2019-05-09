package com.skm.exa.persistence.dao;

import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.domain.bean.UserCodesBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.BaseDao;
import com.skm.exa.persistence.dto.UserCodesDto;
import com.skm.exa.persistence.qo.UserCodesLikeQO;

public interface UserCodesDao extends BaseDao<UserCodesBean> {
    /**
     * 代码分页
     * @param qoPageParam
     * @return
     */
    Page<UserCodesDto> pageCodes (PageParam<UserCodesLikeQO> qoPageParam);

    /**
     * 添加
     * @param userCodesBean
     * @return
     */
    int addCodes (UserCodesBean userCodesBean);
}
