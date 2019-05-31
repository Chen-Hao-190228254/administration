package com.skm.exa.service.biz.impl;

import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.object.UnifyUser;
import com.skm.exa.common.service.UnifyUserService;
import com.skm.exa.common.utils.SetCommonElement;
import com.skm.exa.domain.bean.UserManagementBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.mybatis.enums.UserManagementStatusEnum;
import com.skm.exa.persistence.dao.UserManagementDao;
import com.skm.exa.persistence.dto.UserManagementDto;
import com.skm.exa.persistence.qo.UserManagementLikeQO;
import com.skm.exa.service.BaseServiceImpl;
import com.skm.exa.service.biz.UserManagementService;
import org.omg.CORBA.UNKNOWN;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.Date;



@Service
public class UserManagementImpl extends BaseServiceImpl<UserManagementBean , UserManagementDao> implements UserManagementService {

    /**
     *  UserManagement 分页查询
     * @param userManagementLikeQoPage  //分页
     * @return
     */
    @Override
    @Transactional
    public Page<UserManagementDto> selectDtoPage(PageParam<UserManagementLikeQO> userManagementLikeQoPage) {
        return dao.getManagementDtoPage(userManagementLikeQoPage);

    }

    /**
     * 添加用户
     * @param userManagementBean
     * @param
     * @return
     */
    @Override
    @Transactional
    public UserManagementBean add(UserManagementBean userManagementBean, UnifyAdmin unifyAdmin) {
        SetCommonElement setCommonElement = new SetCommonElement();
        setCommonElement.setAdd(userManagementBean, unifyAdmin);
        dao.addManagement(userManagementBean);
        return userManagementBean;
    }


    /**
     *  更新用户
     * @param userManagementBean
     * @param unifyAdmin
     * @return
     */
    @Override
    @Transactional
    public UserManagementBean update(UserManagementBean userManagementBean, UnifyAdmin unifyAdmin) {
        UserManagementBean  beans = dao.detailsManagement(userManagementBean);
        if (beans.getStatus() == UserManagementStatusEnum.NORMAL.getValue()){
           SetCommonElement setCommonElement = new SetCommonElement() ;
           setCommonElement.setupdate(userManagementBean,unifyAdmin );
            dao.updateManagement(userManagementBean);
            return userManagementBean;
        }
            return null;
    }

    /**
     * 通过id删除
     * @param
     * @param userManagementBean
     * @return
     */
    @Override
    public boolean  delete(UserManagementBean userManagementBean ) {
        UserManagementBean bean = dao.detailsManagement(userManagementBean);
        bean.getStatus();
        if (bean.getStatus() == UserManagementStatusEnum.NORMAL.getValue()){
            dao.deleteManagement(userManagementBean);
            return true ;
        }
            return false;
    }

    /**
     * 通过id获取数据
     * @param
     * @param userManagementBean
     * @return
     */
    @Override
    public UserManagementBean details(UserManagementBean userManagementBean ) {
            return dao.detailsManagement(userManagementBean);
    }

    /***
     * 通过id 更该角色状态
     * @param userManagementBean
     * @param unifyAdmin
     * @return
     */
    @Override
    public UserManagementBean updateStatus(UserManagementBean userManagementBean, UnifyAdmin unifyAdmin) {
        if ( userManagementBean.getStatus() == UserManagementStatusEnum.NORMAL.getValue()){
            System.out.println("当前状态是正常");
            dao.updateStatus(userManagementBean);
            return userManagementBean;
        }
        if (userManagementBean.getStatus() == UserManagementStatusEnum.FORBIDDEN.getValue()){
            System.out.println("当前状态是禁用");
            dao.updateStatus(userManagementBean);
            return userManagementBean;
        }
        if (userManagementBean.getStatus() == UserManagementStatusEnum.VOID.getValue()){
            System.out.println("当前状态是无效");
            dao.updateStatus(userManagementBean);
            return userManagementBean;
        }
            return null;
    }

    /**
     * 更改密码
     * @param userManagementBean
     * @param unifyAdmin
     * @return
     */
    @Override
    public UserManagementBean updatePassword(UserManagementBean userManagementBean, UnifyAdmin unifyAdmin) {
        UserManagementBean bean = dao.detailsManagement(userManagementBean);
        if (bean .getStatus() == UserManagementStatusEnum.NORMAL.getValue()){
            dao.updatePassword(userManagementBean);
            return userManagementBean;
        }
            return null ;
    }


}
