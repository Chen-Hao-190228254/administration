package com.skm.exa.service.biz.impl;

import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.object.UnifyUser;
import com.skm.exa.common.service.UnifyUserService;
import com.skm.exa.domain.bean.UserManagementBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.mybatis.enums.UserManagementStatusEnum;
import com.skm.exa.persistence.dao.UserManagementDao;
import com.skm.exa.persistence.dto.UserManagementDto;
import com.skm.exa.persistence.qo.UserManagementLikeQO;
import com.skm.exa.service.BaseServiceImpl;
import com.skm.exa.service.biz.UserManagementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.Date;



@Service
public class UserManagementImpl extends BaseServiceImpl<UserManagementBean , UserManagementDao> implements UserManagementService , UnifyUserService {
    @Override   //loadUserByUsername  按用户名加载用户
    public UnifyUser loadUserByUsername(String username) {

        return null;
    }

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
        userManagementBean.setEntryId(unifyAdmin.getId());
        userManagementBean.setEntryName(unifyAdmin.getName());
        userManagementBean.setEntryDt(new Date());
        userManagementBean.setUpdateId(unifyAdmin.getId());
        userManagementBean.setUpdateName(unifyAdmin.getName());
        userManagementBean.setUpdateDt(new Date());
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
        userManagementBean.setEntryId(unifyAdmin.getId());
        userManagementBean.setEntryName(unifyAdmin.getName());
        userManagementBean.setEntryDt(new Date());
        userManagementBean.setUpdateId(unifyAdmin.getId());
        userManagementBean.setUpdateName(unifyAdmin.getName());
        userManagementBean.setUpdateDt(new Date());
        dao.updateManagement(userManagementBean);
            return userManagementBean;
    }

    /**
     * 通过id删除
     * @param userManagementBeanClass
     * @param id
     * @return
     */
    @Override
    public Integer delete(Class<UserManagementBean> userManagementBeanClass, Long id) {
        return dao.deleteManagement(UserManagementBean.class, id);
    }

    /**
     * 通过id获取数据
     * @param
     * @param id
     * @return
     */
    @Override
    public UserManagementBean details(UserManagementBean userManagementBean ,Long id ) {
         UserManagementBean  beans = dao.detailsManagement(id);
        /* if (beans.getStatus() == UserManagementStatusEnum.NORMAL.getValue()){  //判断当前状态
             dao.updateStatus(userManagementBean);
             System.out.println(beans.getStatus().toString()+" = "+ UserManagementStatusEnum.NORMAL.getValue());
             return beans;
         }*/
            System.out.println(beans.getStatus());

            return beans;
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
            System.out.println(userManagementBean.getStatus().toString()+"=" + UserManagementStatusEnum.FORBIDDEN.getValue());
            return userManagementBean;
        }
        if (userManagementBean.getStatus() == UserManagementStatusEnum.FORBIDDEN.getValue()){
            System.out.println("当前状态是禁用");
            System.out.println(userManagementBean.getStatus().toString()+"=" + UserManagementStatusEnum.FORBIDDEN.getValue());
            dao.updateStatus(userManagementBean);
            return userManagementBean;
        }
        if (userManagementBean.getStatus() == UserManagementStatusEnum.VOID.getValue()){

            System.out.println("当前状态是无效");
            return null ;
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
            dao.updatePassword(userManagementBean);
            return userManagementBean;
    }


}
