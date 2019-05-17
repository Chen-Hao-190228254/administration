package com.skm.exa.webapi.controller;


import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.domain.bean.UserManagementBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.mybatis.SearchConditionGroup;
import com.skm.exa.persistence.dto.UserManagementDto;
import com.skm.exa.persistence.qo.UserManagementLikeQO;
import com.skm.exa.service.biz.UserManagementService;
import com.skm.exa.webapi.BaseController;
import com.skm.exa.webapi.vo.*;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户管理" ,description = "用户管理接口")
@RestController
@RequestMapping("/web/v1/userManagement")
public class UserManagementController extends BaseController {
    @Autowired
    private UserManagementService userManagementService ;

    /**
     *    分页查询
     * @param pageParam
     * @return
     */
    @PostMapping("/page")
    @ApiOperation(notes = "分页模糊查询获取用户信息",value = "分页模糊查询获取用户信息")
    public Result<Page<UserManagementVO>> pageResult
            (@ApiParam("分页模糊查询获取用户信息")@RequestBody PageParam<UserManagementQueryVO> pageParam){
        UserManagementQueryVO queryVO = pageParam.getCondition();
        UserManagementLikeQO qo = new UserManagementLikeQO();
        qo.addSearchConditionGroup(SearchConditionGroup //columns 列
                .buildMultiColumnsSearch(queryVO.getKeyword(),
                        "account_number","name","card","phone","native_place","email","qq","skill"));//search  搜索
            //转换并返回值给Service
        PageParam<UserManagementLikeQO> qoPageParam = new PageParam<>(pageParam.getPage(), pageParam.getSize());
        qoPageParam.setCondition(qo); //setCondition 设置状态
        qo.setAccountNumberLike(queryVO.getKeyword());
        qo.setCardLike(queryVO.getKeyword());
        qo.setEmailLike(queryVO.getKeyword());
        qo.setNameLike(queryVO.getKeyword());
        qo.setNativePlaceLike(queryVO.getKeyword());
        qo.setPhoneLike(queryVO.getKeyword());
        qo.setSkillLike(queryVO.getKeyword());
        qo.setQqLike(queryVO.getKeyword());
        System.out.println(qo.toString());
        Page<UserManagementDto> userManagementLikeQoPage = userManagementService
                .selectDtoPage(qoPageParam);
        Page<UserManagementVO> userManagementVoPage = userManagementLikeQoPage
                .map(UserManagementDto.class, UserManagementVO.class);
        return Result.success(userManagementVoPage);
    }

    /**
     *  添加用户
     * @param userManagementSaveVO
     * @return
     */
    @PostMapping("add")
    @ApiOperation(notes = "添加用户",value = "添加用户")
    public Result<UserManagementBean> add (@ApiParam("添加用户")@RequestBody UserManagementSaveVO userManagementSaveVO){
        UnifyAdmin unifyAdmin = getCurrentAdmin();
        UserManagementBean userManagementBean = BeanMapper.map(userManagementSaveVO, UserManagementBean.class);
        userManagementBean = userManagementService.add(userManagementBean, unifyAdmin);
        System.out.println(userManagementBean.toString());
        return Result.success(userManagementBean);
    }
    /**
     *  更新用户
     * @param userManagementUpdateVO
     * @return
     */
    @PostMapping("updateManagement")
    @ApiOperation(notes = "更新用户",value = "更新用户")
    public Result<UserManagementBean> update(@ApiParam("更新用户")@RequestBody UserManagementUpdateVO userManagementUpdateVO){
       UnifyAdmin unifyAdmin = getCurrentAdmin();
       UserManagementBean userManagementBean = BeanMapper.map(userManagementUpdateVO,UserManagementBean.class );
       userManagementBean = userManagementService.update(userManagementBean,unifyAdmin );
        return Result.success(userManagementBean);
    }

    /**
     *  通过ID 删除用户
     * @param id
     * @return
     */
    @PostMapping("deleteManagement")
    @ApiOperation(notes = "通过id删除用户",value = "通过id删除用户")
    public Result<UserManagementDeleteVO> delete (@ApiParam("通过id删除用户")@RequestParam ("id") Long id){
        UserManagementBean userManagementBean = new UserManagementBean();
        userManagementBean.setId(id);
        Integer delete = userManagementService.delete(userManagementBean,id );
        System.out.println("到达这里");
        UserManagementDeleteVO userManagementDeleteVO = BeanMapper.map(delete,UserManagementDeleteVO.class );

        return Result.success(userManagementDeleteVO);
    }

    /**
     *  通过id查询
     * @param id
     * @return
     */
    @PostMapping("details")
    @ApiOperation(notes = "通过id查询",value = "通过id查询")
    public Result details(@ApiParam("通过id查询")@RequestParam ("id") Long id){
        UserManagementBean userManagementBean = new UserManagementBean();
        userManagementBean.setId(id);
        UserManagementBean details = userManagementService.details(userManagementBean ,id);
        UserManagementVO userManagementVO = BeanMapper.map(details,UserManagementVO.class );
        return Result.success(userManagementVO) ;
    }

    /***
     * 更改状态
     * @param statusVO
     * @return
     */
    @PostMapping("status")
    @ApiOperation(notes = "更改状态",value = "更改状态")
    public Result<UserManagementBean> updateStatus(@ApiParam("更改状态")@RequestBody UserManagementStatusVO statusVO){
        UnifyAdmin unifyAdmin = getCurrentAdmin();
        UserManagementBean userManagementBean = BeanMapper.map(statusVO,UserManagementBean.class);
        userManagementBean = userManagementService.updateStatus(userManagementBean,unifyAdmin );
        return Result.success(userManagementBean) ;
    }

    /**
     * 更改密码
     * @param updatePasswordVO
     * @return
     */
    @PostMapping("updatePassword")
    @ApiOperation(notes = "更改密码",value = "更改密码")
    public Result<UserManagementBean> updatePassword(@ApiParam("更改密码")@RequestBody UserManagementUpdatePasswordVO updatePasswordVO){
        UnifyAdmin unifyAdmin = getCurrentAdmin();
        UserManagementBean userManagementBean = BeanMapper.map(updatePasswordVO,UserManagementBean.class );
        userManagementBean = userManagementService.updatePassword(userManagementBean,unifyAdmin );
        return Result.success(userManagementBean);
    }
}
