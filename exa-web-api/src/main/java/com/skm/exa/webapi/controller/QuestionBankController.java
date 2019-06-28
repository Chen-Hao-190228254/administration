package com.skm.exa.webapi.controller;

import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.domain.bean.OptionCodesBean;
import com.skm.exa.domain.bean.TechnologicalTypeBean;
import com.skm.exa.domain.bean.QuestionBankBean;
import com.skm.exa.domain.bean.QuestionTypeBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.mybatis.SearchConditionGroup;
import com.skm.exa.persistence.dto.*;
import com.skm.exa.persistence.qo.QuestionBankLikeQO;
import com.skm.exa.persistence.qo.QuestionQueryLikeQO;
import com.skm.exa.service.biz.QuestionBankService;
import com.skm.exa.webapi.BaseController;
import com.skm.exa.webapi.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@Api(tags = "问题管理" ,description = "问题管理")
@RestController
@RequestMapping("web/v1/question")
public class QuestionBankController extends BaseController {
    @Autowired
    private QuestionBankService questionBankService;

    /**
     * 问题管理分页模糊搜索
     * @param param
     * @return
     */
    @PostMapping("/page")
    @ApiOperation(value = "模糊分页查询" ,notes = "输入数据迷糊查询")
    public Result<Page<QuestionBankVO>> page(@ApiParam("param") @RequestBody PageParam<QuestionBankQueryVO> param){
        QuestionBankQueryVO bankQueryVO = param.getCondition();
        QuestionBankLikeQO qo = new QuestionBankLikeQO();
        qo.addSearchConditionGroup(SearchConditionGroup
                .buildMultiColumnsSearch(bankQueryVO.getKeyword()));
        PageParam<QuestionBankLikeQO> qoPageParam = new PageParam<>(param.getPage() ,param.getSize());
        qoPageParam.setCondition(qo);
        qo.setEnterpriseNameLike(bankQueryVO.getKeyword());
        qo.setTitleLike(bankQueryVO.getKeyword());
        qo.setLabelLike(bankQueryVO.getKeyword());
        Page<QuestionBankDto> beanPage = questionBankService.page(qoPageParam);
        Page<QuestionBankVO> bankBeanPage = beanPage.map(QuestionBankDto.class, QuestionBankVO.class);
        return Result.success(bankBeanPage) ;
    }

    /**
     * 输入条件搜索
     * @param pageParam
     * @return
     */
    @ApiOperation(value = "输入条件搜索" ,notes = "输入搜素条件模糊搜索数据")
    @PostMapping("/selectPage")
    public Result<Page<QuestionBankVO>> selectPage(@ApiParam("pageParam")@RequestBody PageParam<QuestionQueryVO> pageParam){
        QuestionQueryVO queryVO = pageParam.getCondition();
        QuestionQueryLikeQO likeQO = new QuestionQueryLikeQO();
        PageParam<QuestionQueryLikeQO> qoPage = new PageParam<>(pageParam.getPage() , pageParam.getSize());
        qoPage.setCondition(likeQO);
        likeQO.setEnterpriseNameLike(queryVO.getEnterpriseName());
        likeQO.setTechnologicalTypeLike(queryVO.getTechnologicalType());
        likeQO.setTopicTypeLike(queryVO.getTopicType());
        likeQO.setTitleLike(queryVO.getTitle());
        likeQO.setStartDtLike(queryVO.getStartDt());
        likeQO.setEndDtLike(queryVO.getEndDt());
        Page<QuestionQueryDto> bankDtoPage = questionBankService.selectPage(qoPage);
        Page<QuestionBankVO> bankVOPage =bankDtoPage.map(QuestionQueryDto.class,QuestionBankVO.class );
        return Result.success(bankVOPage);
    }

