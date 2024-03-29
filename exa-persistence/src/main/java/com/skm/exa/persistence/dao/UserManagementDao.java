package com.skm.exa.persistence.dao;



import com.skm.exa.domain.bean.UserManagementBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.BaseDao;
import com.skm.exa.persistence.dto.UserManagementDto;
import com.skm.exa.persistence.dto.UserManagementUpdatePasswordDto;
import com.skm.exa.persistence.qo.UserManagementLikeQO;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserManagementDao extends BaseDao<UserManagementBean> {
   /**
    * 分页
    * @param userManagementLikeQoPageParam
    * @return
    */
   Page<UserManagementBean> getManagementDtoPage(PageParam<UserManagementLikeQO> userManagementLikeQoPageParam);

   /**
    *  添加用户
    * @param userManagementBean
    * @return
    */
   int addManagement(UserManagementBean userManagementBean);

   /**
    *    更新用户
    * @param userManagementBean
    * @return
    */
   int updateManagement(UserManagementBean userManagementBean);

   /**
    * 通过id删除用户
    * @param
    * @param userManagementBean
    * @return
    */
   int deleteManagement(UserManagementBean userManagementBean);

   /***
    * 通过id 查询
    * @param id
    * @return
    */
   UserManagementBean detailsManagement(long id );
   /**
    * 通过id 更改角色状态
    * @param userManagementBean
    * @return
    */
   int updateStatus(UserManagementBean userManagementBean);

   /**
    * 获取所有数据
    * @param userManagementBean
    * @return
    */
   List<UserManagementBean> selectManagement(UserManagementBean userManagementBean);
   /**
    *  更改密码
    * @param updatePasswordDto
    * @return
    */
   int updatePassword(UserManagementUpdatePasswordDto updatePasswordDto);

}
