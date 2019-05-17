package com.skm.exa.persistence.dao;

import com.skm.exa.common.object.Result;
import com.skm.exa.domain.bean.QuestionBankBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.BaseDao;
import com.skm.exa.persistence.dto.QuestionBankDto;
import com.skm.exa.persistence.qo.QuestionBankLikeQO;
import org.apache.ibatis.annotations.Param;

public interface QuestionBankDao extends BaseDao<QuestionBankBean> {
    /**
     * 分页模糊搜索
     * @param qoPageParam
     * @return
     */
    Page<QuestionBankDto> page (PageParam<QuestionBankLikeQO> qoPageParam);

    /**
     * 添加题库
     * @param questionBankBean
     * @return
     */
    Integer addQuestion(QuestionBankBean questionBankBean);

    /**
     * 题目详情
     * @param id
     * @return
     */
    QuestionBankBean questionDetails(@Param("id") Long id);

    /**
     * 通过id获取所有数据
     * @param id
     * @return
     */
    QuestionBankBean details(@Param("id") Long id);

    /**
     * 通过id更改状态
     * @param questionBankBean
     * @return
     */
    Integer updateStatus(QuestionBankBean questionBankBean);

    Integer delete(Long id);
}