    /**
     * 添加题库
     * @param saveVO
     * @return
     */
    @Transactional
    @PostMapping("/addQuestion")
    @ApiOperation(value = "添加题库" , notes = "technologicalType 值只能取1-4 ，topicType值只能取1-4 ，其中2和3代表选择题，才可添加选项")
    public Result addQuestion(@Valid @ApiParam("saveVO")@RequestBody QuestionBankSaveVO saveVO){
        QuestionBankSaveDto saveDto = BeanMapper.map(saveVO, QuestionBankSaveDto.class);
        saveDto.setOptionCodesDtoList(BeanMapper.mapList(saveVO.getOptionCodesVoList(),OptionCodesVo.class , OptionCodesDto.class));
        QuestionBankBean beanResult = questionBankService.addQuestion(saveDto,getCurrentAdmin());
        QuestionBankVO questionBankVO = BeanMapper.map(beanResult, QuestionBankVO.class);
        return Result.success(questionBankVO);
    }

    /**
     * 题目详情
     * @param id
     * @return
     */
    @PostMapping("/questionDetails")
    @ApiOperation(value = "题目详情" ,notes = "获取题目详情")
    public Result questionDetails(@ApiParam("题目详情")@RequestParam("getId") Long id){
        QuestionBankBean questionBankBean = questionBankService.questionDetails(new QuestionBankDetailsDto(id));
        QuestionBankVO vo = BeanMapper.map(questionBankBean,QuestionBankVO.class );
        QuestionBankDetailsVO detailsVO = BeanMapper.map(vo,QuestionBankDetailsVO.class );
        return Result.success(detailsVO);
    }

    /**
     * 通过id获取所有数据
     * @param id
     * @return
     */
    @Transactional
    @PostMapping("/details")
    @ApiOperation(value = "通过id获取所有数据" , notes = "输入id获取数据库所有数据")
    public Result details (@ApiParam("通过id获取所有数据")@RequestParam ("getId") Long id){
        QuestionBankBean bankBean = questionBankService.details(new QuestionBankBeanDetailsDto(id));
        QuestionBankVO vo = BeanMapper.map(bankBean,QuestionBankVO.class );
        return Result.success(vo);
    }

    /**
     * 通过id更改状态
     * @param vo
     * @return
     */
    @Transactional
    @PostMapping("/updateStatus")
    @ApiOperation(value = "通过id更改状态" , notes = "输入id更改状态")
    public Result updateStatus(@ApiParam("输入id更改状态")@RequestBody QuestionUpdateStatusVo vo){
        QuestionBankBean bankBean = BeanMapper.map(vo, QuestionBankBean.class);
        QuestionBankBean bean = questionBankService.updateStatus(bankBean);
        QuestionUpdateStatusVo bankVO = BeanMapper.map(bean,QuestionUpdateStatusVo.class );
        return Result.success(bankVO);

    }

    /**
     * 通过id删除数据
     * @param id
     * @return
     */
    @Transactional
    @ApiOperation(value = "通过id删除数据" ,notes = "输入id删除数据,如果状态码为0则无法删除，状态码为1则删除")
    @PostMapping("/delete")
    public Result  delete(@ApiParam("id")@RequestParam("getID") Long id){
        boolean bankBean = questionBankService.deleteQuestion(new QuestionBankBeanDetailsDto(id));
        return Result.success(bankBean);
    }

    /**
     * 更新数据
      * @param updateVO
     * @return
     */
    @Transactional
    @ApiOperation(value = "更新数据" ,notes = "更新数据如果不传选择题id则为添加选项，如果传id则为修改选项")
    @PostMapping("/updateQuestion")
    public Result updateQuestion(@Valid @ApiParam("updateVO")@RequestBody QuestionBankUpdateVO updateVO){
        QuestionBankUpdateDto dto = BeanMapper.map(updateVO, QuestionBankUpdateDto.class);
        dto.setOptionCodesUpdateDtoList(BeanMapper.mapList(updateVO.getOptionCodesUpdateVoList(), OptionCodesUpdateVo.class,OptionCodesUpdateDto.class ));
        QuestionBankBean result = questionBankService.updateQuestion( dto ,getCurrentAdmin());
        QuestionBankVO vo = BeanMapper.map(result,QuestionBankVO.class );
        return Result.success(vo);
    }

