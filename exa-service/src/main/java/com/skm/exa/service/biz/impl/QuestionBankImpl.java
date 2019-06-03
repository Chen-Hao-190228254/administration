package com.skm.exa.service.biz.impl;

import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.utils.SetCommonElement;
import com.skm.exa.domain.bean.OptionCodesBean;
import com.skm.exa.domain.bean.TechnologicalTypeBean;
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

import java.util.ArrayList;
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
        //如果不传参数那么将设置题目类型 和 技术类型为1
        if (qoPageParam.getCondition().getTechnologicalTypeLike() == null || qoPageParam.getCondition().getTopicTypeLike() == null){
            qoPageParam.getCondition().setTechnologicalTypeLike((long)1);
            qoPageParam.getCondition().setTopicTypeLike((long) 1);
            return dao.selectQuestionPage(qoPageParam);
        }
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
        if(questionBankBean.getTechnologicalType() > 0 && questionBankBean.getTechnologicalType() <= 4 ){   //判断技术类型
            if (questionBankBean.getTopicType() > 0 && questionBankBean.getTopicType() <= 4 ){      // 判断题目类型
                if (questionBankBean.getTopicType() == 2 || questionBankBean.getTopicType() == 3 ){  //判断是否是选择题
                    System.out.print((int)((Math.random()*9+1)*100000));
                    questionBankBean.setOptionCodes((long)((Math.random()*9+1)*100000));  //设置随机数
                    SetCommonElement setCommonElement = new SetCommonElement();
                    setCommonElement.setAdd(questionBankBean,unifyAdmin );
                    dao.addQuestion(questionBankBean);
                    return questionBankBean;
                }
                if (questionBankBean.getTopicType() != 2 || questionBankBean.getTopicType() != 3){
                    SetCommonElement setCommonElement = new SetCommonElement();
                    setCommonElement.setAdd(questionBankBean,unifyAdmin );
                    questionBankBean.setOptionCodes((long) 0);
                    dao.addQuestion(questionBankBean);
                    return questionBankBean;
                }
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
     * @param questionBankBean
     * @return
     */
    @Override
    public QuestionBankBean updateStatus(QuestionBankBean questionBankBean) {
        dao.updateStatus(questionBankBean);
        return questionBankBean;
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
    public QuestionBankBean updateQuestion(QuestionBankBean questionBankBean ,UnifyAdmin unifyAdmin) {
        QuestionBankBean bean = dao.details(questionBankBean);
        if (bean.getStatus() == QuestionStatusEnum.NORMAL.getValue()){   //判断数据状态
            if (questionBankBean.getTechnologicalType() > 0 && questionBankBean.getTechnologicalType() <= 4 ){
                if (questionBankBean.getTopicType() > 0 && questionBankBean.getTopicType() <= 4){
                    if (questionBankBean.getTopicType() == 2 || questionBankBean.getTopicType() == 3 ){  //判断是否是选择题
                        questionBankBean.setOptionCodes((long)((Math.random()*9+1)*100000));  //设置随机数
                        SetCommonElement setCommonElement = new SetCommonElement();
                        setCommonElement.setupdate(questionBankBean,unifyAdmin );
                        System.out.println(questionBankBean.getEnterpriseName());
                        dao.updateQuestion(questionBankBean);
                        return questionBankBean;
                    }
                    if (questionBankBean.getTopicType() != 2 || questionBankBean.getTopicType() != 3){
                        questionBankBean.setOptionCodes((long)0);  //设置随机数
                        SetCommonElement setCommonElement = new SetCommonElement();
                        setCommonElement.setupdate(questionBankBean,unifyAdmin );
                        dao.updateQuestion(questionBankBean);
                        return questionBankBean;
                    }
                }
            }

        }
        return null;
    }

    /**
     * 获取所有技术类型
     * @param technologicalTypeBean
     * @return
     */
    @Override
    public List<TechnologicalTypeBean> selectBankType(TechnologicalTypeBean technologicalTypeBean) {
        return dao.selectBankType(technologicalTypeBean);
    }

    /**
     * 通过id获取技术类型
     * @param technologicalTypeBean
     * @param
     * @return
     */
    @Override
    public List<TechnologicalTypeBean> selectBank(TechnologicalTypeBean technologicalTypeBean, QuestionBankBean questionBankBean) {
        QuestionBankBean bean = dao.details(questionBankBean ); //查询当前id获取的数据
        List<TechnologicalTypeBean> technologicalTypeBeanList = dao.selectBankType(technologicalTypeBean);   //查询当前所有技术类型
        for (TechnologicalTypeBean option: technologicalTypeBeanList){
            option.getType();
            if (bean.getTechnologicalType() == QuestionTechnologicalTypeEnum.PROGRAMME.getValue() ){
                if ( bean.getTechnologicalType() == option.getType() ){
                    return  dao.selectBank(technologicalTypeBean);
                }
            }
            if (bean.getTechnologicalType() == QuestionTechnologicalTypeEnum.ARITHMETIC.getValue() ){
                if ( bean.getTechnologicalType() == option.getType() ){
                    return  dao.selectBank(technologicalTypeBean);
                }
            }
            if (bean.getTechnologicalType() == QuestionTechnologicalTypeEnum.DATABASE.getValue() ){
                if ( bean.getTechnologicalType() == option.getType() ){
                    return  dao.selectBank(technologicalTypeBean);
                }
            }
            if (bean.getTechnologicalType() == QuestionTechnologicalTypeEnum.OPTIMIZE.getValue() ){
                if ( bean.getTechnologicalType() == option.getType() ){
                    return  dao.selectBank(technologicalTypeBean);
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

    /**
     * 选择题，单选题，添加选项
     * @param optionCodesBean
     * @param questionBankBean
     * @param unifyAdmin
     * @return
     */
    @Override
    public OptionCodesBean addBankOption(OptionCodesBean optionCodesBean,QuestionBankBean questionBankBean, UnifyAdmin unifyAdmin) {
        QuestionBankBean bankBean = dao.details(questionBankBean);
        if (bankBean.getTopicType() == 2 || bankBean.getTopicType() == 3 ){
            optionCodesBean.setCode(bankBean.getOptionCodes());
            SetCommonElement setCommonElement = new SetCommonElement() ;
            setCommonElement.setAdd(optionCodesBean,unifyAdmin );
            dao.addOptionCodes(optionCodesBean);
            return optionCodesBean ;
        }
        return null;
    }

    /**
     * 删除选项
     * @param optionCodesBean
     * @param questionBankBean
     * @return
     */
    @Override
    public boolean deleteBankOption(OptionCodesBean optionCodesBean ,QuestionBankBean questionBankBean) {
        QuestionBankBean bankBean = dao.details(questionBankBean);
        optionCodesBean.setCode(bankBean.getOptionCodes());
        optionCodesBean.getCode();
        List<OptionCodesBean> bean = dao.selectOptionCodes(optionCodesBean);
        for (OptionCodesBean codesBean : bean){
            System.out.println(codesBean.getCode().toString());
            dao.deleteOptionCodes(optionCodesBean);
            return true ;
        }
        return false;
    }

    /**
     * 查询选项
     * @param optionCodesBean
     * @return
     */
    @Override
    public List<OptionCodesBean> selectBankOption(OptionCodesBean optionCodesBean) {
            return  dao.selectOptionCodes(optionCodesBean);
    }
}
