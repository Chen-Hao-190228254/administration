package com.skm.exa.service.biz;

import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.domain.bean.OptionCodesBean;
import com.skm.exa.domain.bean.TechnologicalTypeBean;
import com.skm.exa.domain.bean.QuestionBankBean;
import com.skm.exa.domain.bean.QuestionTypeBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.dto.*;
import com.skm.exa.persistence.qo.QuestionBankLikeQO;
import com.skm.exa.persistence.qo.QuestionQueryLikeQO;
import com.skm.exa.service.BaseService;

import java.util.List;

public interface QuestionBankService extends BaseService<QuestionBankBean> {
    /**
     * 分页迷糊搜索
     * @param qoPageParam
     * @return
     */
    Page<QuestionBankDto> page(PageParam<QuestionBankLikeQO> qoPageParam);

    /**
     * 按条件搜索数据
     * @param qoPageParam
     * @return
     */
    Page<QuestionQueryDto> selectPage(PageParam<QuestionQueryLikeQO> qoPageParam);
    /**
     * 添加题库
     * @param
     * @param unifyAdmin
     * @return
     */
    QuestionBankDto  addQuestion(QuestionBankSaveDto questionBankSaveDto, UnifyAdmin unifyAdmin);

    /**
     * 题目详情
     * @param
     * @param
     * @return
     */
    QuestionBankDto questionDetails( QuestionBankDetailsDto questionBankDetailsDto);

    /**
     * 通过id所有数据
     * @param
     * @param
     * @return
     */
    QuestionBankDto details(QuestionBankBeanDetailsDto questionBankBeanDetailsDto  );

    /**
     * 通过id更改状态
     * @param questionBankBean
     * @param questionBankBean
     * @return
     */
    QuestionBankBean updateStatus(QuestionBankBean questionBankBean);

    /**
     * 输入id删除数据
     * @param questionBankBeanDetailsDto
     * @return
     */
    boolean deleteQuestion(QuestionBankBeanDetailsDto  questionBankBeanDetailsDto );

    /**
     * 更新数据
     * @param
     * @param unifyAdmin
     * @return
     */
    QuestionBankDto updateQuestion(QuestionBankUpdateDto questionBankUpdateDto,UnifyAdmin unifyAdmin);

    /**
     * 获取所有技术类型
     * @param technologicalTypeBean
     * @return
     */
    List<TechnologicalTypeBean> selectBankType(TechnologicalTypeBean technologicalTypeBean);

    /**
     * 通过id获取技术类型
     * @param technologicalTypeBean
     * @param
     * @return
     */
    List<TechnologicalTypeBean> selectBank(TechnologicalTypeBean technologicalTypeBean, QuestionBankBean questionBankBean);

    /**
     * 获取所有问题类型
     * @param questionTypeBean
     * @return
     */
    List<QuestionTypeBean> selectQuestionType(QuestionTypeBean questionTypeBean);

    /**
     * 通过id获取问题类型
     * @param questionTypeBean
     * @param
     * @return
     */
    List<QuestionTypeBean> selectTopicType(QuestionTypeBean questionTypeBean,QuestionBankBean questionBankBean);

    /**
     * 选择题，单选题，添加选项
     * @param optionCodesBean
     * @param questionBankBean
     * @param unifyAdmin
     * @return
     */
   // OptionCodesBean addBankOption (OptionCodesBean optionCodesBean ,QuestionBankBean questionBankBean, UnifyAdmin unifyAdmin);

    /**
     * 删除选项
     * @param optionCodesBean
     * @param
     * @return
     */
    boolean deleteBankOption(OptionCodesBean optionCodesBean);

    /**
     * 查询选项
     * @param optionCodesBean
     * @return
     */
  //  List<OptionCodesBean> selectBankOption(OptionCodesBean optionCodesBean);
}
