package com.skm.exa.service.biz;

import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.domain.bean.UserManagementBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.dto.UserManagementDto;
import com.skm.exa.persistence.qo.UserManagementLikeQO;
import com.skm.exa.service.BaseService;



public interface UserManagementService extends BaseService<UserManagementBean> {
    /**
     *   分页
     * @param userManagementLikeQoPage
     * @return
     */
    Page<UserManagementDto> selectDtoPage(PageParam<UserManagementLikeQO> userManagementLikeQoPage);

    /***
     *  添加用户
     * @param userManagementBean
     * @param
     * @return
     */
    UserManagementBean add (UserManagementBean userManagementBean, UnifyAdmin unifyAdmin);

    /**
     *  更新用户
     * @param userManagementBean
     * @param unifyAdmin
     * @return
     */
    UserManagementBean update(UserManagementBean userManagementBean ,UnifyAdmin unifyAdmin);

    /**
     *  通过id删除用户
     * @param
     * @param id
     * @return
     */
    Integer delete(Long id);

    /**
     * 通过id获取数据
     * @param
     * @param id
     * @return
     */
    UserManagementBean details( Long id );
    /**
     * 更改用户状态
     * @param userManagementBean
     * @param unifyAdmin
     * @return
     */
    UserManagementBean updateStatus (UserManagementBean userManagementBean ,UnifyAdmin unifyAdmin);

    /***
     * 更改用户密码
     * @param userManagementBean
     * @param unifyAdmin
     * @return
     */
    UserManagementBean updatePassword(UserManagementBean userManagementBean ,UnifyAdmin unifyAdmin);
}
