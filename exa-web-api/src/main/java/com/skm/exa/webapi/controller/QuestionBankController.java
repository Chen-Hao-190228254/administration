package com.skm.exa.webapi.controller;

import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.domain.bean.BankOptionBean;
import com.skm.exa.domain.bean.QuestionBankBean;
import com.skm.exa.domain.bean.QuestionTypeBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.mybatis.SearchConditionGroup;
import com.skm.exa.persistence.dto.QuestionBankDto;
import com.skm.exa.persistence.qo.QuestionBankLikeQO;
import com.skm.exa.persistence.qo.QuestionQueryLikeQO;
import com.skm.exa.service.biz.QuestionBankService;
import com.skm.exa.webapi.BaseController;
import com.skm.exa.webapi.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "问题管理" ,description = "问题管理")
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
     * 输入条件搜索
     * @param pageParam
     * @return
     */
    @ApiOperation(value = "输入条件搜索" ,notes = "输入搜素条件模糊搜索数据")
    @PostMapping("selectPage")
    public Result<Page<QuestionBankVO>> selectPage(@ApiParam("输入条件搜索数据")@RequestBody PageParam<QuestionQueryVO> pageParam){
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
        Page<QuestionBankDto> bankDtoPage = questionBankService.selectPage(qoPage);
        Page<QuestionBankVO> bankVOPage =bankDtoPage.map(QuestionBankDto.class,QuestionBankVO.class );
        return Result.success(bankVOPage);
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
        QuestionBankBean beanResult = questionBankService.addQuestion(bean,unifyAdmin );
        return Result.success(beanResult);
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
        QuestionBankBean questionBankBean = questionBankService.questionDetails(id);
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
        QuestionBankBean bankBean = questionBankService.details(id );
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

    /**
     * 通过id删除数据
     * @param id
     * @return
     */
    @ApiOperation(value = "通过id删除数据" ,notes = "输入id删除数据")
    @PostMapping("delete")
    public Result<QuestionBankDeleteVo> delete(@ApiParam("输入id删除数据")@RequestParam("getID") Long id){
        QuestionBankBean bean = new QuestionBankBean();
        bean.setId(id);
        Integer bankBean = questionBankService.delete(id);
        QuestionBankDeleteVo deleteVo = BeanMapper.map(bankBean, QuestionBankDeleteVo.class);
        return Result.success(deleteVo);
    }

    /**
     * 更新数据
      * @param updateVO
     * @return
     */
    @ApiOperation(value = "更新数据" ,notes = "更新数据")
    @PostMapping("updateQuestion")
    public Result<QuestionBankUpdateVO> updateQuestion(@ApiParam("更新数据")@RequestBody QuestionBankUpdateVO updateVO){
        UnifyAdmin unifyAdmin = getCurrentAdmin();
        QuestionBankBean bankBean = BeanMapper.map(updateVO, QuestionBankBean.class);
        Integer result = questionBankService.updateQuestion( bankBean ,unifyAdmin);
        QuestionBankUpdateVO vo = BeanMapper.map(result,QuestionBankUpdateVO.class );
        return Result.success(vo);
    }

    /**
     * 获取所有技术类型
     * @param
     * @return
     */
    @ApiOperation(value = "获取所有技术类型" ,notes = "此接口可获取所有技术类型")
    @PostMapping("selectType")
    public Result selectType(){
        BankOptionBean bean = new BankOptionBean();
        List<BankOptionBean> bank = questionBankService.selectBankType(bean);
        return Result.success(bank);
    }

    /**
     *  根据id获取技术类型
     * @param id
     * @return
     */
    @ApiOperation(value = "输入id获取技术类型",notes = "输入id可获取当前id的技术类型")
    @PostMapping("selectBank")
    public Result selectBank(@ApiParam("输入id获取技术类型")@RequestParam("getId") Long id){
        BankOptionBean bankOptionBean = new BankOptionBean();
        bankOptionBean.setId(id);
        List<BankOptionBean> option = questionBankService.selectBank(bankOptionBean,id);
        System.out.println(option.toString());
        return Result.success(option);
    }

    /**
     * 获取所有的问题类型
     * @return
     */
    @ApiOperation(value = "获取所有问题类型" ,notes = "获取所有问题类型")
    @PostMapping("selectTopic")
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
    @PostMapping("selectTopicType")
    public Result selectTopicType(@ApiParam("输入id获取问题类型")@RequestParam("getId") Long id){
        QuestionTypeBean bean = new QuestionTypeBean();
        bean.setId(id);
        List<QuestionTypeBean> typeBean = questionBankService.selectTopicType(bean ,id);
        return Result.success(typeBean);
    }

}
