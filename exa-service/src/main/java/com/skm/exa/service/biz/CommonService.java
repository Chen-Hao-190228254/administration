package com.skm.exa.service.biz;

import com.skm.exa.domain.bean.AreaBean;
import com.skm.exa.domain.bean.ImageBean;
import com.skm.exa.domain.bean.StatusBean;
import com.skm.exa.persistence.dto.ImageCorrelationDto;

import java.util.List;

public interface CommonService {

    /**
     * 获得状态，从StatusEnum中获取
     * @return
     */
    List<StatusBean> getStatus();

    /**
     * 通过父级code获取地址
     * @param parentCode
     * @return
     */
    List<AreaBean> getAreaParentCode(Long parentCode);


    /**
     *获取图片
     * @param list
     * @return
     */
    List<ImageCorrelationDto> getImageList(List<Long> list, String correlationTableName);

    /**
     * 删除图片
     * @param id
     * @return
     */
    Boolean deleteImage(List<Long> id, String correlationTableName);


}
