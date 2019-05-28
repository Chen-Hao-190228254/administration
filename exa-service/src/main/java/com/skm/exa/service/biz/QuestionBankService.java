package com.skm.exa.service.biz;

import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.domain.bean.BankOptionBean;
import com.skm.exa.domain.bean.QuestionBankBean;
import com.skm.exa.domain.bean.QuestionTypeBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.dto.QuestionBankDto;
import com.skm.exa.persistence.dto.QuestionQueryDto;
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
     * @param questionBankBean
     * @param unifyAdmin
     * @return
     */
    QuestionBankBean addQuestion(QuestionBankBean questionBankBean, UnifyAdmin unifyAdmin);

    /**
     * 题目详情
     * @param
     * @param questionBankBean
     * @return
     */
    QuestionBankBean questionDetails( QuestionBankBean questionBankBean);

    /**
     * 通过id所有数据
     * @param
     * @param
     * @return
     */
    QuestionBankBean details( QuestionBankBean questionBankBean  );

    /**
     * 通过id更改状态
     * @param questionBankBean
     * @param id
     * @return
     */
    QuestionBankBean updateStatus(QuestionBankBean questionBankBean ,Long id);

    /**
     * 输入id删除数据
     * @param questionBankBean
     * @return
     */
    boolean delete(QuestionBankBean  questionBankBean );

    /**
     * 更新数据
     * @param questionBankBean
     * @param unifyAdmin
     * @return
     */
   boolean updateQuestion(QuestionBankBean questionBankBean,UnifyAdmin unifyAdmin);

    /**
     * 获取所有技术类型
     * @param bankOptionBean
     * @return
     */
    List<BankOptionBean> selectBankType(BankOptionBean bankOptionBean);

    /**
     * 通过id获取技术类型
     * @param bankOptionBean
     * @param id
     * @return
     */
    List<BankOptionBean> selectBank(BankOptionBean bankOptionBean,QuestionBankBean questionBankBean);

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
}
