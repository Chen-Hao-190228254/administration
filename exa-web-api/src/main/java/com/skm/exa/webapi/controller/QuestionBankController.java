package com.skm.exa.webapi.controller;

import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.domain.bean.QuestionBankBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.mybatis.SearchConditionGroup;
import com.skm.exa.persistence.dto.QuestionBankDto;
import com.skm.exa.persistence.qo.QuestionBankLikeQO;
import com.skm.exa.service.biz.QuestionBankService;
import com.skm.exa.webapi.BaseController;
import com.skm.exa.webapi.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "问题管理" ,value = "问题管理")
@RestController
@RequestMapping("web/v1/question")
public class QuestionBankController extends BaseController {
    @Autowired
    private QuestionBankService questionBankService;

    /**
     * 问题管理分页模糊搜索
     * @param pageParam
     * @return
     */
    @PostMapping("page")
    @ApiOperation(value = "模糊分页查询" ,notes = "输入数据迷糊查询")
    public Result<Page<QuestionBankVO>> page(@ApiParam("输入数据") @RequestBody PageParam<QuestionBankQueryVO> pageParam){
        QuestionBankQueryVO bankQueryVO = pageParam.getCondition();
        QuestionBankLikeQO qo = new QuestionBankLikeQO();
        qo.addSearchConditionGroup(SearchConditionGroup
                .buildMultiColumnsSearch(bankQueryVO.getKeyword()));
        PageParam<QuestionBankLikeQO> qoPageParam = new PageParam<>(pageParam.getPage() ,pageParam.getSize());
        qoPageParam.setCondition(qo);
        qo.setEnterpriseNameLike(bankQueryVO.getKeyword());
        qo.setTitleLike(bankQueryVO.getKeyword());
        qo.setLabelLike(bankQueryVO.getKeyword());
        Page<QuestionBankDto> beanPage = questionBankService.page(qoPageParam);
        Page<QuestionBankVO> voPage = beanPage.map(QuestionBankDto.class, QuestionBankVO.class);
        return Result.success(voPage) ;
    }

    /**
     * 添加题库
     * @param saveVO
     * @return
     */
    @PostMapping("addQuestion")
    @ApiOperation(value = "添加题库" , notes = "输入数据保存在数据库")
    public Result<QuestionBankBean> addQuestion(@ApiParam("添加题库")@RequestBody QuestionBankSaveVO saveVO){
        UnifyAdmin unifyAdmin = getCurrentAdmin();
        QuestionBankBean  bean = BeanMapper.map(saveVO,QuestionBankBean.class );
        bean = questionBankService.addQuestion(bean, unifyAdmin);
        return Result.success(bean);
    }

    /**
     * 题目详情
     * @param id
     * @return
     */
    @PostMapping("questionDetails")
    @ApiOperation(value = "题目详情" ,notes = "获取题目详情")
    public Result questionDetails(@ApiParam("题目详情")@RequestParam("getId") Long id){
        QuestionBankBean bankBean = new QuestionBankBean();
        bankBean.setId(id);
        QuestionBankBean questionBankBean = questionBankService.questionDetails(bankBean,id );
        QuestionBankDetailsVO detailsVO = BeanMapper.map(questionBankBean,QuestionBankDetailsVO.class );
        return Result.success(detailsVO);
    }

    /**
     * 通过id获取所有数据
     * @param id
     * @return
     */
    @PostMapping("details")
    @ApiOperation(value = "通过id获取所有数据" , notes = "输入id获取数据库所有数据")
    public Result details (@ApiParam("通过id获取所有数据")@RequestParam ("getId") Long id){
        QuestionBankBean bean = new QuestionBankBean();
        bean.setId(id);
        QuestionBankBean bankBean = questionBankService.details(bean,id );
        QuestionBankVO vo = BeanMapper.map(bankBean,QuestionBankVO.class );
        System.out.println(vo.getStatus().toString());
        return Result.success(vo);
    }

    /**
     * 通过id更改状态
     * @param id
     * @return
     */
    @PostMapping("updateStatus")
    @ApiOperation(value = "通过id更改状态" , notes = "输入id更改状态")
    public Result updateStatus(@ApiParam("输入id更改状态")@RequestParam("getId") Long id){
        QuestionBankBean bean = new QuestionBankBean();
        bean.setId(id);
        QuestionBankBean bankBean = questionBankService.updateStatus(bean,id );
        QuestionBankVO bankVO = BeanMapper.map(bankBean,QuestionBankVO.class );
        return Result.success(bankVO);

    }
    @PostMapping("delete")
    public Result<QuestionBankDeleteVo> delete(@RequestParam("getID") Long id){
        QuestionBankBean bean = new QuestionBankBean();
        bean.setId(id);
        Result<Integer> bankBean = questionBankService.delete(id);
        QuestionBankDeleteVo deleteVo = BeanMapper.map(bankBean, QuestionBankDeleteVo.class);
        return Result.success(deleteVo);
    }
}
