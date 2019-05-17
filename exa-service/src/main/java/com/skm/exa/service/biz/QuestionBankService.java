package com.skm.exa.service.biz;

import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.domain.bean.QuestionBankBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.dto.QuestionBankDto;
import com.skm.exa.persistence.qo.QuestionBankLikeQO;
import com.skm.exa.service.BaseService;

public interface QuestionBankService extends BaseService<QuestionBankBean> {
    /**
     * 分页迷糊搜索
     * @param qoPageParam
     * @return
     */
    Page<QuestionBankDto> page(PageParam<QuestionBankLikeQO> qoPageParam);

    /**
     * 添加题库
     * @param questionBankBean
     * @param unifyAdmin
     * @return
     */
    QuestionBankBean addQuestion(QuestionBankBean questionBankBean, UnifyAdmin unifyAdmin);

    /**
     * 题目详情
     * @param questionBankBean
     * @param id
     * @return
     */
    QuestionBankBean questionDetails(QuestionBankBean questionBankBean , Long id);

    /**
     * 获取所有数据
     * @param questionBankBean
     * @param id
     * @return
     */
    QuestionBankBean details(QuestionBankBean questionBankBean ,Long id);

    /**
     * 通过id更改状态
     * @param questionBankBean
     * @param id
     * @return
     */
    QuestionBankBean updateStatus(QuestionBankBean questionBankBean ,Long id);

    Result<Integer> delete(Long id);
}
