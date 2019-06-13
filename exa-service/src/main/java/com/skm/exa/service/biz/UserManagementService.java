package com.skm.exa.service.biz;

import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.domain.bean.UserManagementBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.dto.UserManagementAddDto;
import com.skm.exa.persistence.dto.UserManagementDto;
import com.skm.exa.persistence.dto.UserManagementUpdateDto;
import com.skm.exa.persistence.dto.UserManagementUpdatePasswordDto;
import com.skm.exa.persistence.qo.UserManagementLikeQO;
import com.skm.exa.service.BaseService;
import org.omg.CORBA.LongHolder;

import java.util.List;


public interface UserManagementService extends BaseService<UserManagementBean> {
    /**
     *   分页
     * @param userManagementLikeQoPage
     * @return
     */
    Page<UserManagementDto> selectDtoPage(PageParam<UserManagementLikeQO> userManagementLikeQoPage);

    /***
     *  添加用户
     * @param userManagementAddDto
     * @param
     * @return
     */
    Boolean add (UserManagementAddDto userManagementAddDto, UnifyAdmin unifyAdmin);

    /**
     *  更新用户
     * @param userManagementUpdateDto
     * @param unifyAdmin
     * @return
     */
    Boolean update(UserManagementUpdateDto userManagementUpdateDto , UnifyAdmin unifyAdmin);

    /**
     *  通过id删除用户
     * @param
     * @param userManagementBean
     * @return
     */
    boolean delete(UserManagementBean userManagementBean);

    /**
     * 通过id获取数据
     * @param
     * @param
     * @return
     */
    UserManagementDto details(Long id );
    /**
     * 更改用户状态
     * @param userManagementBean
     * @param unifyAdmin
     * @return
     */
    UserManagementBean updateStatus (UserManagementBean userManagementBean ,UnifyAdmin unifyAdmin);

    /***
     * 更改用户密码
     * @param updatePasswordDto
     * @param unifyAdmin
     * @return
     */
    int updatePassword(UserManagementUpdatePasswordDto updatePasswordDto , UnifyAdmin unifyAdmin);

    /**
     * 获取所有数据
     * @param userManagementBean
     * @return
     */
    List<UserManagementBean> selectManagement (UserManagementBean userManagementBean);

    /**
     * 判断唯一值
     * @param accountNumber
     * @return
     */
    Boolean judgeUnique(String accountNumber);
}
