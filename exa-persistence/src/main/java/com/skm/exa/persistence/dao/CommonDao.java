package com.skm.exa.persistence.dao;

import com.skm.exa.domain.bean.AreaBean;
import com.skm.exa.domain.bean.ImageBean;
import com.skm.exa.persistence.dto.ImageCorrelationDto;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommonDao{

    /**
     * 根据父code，获得下一级的所有地址
     * @param code
     * @return
     */
    List<AreaBean> getAreaParentCode(Long code);

    /**
     * 通过图片的ID获得图片的信息
     * @param list
     * @return
     */
    List<ImageCorrelationDto> getImageList(@Param("list") List<Long> list,@Param("correlationTableName") String correlationTableName);


    /**
     * 添加图片
     * @param imageBean
     * @return
     */
    int addImage(List<ImageBean> imageBean);

    /**
     * 添加图片关联
     * @param imageId
     * @param correlationId
     * @param correlationName
     * @return
     */
    int addImageCorrelation(@Param("list")List<Long> imageId,@Param("correlationId")Long correlationId,@Param("correlationName") String correlationName);


    /**
     * 删除图片
     * @param list 图片ID
     * @return
     */
    int deleteImage(@Param("list") List<Long> list);

    /**
     * 删除图片关联数据
     * @param list 关联ID
     * @param correlationTableName
     * @return
     */
    int deleteImageCorrelation(@Param("list") List<Long> list,@Param("correlationTableName") String correlationTableName);

}
