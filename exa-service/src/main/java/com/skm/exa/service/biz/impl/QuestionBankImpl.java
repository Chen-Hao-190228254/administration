package com.skm.exa.service.biz.impl;

import com.skm.exa.common.enums.Msg;
import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.object.UnifyUser;
import com.skm.exa.common.service.UnifyUserService;
import com.skm.exa.domain.bean.QuestionBankBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.mybatis.enums.QuestionStatusEnum;
import com.skm.exa.persistence.dao.QuestionBankDao;
import com.skm.exa.persistence.dto.QuestionBankDto;
import com.skm.exa.persistence.qo.QuestionBankLikeQO;
import com.skm.exa.service.BaseServiceImpl;
import com.skm.exa.service.biz.QuestionBankService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class QuestionBankImpl extends BaseServiceImpl<QuestionBankBean , QuestionBankDao> implements QuestionBankService , UnifyUserService {
    @Override
    public UnifyUser loadUserByUsername(String username) {
        return null;
    }

    /**
     * 分页模糊搜索
     * @param qoPageParam
     * @return
     */
    @Override
    public Page<QuestionBankDto> page(PageParam<QuestionBankLikeQO> qoPageParam) {
        return dao.page(qoPageParam);
    }

    /**
     * 添加题库
     * @param questionBankBean
     * @param unifyAdmin
     * @return
     */
    @Override
    public QuestionBankBean addQuestion(QuestionBankBean questionBankBean, UnifyAdmin unifyAdmin) {
        questionBankBean.setEntryId(unifyAdmin.getId());
        questionBankBean.setEntryName(unifyAdmin.getName());
        questionBankBean.setEntryDt(new Date());
        questionBankBean.setUpdateId(unifyAdmin.getId());
        questionBankBean.setUpdateName(unifyAdmin.getName());
        questionBankBean.setUpdateDt(new Date());
        dao.addQuestion(questionBankBean);
        return questionBankBean;
    }

    /**
     * 题目详情
     * @param questionBankBean
     * @param id
     * @return
     */
    @Override
    public QuestionBankBean questionDetails(QuestionBankBean questionBankBean, Long id) {
        QuestionBankBean bankBean = dao.questionDetails(id);
        return bankBean ;
    }

    /**
     * 通过id获取所有数据
     * @param questionBankBean
     * @param id
     * @return
     */
    @Override
    public QuestionBankBean details(QuestionBankBean questionBankBean, Long id) {

        return dao.details(id);
    }

    /**
     * 通过id更改状态
     * @param questionBankBean
     * @param id
     * @return
     */
    @Override
    public QuestionBankBean updateStatus(QuestionBankBean questionBankBean, Long id) {
        QuestionBankBean bean =dao.details(id);
        if (bean.getStatus() != null){
            if (bean.getStatus() == QuestionStatusEnum.NORMAL.getValue()){
                questionBankBean.setStatus((long) 1);
                dao.updateStatus(questionBankBean);
                return questionBankBean;
            }
            if (bean.getStatus() == QuestionStatusEnum.FORBIDDEN.getValue()){
                questionBankBean.setStatus((long) 0);
                dao.updateStatus(questionBankBean);
                return questionBankBean;
            }
        }
        return null;
    }

    @Override
    public Result<Integer> delete(Long id) {
        QuestionBankBean bankBean  = dao.details(id);
        if (bankBean.getStatus() == QuestionStatusEnum.NORMAL.getValue()){

            return Result.success(dao.delete(id));
        }else if (bankBean.getStatus() == QuestionStatusEnum.FORBIDDEN.getValue()){
            Result result = new Result();
            result.setMessage("当前状态不允许删除");
            return result;
        }
        return null;
    }
}
