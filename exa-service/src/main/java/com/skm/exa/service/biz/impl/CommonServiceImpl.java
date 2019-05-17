package com.skm.exa.service.biz.impl;

import com.skm.exa.common.enums.StatusEnum;
import com.skm.exa.common.object.AliyunOSSClientUtil;
import com.skm.exa.domain.bean.AreaBean;
import com.skm.exa.domain.bean.StatusBean;

import com.skm.exa.persistence.dao.CommonDao;
import com.skm.exa.persistence.dto.ImageCorrelationDto;
import com.skm.exa.service.biz.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    CommonDao commonDao;


    /**
     * 获得状态，从StatusEnum中获取
     * @return
     */
    @Override
    public List<StatusBean> getStatus() {
        List<StatusBean> statusBeans = new ArrayList<>();
        for(StatusEnum s:StatusEnum.values()){
            StatusBean statusBean = new StatusBean();
            statusBean.setCode(s.getIndex());
            statusBean.setName(s.getName());
            statusBeans.add(statusBean);
        }
        return statusBeans;
    }


    /**
     * 通过父级code获取地址
     * @param parentCode
     * @return
     */
    @Override
    public List<AreaBean> getAreaParentCode(Long parentCode) {
        return commonDao.getAreaParentCode(parentCode);
    }


    /**
     * 获得图片
     * @param list
     * @param correlationTableName
     * @return
     */
    @Override
    public List<ImageCorrelationDto> getImageList(List<Long> list,String correlationTableName) {
        return commonDao.getImageList(list,correlationTableName);
    }

    /**
     * 删除图片及关联
     * @param id
     * @param correlationTableName
     * @return
     */
    @Override
    @Transactional
    public Boolean deleteImage(List<Long> id, String correlationTableName) {
        List<ImageCorrelationDto> imageCorrelationDtos = getImageList(id,correlationTableName);
        if(imageCorrelationDtos == null || imageCorrelationDtos.size() == 0)
            return true;
        List<Long> correlation = new ArrayList<>();
        for(ImageCorrelationDto c:imageCorrelationDtos){
            correlation.add(c.getId());
        }
        int i = commonDao.deleteImage(correlation);
        if(i != correlation.size())
            return false;
        int s = commonDao.deleteImageCorrelation(id,correlationTableName);
        if(s <= 0)
            return false;

        //删除OSS上的图片
        if(imageCorrelationDtos == null || imageCorrelationDtos.size() == 0){
            for(ImageCorrelationDto imageCorrelationDto:imageCorrelationDtos){
                AliyunOSSClientUtil.deleteFile(imageCorrelationDto.getName());
            }
        }


        return true;
    }
}
