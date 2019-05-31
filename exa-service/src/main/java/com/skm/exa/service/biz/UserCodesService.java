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

    /**
     * 通过id获取数据
     * @param
     * @param userCodesBean
     * @return
     */
    UserCodesBean details(UserCodesBean userCodesBean );

    /**
     * 通过id修改数据
     * @param userCodesBean
     * @param unifyAdmin
     * @return
     */
    UserCodesBean update(UserCodesBean userCodesBean ,UnifyAdmin unifyAdmin );

    /**
     * 通过id删除
     * @param
     * @param id
     * @return
     */
    boolean deleteCodes(Long id);

    /**
     * 更改状态
     * @param userCodesBean
     * @param
     * @return
     */
    UserCodesBean updateStatus(UserCodesBean userCodesBean );

    /**
     * 更改可编辑状态
     * @param userCodesBean
     * @param
     * @return
     */
    UserCodesBean updateEditStatus(UserCodesBean userCodesBean);
}
