package com.skm.exa.service.biz.impl;

import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.utils.SetCommonElement;
import com.skm.exa.domain.bean.BankOptionBean;
import com.skm.exa.domain.bean.QuestionBankBean;
import com.skm.exa.domain.bean.QuestionTypeBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.mybatis.enums.QuestionStatusEnum;
import com.skm.exa.mybatis.enums.QuestionTechnologicalTypeEnum;
import com.skm.exa.persistence.dao.QuestionBankDao;
import com.skm.exa.persistence.dto.QuestionBankDto;
import com.skm.exa.persistence.dto.QuestionQueryDto;
import com.skm.exa.persistence.qo.QuestionBankLikeQO;
import com.skm.exa.persistence.qo.QuestionQueryLikeQO;
import com.skm.exa.service.BaseServiceImpl;
import com.skm.exa.service.biz.QuestionBankService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionBankImpl extends BaseServiceImpl<QuestionBankBean , QuestionBankDao> implements QuestionBankService  {
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
     * 按条件搜索数据
     * @param qoPageParam
     * @return
     */
    @Override
    public Page<QuestionQueryDto> selectPage(PageParam<QuestionQueryLikeQO> qoPageParam) {
        return dao.selectQuestionPage(qoPageParam);
    }

    /**
     * 添加题库
     * @param questionBankBean
     * @param unifyAdmin
     * @return
     */
    @Override
    public QuestionBankBean addQuestion(QuestionBankBean questionBankBean, UnifyAdmin unifyAdmin) {
        if(questionBankBean.getTechnologicalType() > 0 && questionBankBean.getTechnologicalType() <= 4 ){
            if (questionBankBean.getTopicType() > 0 && questionBankBean.getTopicType() <= 4 ){
                SetCommonElement setCommonElement = new SetCommonElement();
                setCommonElement.setAdd(questionBankBean,unifyAdmin );
                dao.addQuestion(questionBankBean);
                return questionBankBean;
            }
        }
        return null;
    }

    /**
     * 题目详情
     * @param
     * @param questionBankBean
     * @return
     */
    @Override
    public QuestionBankBean questionDetails( QuestionBankBean questionBankBean ) {
        QuestionBankBean bankBean = dao.questionDetails(questionBankBean);
        return bankBean ;
    }

    /**
     * 通过id获取所有数据
     * @param
     * @param
     * @return
     */
    @Override
    public QuestionBankBean details(QuestionBankBean questionBankBean) {
        return dao.details(questionBankBean);
    }

    /**
     * 通过id更改状态
     * @param questionBankBean
     * @param id
     * @return
     */
    @Override
    public QuestionBankBean updateStatus(QuestionBankBean questionBankBean, Long id) {
        QuestionBankBean bean =dao.details(questionBankBean);
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

    /**
     * 输入id删除数据
     * @param questionBankBean
     * @return
     */
    @Override
    public boolean delete(QuestionBankBean questionBankBean) {
        QuestionBankBean bankBean  = dao.details(questionBankBean);
        if (bankBean.getStatus() == QuestionStatusEnum.NORMAL.getValue()){
            dao.delete(bankBean.getId());
            return true ;
        }else if (bankBean.getStatus() == QuestionStatusEnum.FORBIDDEN.getValue()){
            return false;
        }
        return true;
    }

    /**
     * 更新数据
     * @param questionBankBean
     * @param unifyAdmin
     * @return
     */
    @Override
    public boolean updateQuestion(QuestionBankBean questionBankBean ,UnifyAdmin unifyAdmin) {
        QuestionBankBean bean = dao.details(questionBankBean);
        if (bean.getStatus() == QuestionStatusEnum.NORMAL.getValue()){
            if (questionBankBean.getTechnologicalType() > 0 && questionBankBean.getTechnologicalType() <= 4 ){
                if (questionBankBean.getTopicType() > 0 && questionBankBean.getTopicType() <= 4){
                    SetCommonElement setCommonElement = new SetCommonElement();
                    setCommonElement.setupdate(questionBankBean,unifyAdmin );
                    dao.updateQuestion(questionBankBean);
                    return true;
                }
            }

        }else {
            return false;
        }
        return false;
    }

    /**
     * 获取所有技术类型
     * @param bankOptionBean
     * @return
     */
    @Override
    public List<BankOptionBean> selectBankType(BankOptionBean bankOptionBean) {
        return dao.selectBankType(bankOptionBean);
    }

    /**
     * 通过id获取技术类型
     * @param bankOptionBean
     * @param
     * @return
     */
    @Override
    public List<BankOptionBean> selectBank(BankOptionBean bankOptionBean,QuestionBankBean questionBankBean) {
        QuestionBankBean bean = dao.details(questionBankBean ); //查询当前id获取的数据
        List<BankOptionBean> bankOptionBeanList = dao.selectBankType(bankOptionBean);   //查询当前所有技术类型
        for (BankOptionBean option: bankOptionBeanList){
            option.getType();
            if (bean.getTechnologicalType() == QuestionTechnologicalTypeEnum.PROGRAMME.getValue() ){
                if ( bean.getTechnologicalType() == option.getType() ){
                    return  dao.selectBank(bankOptionBean);
                }
            }
            if (bean.getTechnologicalType() == QuestionTechnologicalTypeEnum.ARITHMETIC.getValue() ){
                if ( bean.getTechnologicalType() == option.getType() ){
                    return  dao.selectBank(bankOptionBean);
                }
            }
            if (bean.getTechnologicalType() == QuestionTechnologicalTypeEnum.DATABASE.getValue() ){
                if ( bean.getTechnologicalType() == option.getType() ){
                    return  dao.selectBank(bankOptionBean);
                }
            }
            if (bean.getTechnologicalType() == QuestionTechnologicalTypeEnum.OPTIMIZE.getValue() ){
                if ( bean.getTechnologicalType() == option.getType() ){
                    return  dao.selectBank(bankOptionBean);
                }
            }
        }
            return null;
    }

    /**
     * 获取所有问题类型
     * @param questionTypeBean
     * @return
     */
    @Override
    public List<QuestionTypeBean> selectQuestionType(QuestionTypeBean questionTypeBean) {
        return dao.selectQuestionType(questionTypeBean);
    }

    /**
     * 通过id获取问题类型
     * @param questionTypeBean
     * @param
     * @return
     */
    @Override
    public List<QuestionTypeBean> selectTopicType(QuestionTypeBean questionTypeBean ,QuestionBankBean questionBankBean) {
        QuestionBankBean bean  = dao.details(questionBankBean);
        List<QuestionTypeBean> list = dao.selectQuestionType(questionTypeBean);
        for (QuestionTypeBean typeBean : list){
            typeBean.getType();
            if (bean.getTopicType() == 1){
                if (typeBean.getType() == bean.getTopicType()){
                    return dao.selectTopicType(questionTypeBean);
                }
            }
            if (bean.getTopicType() == 2){
                if (typeBean.getType() == bean.getTopicType()){
                    return dao.selectTopicType(questionTypeBean);
                }
            }
            if (bean.getTopicType() == 3){
                if (typeBean.getType() == bean.getTopicType()){
                    return dao.selectTopicType(questionTypeBean);
                }
            }
            if (bean.getTopicType() == 4){
                if (typeBean.getType() == bean.getTopicType()){
                    return dao.selectTopicType(questionTypeBean);
                }
            }
        }
        return null;
    }
}
