package com.skm.exa.persistence.dao;

import com.skm.exa.domain.bean.OptionCodesBean;
import com.skm.exa.domain.bean.TechnologicalTypeBean;
import com.skm.exa.domain.bean.QuestionBankBean;
import com.skm.exa.domain.bean.QuestionTypeBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.BaseDao;
import com.skm.exa.persistence.dto.*;
import com.skm.exa.persistence.qo.QuestionBankLikeQO;
import com.skm.exa.persistence.qo.QuestionQueryLikeQO;
import org.apache.ibatis.annotations.Param;


import java.util.List;

public interface QuestionBankDao extends BaseDao<QuestionBankBean> {
    /**
     * 分页模糊搜索
     * @param qoPageParam
     * @return
     */
    Page<QuestionBankDto> page (PageParam<QuestionBankLikeQO> qoPageParam);

    /**
     * 按条件搜索数据
     * @param qoPageParam
     * @return
     */
    Page<QuestionQueryDto> selectQuestionPage(PageParam<QuestionQueryLikeQO> qoPageParam);

    /**
     * 添加题库
     * @param questionBankBean
     * @return
     */
    Integer addQuestion(QuestionBankBean questionBankBean);

    /**
     * 题目详情
     * @param questionBankBean
     * @return
     */
    QuestionBankDetailsDto questionDetails(QuestionBankBean questionBankBean);

    /**
     * 通过id获取所有数据
     * @param
     * @return
     */
    QuestionBankDto details(QuestionBankBean questionBankBean);

    /**
     * 通过id更改状态
     * @param questionBankBean
     * @return
     */
    Integer updateStatus(QuestionBankBean questionBankBean);

    /**
     * 输入id删除数据
     * @param
     * @return
     */
    Integer deleteQuestion( QuestionBankBean questionBankBean);

    /**
     * 更新数据
     * @param questionBankBean
     * @return
     */
    Integer updateQuestion(QuestionBankBean questionBankBean);

    /**
     * 获取所有技术类型
     * @param technologicalTypeBean
     * @return
     */
    List<TechnologicalTypeBean> selectBankType(TechnologicalTypeBean technologicalTypeBean);

    /**
     * 通过id获取技术类型
     * @param technologicalTypeBean
     * @return
     */
    List<TechnologicalTypeBean> selectBank(TechnologicalTypeBean technologicalTypeBean);

    /**
     * 获取所有问题类型
     * @param questionTypeBean
     * @return
     */
    List<QuestionTypeBean> selectQuestionType(QuestionTypeBean questionTypeBean);

    /**
     * 通过id获取问题类型
     * @param questionTypeBean
     * @return
     */
    List<QuestionTypeBean> selectTopicType(QuestionTypeBean questionTypeBean);

    /**
     * 选择题，单选题，添加选项
     * @param optionCodesBeans
     * @return
     */
    Integer addOptionCodes(@Param("optionCodesBeans") List<OptionCodesBean> optionCodesBeans );

    /**
     * 通过Code删除选项
     * @param code
     * @return
     */
    Integer deleteOptionCodes(Long code);
    /**
     * 通过id删除选项
     * @param id
     * @return
     */
    Integer deleteOption(Long id);

    /**
     *修改选项
     */
    Integer updateBank(@Param(value = "list") List<OptionCodesBean> optionCodesBeanList);
    /**
     * 查询选项
     * @param codes
     * @return
     */
    List<OptionCodesBean> selectOptionCodes(@Param("codes") List<Long> codes);

}
