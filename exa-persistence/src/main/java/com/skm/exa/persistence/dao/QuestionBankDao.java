package com.skm.exa.persistence.dao;

import com.skm.exa.domain.bean.OptionCodesBean;
import com.skm.exa.domain.bean.TechnologicalTypeBean;
import com.skm.exa.domain.bean.QuestionBankBean;
import com.skm.exa.domain.bean.QuestionTypeBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.BaseDao;
import com.skm.exa.persistence.dto.QuestionBankDto;
import com.skm.exa.persistence.dto.QuestionQueryDto;
import com.skm.exa.persistence.qo.QuestionBankLikeQO;
import com.skm.exa.persistence.qo.QuestionQueryLikeQO;


import java.util.List;
import java.util.ListResourceBundle;

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
    QuestionBankBean questionDetails(QuestionBankBean questionBankBean);

    /**
     * 通过id获取所有数据
     * @param
     * @return
     */
    QuestionBankBean details(QuestionBankBean questionBankBean);

    /**
     * 通过id更改状态
     * @param questionBankBean
     * @return
     */
    Integer updateStatus(QuestionBankBean questionBankBean);

    /**
     * 输入id删除数据
     * @param id
     * @return
     */
    Integer delete(Long id);

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
     * @param optionCodesBean
     * @return
     */
    Integer addOptionCodes(OptionCodesBean optionCodesBean);

    /**
     * 删除选项
     * @param optionCodesBean
     * @return
     */
    boolean deleteOptionCodes(OptionCodesBean optionCodesBean);

    /**
     * 查询选项
     * @param optionCodesBean
     * @return
     */
    List<OptionCodesBean> selectOptionCodes(OptionCodesBean optionCodesBean);
}