    /**
     * 获取所有技术类型
     * @param
     * @return
     */
    @ApiOperation(value = "获取所有技术类型" ,notes = "此接口可获取所有技术类型")
    @PostMapping("/selectType")
    public Result selectType(){
        TechnologicalTypeBean bean = new TechnologicalTypeBean();
        List<TechnologicalTypeBean> bank = questionBankService.selectBankType(bean);
        return Result.success(bank);
    }

    /**
     *  根据id获取技术类型
     * @param id
     * @return
     */
    @ApiOperation(value = "输入id获取技术类型",notes = "输入id可获取当前id的技术类型")
    @PostMapping("/selectBank")
    public Result selectBank(@ApiParam("输入id获取技术类型")@RequestParam("getId") Long id){
        TechnologicalTypeBean technologicalTypeBean = new TechnologicalTypeBean();
        QuestionBankBean questionBankBean = new QuestionBankBean();
        questionBankBean.setId(id);
        technologicalTypeBean.setId(id);
        List<TechnologicalTypeBean> option = questionBankService.selectBank(technologicalTypeBean,questionBankBean);
        return Result.success(option);
    }

    /**
     * 获取所有的问题类型
     * @return
     */
    @ApiOperation(value = "获取所有问题类型" ,notes = "获取所有问题类型")
    @PostMapping("/selectTopic")
    public Result selectTopic(){
        QuestionTypeBean typeBean = new QuestionTypeBean();
        List<QuestionTypeBean> beans = questionBankService.selectQuestionType(typeBean);
        return Result.success(beans) ;
    }

    /**
     * 通过id获取问题类型
     * @param id
     * @return
     */
    @ApiOperation(value="通过id获取问题类型" , notes = "输入id获取问题类型")
    @PostMapping("/selectTopicType")
    public Result selectTopicType(@ApiParam("输入id获取问题类型")@RequestParam("getId") Long id){
        QuestionTypeBean bean = new QuestionTypeBean();
        QuestionBankBean questionBankBean = new QuestionBankBean();
        questionBankBean.setId(id);
        bean.setId(id);
        List<QuestionTypeBean> typeBean = questionBankService.selectTopicType(bean ,questionBankBean);
        return Result.success(typeBean);
    }

   /* *//**
     * 选择题，单选题，添加选项
     * @param codesVo
     * @return
     *//*
    @Transactional
    @ApiOperation(value = "选择题添加选项" , notes = "输入id获取当前题目类型，如果是选择题，则可添加选项" )
    @PostMapping("/addBankOption")
    public Result addBankOption(*//*@RequestParam("getId") Long id  ,*//*@RequestBody OptionCodesVo codesVo){
        UnifyAdmin unifyAdmin = getCurrentAdmin();
        QuestionBankBean questionBankBean = new QuestionBankBean() ;
        *//*questionBankBean.setId(id);*//*
        *//*questionBankBean.getId();*//*
        OptionCodesBean optionCodesBean  = BeanMapper.map(codesVo,OptionCodesBean.class );
        OptionCodesBean vo = questionBankService.addBankOption(optionCodesBean,questionBankBean, unifyAdmin );
        return Result.success(vo);
    }*/

    /**
     * 删除选项
     * @param
     * @param id
     * @return
     */
    @ApiOperation(value = "删除选项" ,notes = "输入选项id，获取要删除的选项")
    @PostMapping("/deleteBankOption")
    @Transactional
    public Result deleteBankOption(@ApiParam("输入id获取要删除的选择题选项")@RequestParam("id") Long id){
        OptionCodesBean codesBean = new OptionCodesBean();
        codesBean.setId(id);
        boolean bankOption = questionBankService.deleteBankOption(codesBean);
        return Result.success(bankOption);
    }

    /**
     * 通过code查询选项内容
     * @param code
     * @return
     */
   /* @ApiOperation(value = "通过code获取选项", notes = "通过code获取选项")
    @PostMapping("/selectBankOption")
    @Transactional
    public Result selectBankOption(@RequestParam("code") Long code){
        OptionCodesBean codesBean = new OptionCodesBean();
        codesBean.setCode(code);
        List<OptionCodesBean> bean = questionBankService.selectBankOption(codesBean);
        return Result.success(bean) ;
    }*/
}
