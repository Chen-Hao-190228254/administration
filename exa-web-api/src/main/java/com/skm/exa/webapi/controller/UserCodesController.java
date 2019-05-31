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
import com.skm.exa.webapi.vo.*;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Api(tags = "代码管理" ,description = "代码管理接口")
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
    @ApiOperation(notes = "分页模糊查询获取代码管理",value = "分页模糊查询获取代码管理")
    public Result<Page<UserCodesVO>>  pageResult (@ApiParam("queryVOPageParam")
                                                          @RequestBody PageParam<UserCodesQueryVO> queryVOPageParam){
        UserCodesQueryVO userCodesQueryVO = queryVOPageParam.getCondition();
        UserCodesLikeQO userCodesLikeQO = new UserCodesLikeQO();
        userCodesLikeQO.addSearchConditionGroup(SearchConditionGroup
                .buildMultiColumnsSearch(userCodesQueryVO.getKeyword()));
        PageParam<UserCodesLikeQO> pageParam = new PageParam<>(queryVOPageParam.getPage(),queryVOPageParam.getSize());
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
    @Transactional
    @PostMapping("/add")
    @ApiOperation(notes = "添加代码",value = "添加代码")
    public Result<UserCodesBean> add (@ApiParam( name = "userCodesSaveVO")@RequestBody UserCodesSaveVO userCodesSaveVO){
        UnifyAdmin unifyAdmin = getCurrentAdmin();
        UserCodesBean userCodesBean = BeanMapper.map(userCodesSaveVO,UserCodesBean.class );
        userCodesBean = userCodesService.add(userCodesBean, unifyAdmin);
        return Result.success(userCodesBean);
    }

    /**
     * 通过id获取数据
     * @param id
     * @return
     */
    @Transactional
    @PostMapping("/details")
    @ApiOperation(notes = "通过id查询",value = "通过id查询")
    public Result details(@ApiParam("id")@RequestParam ("getId") Long id){
        UserCodesBean userCodesBean = new UserCodesBean();
        userCodesBean.setId(id);
        UserCodesBean details = userCodesService.details(userCodesBean);
        UserCodesVO codesVO = BeanMapper.map(details,UserCodesVO.class );
        return Result.success(codesVO) ;
    }

    /**
     * 通过id修改数据
     * @param userCodesUpdateVO
     * @return
     */
    @Transactional
    @PostMapping("/update")
    @ApiOperation(notes = "通过id修改数据",value = "通过id修改数据")
    public Result<UserCodesBean> updateCodes(@ApiParam("userCodesUpdateVO")@RequestBody UserCodesUpdateVO userCodesUpdateVO){
        UnifyAdmin unifyAdmin = getCurrentAdmin();
        UserCodesBean userCodesBean = BeanMapper.map(userCodesUpdateVO,UserCodesBean.class );
        userCodesBean = userCodesService.update(userCodesBean,unifyAdmin );
        return Result.success(userCodesBean);
    }

    /**
     * 通过id删除
     * @param id
     * @return
     */
    @Transactional
    @PostMapping("/delete")
    @ApiOperation(notes = "通过id删除" ,value = "通过id删除")
    public Result deleteCodes(@ApiParam("id")@RequestParam ("getId") Long id){
        UserCodesBean userCodesBean = new UserCodesBean();
        userCodesBean.setId(id);
        boolean codesBean = userCodesService.deleteCodes(id);
        UserCodesVO userCodesVO = BeanMapper.map(codesBean, UserCodesVO.class);
        return Result.success(userCodesVO);
    }


    /**
     * 更改状态
     * @param vo
     * @return
     */
    @PostMapping("/updateStatus")
    @ApiOperation(notes = "更改状态" ,value = "更改状态")
    public Result updateStatus(@ApiParam("vo") @RequestBody UserCodesUpdateStatusVo vo){
        UserCodesBean userCodesBean = BeanMapper.map(vo,UserCodesBean.class );
        UserCodesBean  updateStatus = userCodesService.updateStatus(userCodesBean);
        UserCodesUpdateStatusVo statusVo = BeanMapper.map(updateStatus,UserCodesUpdateStatusVo.class );
        return Result.success(statusVo);
    }

    /***
     * 更改可编辑状态
     * @param vo
     * @return
     */
    @PostMapping("/updateEditStatus")
    @ApiOperation(notes = "更改可编辑状态" , value= "更改可编辑状态")
    public Result updateEditStatus( @ApiParam("vo")@RequestBody UserCodesUpdateEditStatusVo vo){
        UserCodesBean userCodesBean = BeanMapper.map(vo,UserCodesBean.class );
        UserCodesBean bean = userCodesService.updateEditStatus(userCodesBean );
        UserCodesUpdateEditStatusVo userCodesVO = BeanMapper.map(bean,UserCodesUpdateEditStatusVo.class );
        return Result.success(userCodesVO);
    }
}
