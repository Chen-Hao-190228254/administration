package com.skm.exa.service.biz.impl;

import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.domain.bean.UserCodesBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.mybatis.enums.UserCodesEditStatusEnum;
import com.skm.exa.mybatis.enums.UserCodesStatus;
import com.skm.exa.persistence.dao.UserCodesDao;
import com.skm.exa.persistence.dto.UserCodesDto;
import com.skm.exa.persistence.qo.UserCodesLikeQO;
import com.skm.exa.service.BaseServiceImpl;
import com.skm.exa.service.biz.UserCodesService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserCodesImpl extends BaseServiceImpl<UserCodesBean , UserCodesDao> implements UserCodesService {

    /**
     * 代码分页
     * @param qoPageParam
     * @return
     */
    @Override
    public Page<UserCodesDto> page(PageParam<UserCodesLikeQO> qoPageParam) {
        return dao.pageCodes(qoPageParam);
    }

    /**
     * 添加
     * @param userCodesBean
     * @param unifyAdmin
     * @return
     */
    @Override
    public UserCodesBean add(UserCodesBean userCodesBean, UnifyAdmin unifyAdmin) {
        userCodesBean.setEntryId(unifyAdmin.getId());
        userCodesBean.setEntryName(unifyAdmin.getName());
        userCodesBean.setEntryDt(new Date());
        userCodesBean.setUpdateId(unifyAdmin.getId());
        userCodesBean.setUpdateName(unifyAdmin.getName());
        userCodesBean.setUpdateDt(new Date());
        dao.addCodes(userCodesBean);
        return userCodesBean;
    }

    /**
     * 通过id获取数据
     * @param
     * @param id
     * @return
     */
    @Override
    public UserCodesBean details(Long id) {
            return dao.details(id);

    }

    /**
     * 通过id修改数据
     * @param userCodesBean
     * @param unifyAdmin
     * @return
     */
    @Override
    public UserCodesBean update(UserCodesBean userCodesBean, UnifyAdmin unifyAdmin) {
        UserCodesBean bean = dao.details(userCodesBean.getId());
        if (bean.getEditStatus() != null){
            if (bean.getEditStatus() == UserCodesEditStatusEnum.EDIT.getValue() ){
                userCodesBean.setEntryId(unifyAdmin.getId());
                userCodesBean.setEntryName(unifyAdmin.getName());
                userCodesBean.setEntryDt(new Date());
                userCodesBean.setUpdateId(unifyAdmin.getId());
                userCodesBean.setUpdateName(unifyAdmin.getName());
                userCodesBean.setUpdateDt(new Date());
                dao.updateCodes(userCodesBean);
                return userCodesBean;
            }else {
                return null;
            }
        }
        return null;
    }

    /**
     * 通过id删除
     * @param
     * @param id
     * @return
     */
    @Override
    public Integer deleteCodes( Long id) {
        return dao.deleteCodes(id);
    }

    /**
     * 更改状态
     * @param userCodesBean
     * @param id
     * @return
     */
    @Override
    public Integer updateStatus(UserCodesBean userCodesBean ,Long id) {
        UserCodesBean bean = dao.details(id);
        if (bean.getStatus() != null ){
            if (bean.getStatus() == UserCodesStatus.NORMAL.getValue()){
                userCodesBean.setStatus((long) 1);
                System.out.println(userCodesBean.getStatus().toString());
                return dao.updateStatus(userCodesBean);
            }
            if(bean.getStatus() == UserCodesStatus.FORBIDDEN.getValue()){
                userCodesBean.setStatus((long) 0 );
                return dao.updateStatus(userCodesBean);
            }
        }
                return null;
    }

    /**
     * 更改可编辑状态
     * @param userCodesBean
     * @param id
     * @return
     */
    @Override
    public Integer updateEditStatus(UserCodesBean userCodesBean, Long id) {
        UserCodesBean bean = dao.details(id);
        if (bean.getEditStatus() != null){
           if (bean.getEditStatus() == UserCodesEditStatusEnum.EDIT.getValue()){
               userCodesBean.setEditStatus((long) 1);
               return dao.updateEditStatus(userCodesBean);
           }
            if (bean.getEditStatus() == UserCodesEditStatusEnum.NO_EDIT.getValue()){
                userCodesBean.setEditStatus((long) 0);
                return dao.updateEditStatus(userCodesBean);
            }
        }
        return null;
    }
}
