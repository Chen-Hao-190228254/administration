package com.skm.exa.service.biz.impl;

import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.utils.SetCommonElement;
import com.skm.exa.domain.bean.UserCodesBean;
import com.skm.exa.domain.bean.UserManagementBean;
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

import java.util.List;

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
        SetCommonElement setCommonElement = new SetCommonElement();
        setCommonElement.setAdd(userCodesBean,unifyAdmin );
        dao.addCodes(userCodesBean);
        return userCodesBean;
    }

    /**
     * 通过id获取数据
     * @param
     * @param userCodesBean
     * @return
     */
    @Override
    public UserCodesBean details(UserCodesBean userCodesBean ) {
            return dao.details(userCodesBean);

    }

    /**
     * 通过id修改数据
     * @param userCodesBean
     * @param unifyAdmin
     * @return
     */
    @Override
    public UserCodesBean update(UserCodesBean userCodesBean, UnifyAdmin unifyAdmin) {
        UserCodesBean bean = dao.details(userCodesBean);
        if (bean.getEditStatus() != null){
            if (bean.getEditStatus() == UserCodesEditStatusEnum.EDIT.getValue() ){
                SetCommonElement setCommonElement = new SetCommonElement();
                setCommonElement.setupdate(userCodesBean,unifyAdmin );
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
    public boolean deleteCodes( Long id) {
        dao.deleteCodes(id);
        return true;
    }

    /**
     * 更改状态
     * @param userCodesBean
     * @param
     * @return
     */
    @Override
    public UserCodesBean updateStatus(UserCodesBean userCodesBean ) {
        dao.updateStatus(userCodesBean);
            return userCodesBean ;


    }

    /**
     * 更改可编辑状态
     * @param userCodesBean
     * @param userCodesBean
     * @return
     */
    @Override
    public UserCodesBean updateEditStatus(UserCodesBean userCodesBean) {
        dao.updateEditStatus(userCodesBean);
        return userCodesBean;
    }
    @Override
    public Boolean judgeUnique(String codes) {
        List<UserCodesBean> userCodesBeans = dao.select(new UserCodesBean(codes) );
        return userCodesBeans != null && userCodesBeans.size() > 0;
    }
}
