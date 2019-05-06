package com.skm.exa.persistence.dao;


import com.skm.exa.domain.bean.UserManagementBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.BaseDao;
import com.skm.exa.persistence.dto.UserManagementDto;
import com.skm.exa.persistence.qo.UserManagementLikeQO;

public interface UserManagementDao extends BaseDao<UserManagementBean> {
   /**
    * 分页
    * @param userManagementLikeQoPageParam
    * @return
    */
   Page<UserManagementDto> getManagementDtoPage(PageParam<UserManagementLikeQO> userManagementLikeQoPageParam);

}
