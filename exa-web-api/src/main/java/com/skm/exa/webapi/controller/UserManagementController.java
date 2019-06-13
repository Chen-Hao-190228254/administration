package com.skm.exa.webapi.controller;


import com.skm.exa.common.enums.Msg;
import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.domain.bean.UserManagementBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.mybatis.SearchConditionGroup;

import com.skm.exa.persistence.dto.*;
import com.skm.exa.persistence.qo.UserManagementLikeQO;
import com.skm.exa.service.biz.UserManagementService;
import com.skm.exa.webapi.BaseController;
import com.skm.exa.webapi.vo.*;
import io.swagger.annotations.Api;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Transactional
    @PostMapping("/add")
    @ApiOperation(notes = "添加用户",value = "添加用户")
    public Result add (@ApiParam("添加用户")@RequestBody UserManagementSaveVO userManagementSaveVO){
        UnifyAdmin unifyAdmin = getCurrentAdmin();
        UserManagementAddDto userManagementAddDto = BeanMapper.map(userManagementSaveVO, UserManagementAddDto.class);
        userManagementAddDto.setFileSaveDtos(BeanMapper.mapList(userManagementSaveVO.getFileSaveVos(), FileSaveVo.class, FileSaveDto.class));
        boolean is = userManagementService.add(userManagementAddDto, unifyAdmin);
        return is? Result.success():Result.error(Msg.E40019);
    }
    /**
     *  更新用户
     * @param userManagementUpdateVO
     * @return
     */
    @Transactional
    @PostMapping("/updateManagement")
    @ApiOperation(notes = "更新用户",value = "更新用户")
    public Result update(@ApiParam("更新用户")@RequestBody UserManagementUpdateVO userManagementUpdateVO){
       UnifyAdmin unifyAdmin = getCurrentAdmin();
       UserManagementUpdateDto userManagementUpdateDto = BeanMapper.map(userManagementUpdateVO,UserManagementUpdateDto.class );
       userManagementUpdateDto.setFileUpdateDtos(BeanMapper.mapList(userManagementUpdateVO.getFileUpdateVos(), FileUpdateVo.class, FileUpdateDto.class));
       boolean is = userManagementService.update(userManagementUpdateDto,unifyAdmin );
        return is? Result.success():Result.error(Msg.E40023);
    }

    /**
     *  通过ID 删除用户
     * @param id
     * @return
     */
    @Transactional
    @PostMapping("/deleteManagement")
    @ApiOperation(notes = "通过id删除用户",value = "通过id删除用户")
    public Result delete (@ApiParam("通过id删除用户")@RequestParam ("id") Long id){
        UserManagementBean userManagementBean = new UserManagementBean();
        userManagementBean.setId(id);
        boolean delete = userManagementService.delete(userManagementBean);
        return Result.success(delete);
    }

    /**
     *  通过id查询
     * @param id
     * @return
     */
    @PostMapping("/details")
    @ApiOperation(notes = "通过id查询",value = "通过id查询")
    public Result details(@ApiParam("通过id查询")@RequestParam ("id") Long id){
        UserManagementDto userManagementDto = userManagementService.details(id);
        UserManagementVO userManagementVO = BeanMapper.map(userManagementDto,UserManagementVO.class );
        return Result.success(userManagementVO) ;
    }

    /***
     * 更改状态
     * @param statusVO
     * @return
     */
    @PostMapping("/status")
    @ApiOperation(notes = "更改状态",value = "更改状态")
    public Result updateStatus(@ApiParam("更改状态")@RequestBody UserManagementStatusVO statusVO){
        UnifyAdmin unifyAdmin = getCurrentAdmin();
        UserManagementBean userManagementBean = BeanMapper.map(statusVO,UserManagementBean.class);
        userManagementBean = userManagementService.updateStatus(userManagementBean,unifyAdmin );
        UserManagementStatusVO vo = BeanMapper.map(userManagementBean,UserManagementStatusVO.class );
        return Result.success(vo) ;
    }

    /**
     * 更改密码
     * @param updatePasswordVO
     * @return
     */
    @PostMapping("/updatePassword")
    @ApiOperation(notes = "旧密码必须与数据库密码一致，新密码不能与旧密码一致",value = "更改密码")
    public Result updatePassword(@ApiParam("更改密码")@RequestBody UserManagementUpdatePasswordVO updatePasswordVO){
        UnifyAdmin unifyAdmin = getCurrentAdmin();
        UserManagementUpdatePasswordDto userManagementBean = BeanMapper.map(updatePasswordVO,UserManagementUpdatePasswordDto.class );
        int updatePassword = userManagementService.updatePassword(userManagementBean,unifyAdmin );
        return Result.success(updatePassword);
    }

    /**
     * 获取所有数据
     * @return
     */
    @PostMapping("/select")
    @ApiOperation(value = "获取所有数据" ,notes = "获取所有数据")
    public Result select(){
        UserManagementBean bean = new UserManagementBean();
        List<UserManagementBean> userManagementBean = userManagementService.selectManagement(bean);
        return Result.success(userManagementBean) ;
    }
    /**
     * 判断唯一
     * @return
     */
    @PostMapping("/judgeUnique")
    @ApiOperation(value = "查询身份证唯一值" ,notes = "true:数据库有数据 ，false:表示没数据")
    public Result judgeUnique(@RequestParam("accountNumber") String accountNumber){
        Boolean is = userManagementService.judgeUnique(accountNumber);
        return  Result.success(is);
    }
}
