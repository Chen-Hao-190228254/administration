package com.skm.exa.service.biz.impl;

import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.domain.bean.EnterpriseBean;
import com.skm.exa.domain.bean.ImageBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.dao.EnterpriseDao;
import com.skm.exa.persistence.dto.EnterpriseDto;
import com.skm.exa.persistence.dto.EnterpriseSaveDto;
import com.skm.exa.persistence.dto.EnterpriseUpdateDto;
import com.skm.exa.persistence.dto.ImageCorrelationDto;
import com.skm.exa.persistence.qo.EnterpriseQO;
import com.skm.exa.service.BaseServiceImpl;
import com.skm.exa.service.biz.CommonService;
import com.skm.exa.service.biz.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EnterpriseServiceImpl extends BaseServiceImpl<EnterpriseBean, EnterpriseDao> implements EnterpriseService {




    /**
     * 获取所有企业信息
     * @return
     */
    @Override
    public List<EnterpriseDto> getEnterpriseList() {
        List<EnterpriseBean> enterpriseBeans = dao.getEnterpriseList();
        List<EnterpriseDto> enterpriseDtos = enterpriseImage(enterpriseBeans);
        return enterpriseDtos;
    }


    /**
     * 根据ID获取企业
     * @param id
     * @return
     */
    @Override
    public EnterpriseDto getEnterprise(Long id) {
        EnterpriseBean enterpriseBean = dao.getEnterprise(id);
        return enterpriseImage(enterpriseBean);
    }

    /**
     * 分页获取企业
     * @param pageParam
     * @return
     */
    @Override
    public Page<EnterpriseDto> getEnterprisePage(PageParam<EnterpriseQO> pageParam) {
        Page<EnterpriseBean> enterpriseBeanPage = dao.getEnterprisePage(pageParam);
        List<EnterpriseBean> enterpriseBeans = enterpriseBeanPage.getContent();
        List<EnterpriseDto> enterpriseDtos = enterpriseImage(enterpriseBeans);
        Page<EnterpriseDto> page = BeanMapper.map(enterpriseBeanPage,Page.class);
        page.setContent(enterpriseDtos);
        return page;
    }

    /**
     * 添加企业
     * @return
     */
    @Override
    public Result<EnterpriseDto> addEnterprise(EnterpriseSaveDto enterpriseSaveDto, UnifyAdmin unifyAdmin) {
        return null;
    }

    /**
     * 更新企业
     * @return
     */
    @Override
    public Result<EnterpriseDto> updateEnterprise(EnterpriseUpdateDto enterpriseUpdateDto, UnifyAdmin unifyAdmin) {
        return null;
    }

    /**
     * 删除企业
     * @return
     */
    @Override
    @Transactional
    public Boolean deleteEnterprise(Long id) {
        List<Long> list = new ArrayList<>();
        list.add(id);
        boolean is = deleteImage(list);
        if(!is)
            return false;
        int i = dao.deleteEnterprise(id);
        if(i<=0)
            return false;
        return true;
    }

    /**
     * 更新企业状态
     * @return
     */
    @Override
    public EnterpriseDto setEnterpriseStatus() {
        return null;
    }


//---------------------------------图片处理----------------------------------------------

    @Autowired
    CommonService commonService;

    /**
     * 获取图片
     * @param enterpriseBean
     * @return
     */
     @Override
     public EnterpriseDto enterpriseImage(EnterpriseBean enterpriseBean){
        if(enterpriseBean == null)
            return null;
        List<Long> longs = new ArrayList<>();
        longs.add(enterpriseBean.getId());
        List<ImageCorrelationDto> imageCorrelationDtos = commonService.getImageList(longs,"administration_enterprise");
        List<ImageBean> imageBeans = BeanMapper.mapList(imageCorrelationDtos,ImageCorrelationDto.class,ImageBean.class);
        EnterpriseDto enterpriseDto = BeanMapper.map(enterpriseBean,EnterpriseDto.class);
        enterpriseDto.setImageBeans(imageBeans);
        return enterpriseDto;
     }


    /**
     * 获取图片
     * @param enterpriseBeans
     * @return
     */
    @Override
    public List<EnterpriseDto> enterpriseImage(List<EnterpriseBean> enterpriseBeans ){
        if(enterpriseBeans == null || enterpriseBeans.size() == 0)
            return null;
        List<Long> enterpriseId = new ArrayList<>();
        Map<Long,EnterpriseDto> mapEnterprise = new HashMap<>();
        for(EnterpriseBean enterpriseBean:enterpriseBeans){
            enterpriseId.add(enterpriseBean.getId());
            mapEnterprise.put(enterpriseBean.getId(), BeanMapper.map(enterpriseBean,EnterpriseDto.class));
        }
        List<ImageCorrelationDto> imageBeans = commonService.getImageList(enterpriseId,"administration_enterprise");
        if(imageBeans == null)
            return BeanMapper.mapList(enterpriseBeans,EnterpriseBean.class,EnterpriseDto.class);
        Map<Long,List<ImageBean>> mapImage = new HashMap<>();
        for(ImageCorrelationDto i:imageBeans){
            if(mapImage.containsKey(i.getCorrelationId())){
                mapImage.get(i.getCorrelationId()).add(BeanMapper.map(i,ImageBean.class));
            }else {
                List<ImageBean> imageBeanList = new ArrayList<>();
                imageBeanList.add(BeanMapper.map(i,ImageBean.class));
                mapImage.put(i.getCorrelationId(),imageBeanList);
            }
        }
        for(Long key:mapEnterprise.keySet()){
            if(mapImage.containsKey(key)){
                mapEnterprise.get(key).setImageBeans(mapImage.get(key));
            }
        }
        List<EnterpriseDto> enterpriseDtos = new ArrayList<>();
        for(Long key:mapEnterprise.keySet()){
            enterpriseDtos.add(mapEnterprise.get(key));
        }
        return enterpriseDtos;
    }


    /**
     * 删除图片及关联
     * @param list
     * @return
     */
    @Override
    public boolean deleteImage(List<Long> list){
        return commonService.deleteImage(list,"administration_enterprise");
    }


}
