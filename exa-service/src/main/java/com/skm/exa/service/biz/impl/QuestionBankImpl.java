package com.skm.exa.service.biz.impl;

import com.google.common.collect.Lists;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.common.utils.SetCommonElement;
import com.skm.exa.domain.bean.OptionCodesBean;
import com.skm.exa.domain.bean.TechnologicalTypeBean;
import com.skm.exa.domain.bean.QuestionBankBean;
import com.skm.exa.domain.bean.QuestionTypeBean;
import com.skm.exa.mybatis.Ognl;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.mybatis.enums.QuestionStatusEnum;
import com.skm.exa.mybatis.enums.QuestionTechnologicalTypeEnum;
import com.skm.exa.persistence.dao.QuestionBankDao;
import com.skm.exa.persistence.dto.*;
import com.skm.exa.persistence.qo.QuestionBankLikeQO;
import com.skm.exa.persistence.qo.QuestionOptionQO;
import com.skm.exa.persistence.qo.QuestionQueryLikeQO;
import com.skm.exa.service.BaseServiceImpl;
import com.skm.exa.service.biz.QuestionBankService;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


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
        //如果不传参数那么将设置技术类型为1
        if (qoPageParam.getCondition().getTechnologicalTypeLike() == null || qoPageParam.getCondition().getTopicTypeLike() == null){
            return dao.selectQuestionPage(qoPageParam);
        }
        return dao.selectQuestionPage(qoPageParam);
    }

    /**
     * 添加题库
     * @param questionBankSaveDto
     * @param unifyAdmin
     * @return
     */
    @Override
    public QuestionBankDto addQuestion(QuestionBankSaveDto questionBankSaveDto,UnifyAdmin unifyAdmin) {
        QuestionBankBean bean = BeanMapper.map(questionBankSaveDto, QuestionBankBean.class);
        bean = new SetCommonElement().setAdd(bean,unifyAdmin );
        double optionCode = (Math.random()*9+1)*100000;
        bean.setOptionCodes((long) optionCode);
        dao.addQuestion(bean);  //添加题库
        List<OptionCodesBean> optionCodesBeans = new ArrayList<>();
        if(bean.getTopicType() == 2 || bean.getTopicType() == 3){
            List<OptionCodesDto> codesDtoList = questionBankSaveDto.getOptionCodesDtoList();
            for (OptionCodesDto codesDto: codesDtoList) {
                codesDto.setCode((long) optionCode);
                OptionCodesDto optionCodesDto = new SetCommonElement().setAdd(codesDto,unifyAdmin );
                optionCodesBeans.add(BeanMapper.map(optionCodesDto, OptionCodesBean.class));
            }
            dao.addOptionCodes(optionCodesBeans); //添加选项
        }
        QuestionBankDto questionBankDto = BeanMapper.map(bean, QuestionBankDto.class);
        questionBankDto.setOptionCodesBeans(optionCodesBeans);
        return questionBankDto;
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
    public QuestionBankDto details(QuestionBankBeanDetailsDto questionBankBeanDetailsDto) {
        QuestionBankBean dto =  BeanMapper.map(questionBankBeanDetailsDto,QuestionBankBean.class );
        QuestionBankBean bankBean = dao.details(dto);
        QuestionBankDto bankDto = BeanMapper.map(bankBean,QuestionBankDto.class );
        if (bankBean.getTopicType() == 2 || bankBean.getTopicType() == 3){
            bankDto.setOptionCodesBeans(dao.selectOptionCodes(new ArrayList<>(Collections.singleton(bankBean.getOptionCodes()))));
        }
        return bankDto;
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
     * @param
     * @return
     */
    @Override
    public boolean deleteQuestion(QuestionBankBeanDetailsDto questionBankBeanDetailsDto) {
        QuestionBankBean questionBankBean = BeanMapper.map(questionBankBeanDetailsDto,QuestionBankBean.class );
        QuestionBankBean bankBean  = dao.details(questionBankBean);
        if (bankBean.getStatus() == QuestionStatusEnum.NORMAL.getValue()){   //判断状态
            dao.deleteQuestion(questionBankBean);
            if (bankBean.getTopicType() == 2 || bankBean.getTopicType() == 3){ // 判断题目类型
                 dao.deleteOptionCodes(bankBean.getOptionCodes());
            }
            return true ;
        }else if (bankBean.getStatus() == QuestionStatusEnum.FORBIDDEN.getValue()){
            return false;
        }
        return true;
    }

    /**
     * 更新数据
     * @param
     * @param unifyAdmin
     * @return
     */
    @Override
    public QuestionBankDto updateQuestion(QuestionBankUpdateDto updateDto ,UnifyAdmin unifyAdmin) {
        QuestionBankBean bean = BeanMapper.map(updateDto,QuestionBankBean.class );
        QuestionBankBean bankBean = dao.details(bean);  //查询数据
        bean  = new SetCommonElement().setupdate(bean ,unifyAdmin );
        dao.updateQuestion(bean);  //更新题库
        List<OptionCodesBean> updateBeans = new ArrayList<>();  //更新list
        List<OptionCodesBean> addBeans = new ArrayList<>();   // 添加list
        if(bean.getTopicType() == 2 || bean.getTopicType() == 3){  //判断是否为选择题
            List<OptionCodesUpdateDto> codesDtoList = updateDto.getOptionCodesUpdateDtoList();  //取得选项
            List<OptionCodesBean> oldDatas = dao.selectOptionCodes(   //查询所有选项，获取未更新时候的选项
                    Lists.newArrayList(bankBean.getOptionCodes()));
            Map<Long, OptionCodesBean> map = oldDatas.stream().collect(Collectors.toMap(OptionCodesBean::getId, b->b));
            for (OptionCodesUpdateDto codesDto: codesDtoList) {
                if (map.get(codesDto.getId()) != null ){   //判断如果传入id则更新选项 ，否者则添加选项
                    if (bankBean.getOptionCodes().equals(map.get(codesDto.getId()).getCode()) ){
                        codesDto.setCode(bankBean.getOptionCodes());
                        SetCommonElement.setupdate(codesDto, unifyAdmin);
                        updateBeans.add(BeanMapper.map(codesDto, OptionCodesBean.class));
                    }
                }else{
                    codesDto.setCode(bankBean.getOptionCodes());
                    SetCommonElement.setAdd(codesDto, unifyAdmin);
                    addBeans.add(BeanMapper.map(codesDto, OptionCodesBean.class));
                }
                map.remove(codesDto.getId());
            }
            if (Ognl.isNotEmpty(updateBeans)) {
                dao.updateBank(updateBeans);  //更新选项
            }
            if (Ognl.isNotEmpty(addBeans)) {
                dao.addOptionCodes(addBeans);  //添加选项
            }
        }else if(bean.getTopicType() != 2 || bean.getTopicType() != 3){  //如果改为不是选择题者删除所有选项
            List<OptionCodesBean> beans = dao.selectOptionCodes(new ArrayList<>(Collections.singleton(bankBean.getOptionCodes())));
            for (OptionCodesBean codesBean: beans){
                codesBean.getCode();
                dao.deleteOptionCodes(codesBean.getCode());
            }
        }
        QuestionBankDto questionBankDto = BeanMapper.map(updateDto, QuestionBankDto.class);
        questionBankDto.setOptionCodesBeans(updateBeans);
        return questionBankDto;
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
    @Transactional
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
     * @param
     * @param
     * @param
     * @return
     */
 /*  // @Override
    public Integer addBankOption(QuestionBankSaveDto questionBankSaveDto ,QuestionBankBean questionBankBean ,UnifyAdmin unifyAdmin) {
        List<OptionCodesDto> codesDtoList = questionBankSaveDto.getOptionCodesDtoList();
        for (OptionCodesDto optionCodesDto: codesDtoList){
            optionCodesDto.setCode(questionBankBean.getOptionCodes());
            dao.addOptionCodes(optionCodesDto);
            SetCommonElement setCommonElement = new SetCommonElement() ;
            setCommonElement.setAdd(questionBankBean, unifyAdmin);
            System.out.println(dao.addOptionCodes(optionCodesDto).toString());
            return dao.addOptionCodes(optionCodesDto);
        }
            return 0 ;
        }*/


    /**
     * 删除选项
     * @param optionCodesBean
     * @param
     * @return
     */
    @Override
    public boolean deleteBankOption(OptionCodesBean optionCodesBean ) {
            dao.deleteOption(optionCodesBean.getId());
            return true ;

    }

    /**
     * 查询选项
     * @param optionCodesBean
     * @return
     */
    //@Override
   /* public List<OptionCodesBean> selectBankOption(QuestionBankBeanDetailsDto questionBankBeanDetailsDto ,OptionCodesBean optionCodesBean) {

            return  optionCodesDto ;
    }*/
}
