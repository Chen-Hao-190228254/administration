package com.skm.exa.persistence.dao;


import com.skm.exa.domain.bean.UserCodesBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.BaseDao;
import com.skm.exa.persistence.dto.UserCodesDto;
import com.skm.exa.persistence.qo.UserCodesLikeQO;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 通过id获取数据
     * @param id
     * @return
     */
    UserCodesBean details(@Param("id") Long  id);

    /**
     * 修改数据
     * @param userCodesBean
     * @return
     */
    int updateCodes (UserCodesBean userCodesBean);

    /**
     * 通过id删除数据
     * @param id
     * @return
     */
    Integer deleteCodes(@Param("id") Long id);

    /**
     * 更改状态
     * @param userCodesBean
     * @return
     */
    Integer updateStatus(UserCodesBean userCodesBean);

    /**
     * 更改可编辑状态
     * @param userCodesBean
     * @return
     */
    Integer updateEditStatus(UserCodesBean userCodesBean);
}
