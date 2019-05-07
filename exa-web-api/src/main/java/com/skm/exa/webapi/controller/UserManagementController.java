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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
    public Result<Page<UserManagementVO>> pageResult
            (@RequestBody PageParam<UserManagementQueryVO> pageParam){
        UserManagementQueryVO queryVO = pageParam.getCondition();
        UserManagementLikeQO qo = new UserManagementLikeQO();
        qo.addSearchConditionGroup(SearchConditionGroup //columns 列
                .buildMultiColumnsSearch(queryVO.getKeyword(),
                        "account_number","name","card","phone","native_place","email","qq","skill"));//search  搜索
            //转换并返回值给Service
        PageParam<UserManagementLikeQO> qoPageParam = new PageParam<>(pageParam.getPage(), pageParam.getSize());
        qoPageParam.setCondition(qo); //setCondition 设置条件
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
    public Result<UserManagementBean> add (@RequestBody UserManagementSaveVO userManagementSaveVO){
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
    public Result<UserManagementBean> update(@RequestBody UserManagementUpdateVO userManagementUpdateVO){
       UnifyAdmin unifyAdmin = getCurrentAdmin();
       UserManagementBean userManagementBean = BeanMapper.map(userManagementUpdateVO,UserManagementBean.class );
       userManagementBean = userManagementService.update(userManagementBean,unifyAdmin );
       System.out.println(userManagementBean.toString());
        return Result.success(userManagementBean);
    }

    /**
     *  通过ID 删除用户
     * @param id
     * @return
     */
    @PostMapping("deleteManagement")
    public Result<UserManagementDeleteVO> delete (@RequestParam ("id") Long id){
        UserManagementBean userManagementBean = new UserManagementBean();
        userManagementBean.setId(id);
        Integer delete = userManagementService.delete(UserManagementBean.class,id );
        UserManagementDeleteVO userManagementDeleteVO = BeanMapper.map(delete,UserManagementDeleteVO.class );
        System.out.println(userManagementDeleteVO.toString());
        return Result.success(userManagementDeleteVO);
    }
    @PostMapping("status")
    public Result<UserManagementBean> updateStatus(@RequestBody UserManagementStatusVO statusVO){
        UnifyAdmin unifyAdmin = getCurrentAdmin();
        UserManagementBean userManagementBean = BeanMapper.map(statusVO,UserManagementBean.class);
        userManagementBean = userManagementService.updateStatus(userManagementBean,unifyAdmin );
        return Result.success(userManagementBean) ;
    }
}
