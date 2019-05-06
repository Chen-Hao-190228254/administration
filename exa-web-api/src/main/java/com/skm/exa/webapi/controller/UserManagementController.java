package com.skm.exa.webapi.controller;


import com.skm.exa.common.object.Result;
import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.domain.bean.UserManagementBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.mybatis.SearchConditionGroup;
import com.skm.exa.persistence.dto.UserManagementDto;
import com.skm.exa.persistence.qo.UserManagementLikeQO;
import com.skm.exa.service.biz.UserManagementService;
import com.skm.exa.webapi.BaseController;
import com.skm.exa.webapi.vo.UserManagementQueryVO;
import com.skm.exa.webapi.vo.UserManagementVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
     *  批量添加
     * @param userManagementVOList
     * @return
     */
    @PostMapping("batchInsert")
    public Result batchInsert( @RequestBody List<UserManagementVO> userManagementVOList){
        List<UserManagementBean> userManagementBeans = BeanMapper
                .mapList(userManagementVOList,UserManagementVO.class ,UserManagementBean.class );
        List<UserManagementDto> save = userManagementService.add(userManagementBeans, getCurrentUser());
        return Result.success(save);
    }
}
