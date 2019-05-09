package com.skm.exa.webapi.controller;


import com.skm.exa.common.object.Result;

import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.domain.bean.UserCodesBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.mybatis.SearchConditionGroup;
import com.skm.exa.persistence.dto.UserCodesDto;
import com.skm.exa.persistence.qo.UserCodesLikeQO;
import com.skm.exa.service.biz.UserCodesService;
import com.skm.exa.webapi.BaseController;
import com.skm.exa.webapi.vo.UserCodesQueryVO;
import com.skm.exa.webapi.vo.UserCodesSaveVO;
import com.skm.exa.webapi.vo.UserCodesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/web/v1/codes")
public class UserCodesController extends BaseController {
    @Autowired
    private UserCodesService userCodesService;

    /**
     *  代码分页
     * @param queryVOPageParam
     * @return
     */
    @PostMapping("/page")
    public Result<Page<UserCodesVO>>  pageResult (@RequestBody PageParam<UserCodesQueryVO> queryVOPageParam){
        UserCodesQueryVO userCodesQueryVO = queryVOPageParam.getCondition();
        UserCodesLikeQO userCodesLikeQO = new UserCodesLikeQO();
        userCodesLikeQO.addSearchConditionGroup(SearchConditionGroup
                .buildMultiColumnsSearch(userCodesQueryVO.getKeyword()));
        PageParam<UserCodesLikeQO> pageParam = new PageParam<>();
        pageParam.setCondition(userCodesLikeQO); //设置状态
        userCodesLikeQO.setCodeNameLike(userCodesQueryVO.getKeyword());
        userCodesLikeQO.setCodesLike(userCodesQueryVO.getKeyword());
        Page<UserCodesDto> dtoPage = userCodesService.page(pageParam);
        Page<UserCodesVO> voPage = dtoPage.map(UserCodesDto.class, UserCodesVO.class);
        return Result.success(voPage);
    }

    /**
     *  添加
     * @param userCodesSaveVO
     * @return
     */
    @PostMapping("/add")
    public Result<UserCodesBean> add (@RequestBody UserCodesSaveVO userCodesSaveVO){
        UnifyAdmin unifyAdmin = getCurrentAdmin();
        UserCodesBean userCodesBean = BeanMapper.map(userCodesSaveVO,UserCodesBean.class );
        userCodesBean = userCodesService.add(userCodesBean, unifyAdmin);
        return Result.success(userCodesBean);
    }

}
