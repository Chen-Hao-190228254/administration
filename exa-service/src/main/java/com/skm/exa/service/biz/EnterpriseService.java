package com.skm.exa.service.biz;

import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.domain.bean.EnterpriseBean;
import com.skm.exa.domain.bean.LabelBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.dto.*;
import com.skm.exa.persistence.qo.EnterpriseQO;
import com.skm.exa.service.BaseService;


import java.util.List;

public interface EnterpriseService extends BaseService<EnterpriseBean> {

    /**
     * 获取所有企业
     * @return
     */
    List<EnterpriseDto> getEnterpriseList();

    /**
     * 根据ID获取企业
     * @param id
     * @return
     */
    EnterpriseDto getEnterprise(Long id);


    /**
     * 分页获取企业，可加条件
     * @param pageParam
     * @return
     */
    Page<EnterpriseDto> getEnterprisePage(PageParam<EnterpriseQO> pageParam);

    /**
     * 添加企业
     * @return
     */
    Boolean addEnterprise(EnterpriseSaveDto enterpriseSaveDto, UnifyAdmin unifyAdmin);

    /**
     * 更新企业
     * @return
     */
    Boolean updateEnterprise(EnterpriseUpdateDto enterpriseUpdateDto,UnifyAdmin unifyAdmin);

    /**
     * 删除企业
     * @return
     */
    Boolean deleteEnterprise(Long id);




//------------------------图片及标签获取--------------------------------------


    /**
     * 获取图片及标签
     * @param enterpriseBean 需要获取图片的Bean
     * @return
     */
    EnterpriseDto getEnterpriseImageMessageAndLabel(EnterpriseBean enterpriseBean);


    /**
     * 获取图片
     * @param enterpriseBeans 需要获取图片的List<Bean>
     * @return
     */
    List<EnterpriseDto> getEnterpriseImageMessageAndLabel(List<EnterpriseBean> enterpriseBeans );


    /**
     * 向数据库添加图片信息及标签
     * @param fileSaveDtos
     * @return
     */
    Boolean addImageMessageAndLabel(List<FileSaveDto> fileSaveDtos,List<Long> labelIds, Long enterpriseId);




    /**
     * 删除照片及标签
     * @param fileDeleteDto
     * @return
     */
    Boolean deleteImageMessageAndLabel(FileDeleteDto fileDeleteDto, List<Long> labelIds);


    /**
     * 更新图片及标签
     * @param fileUpdateDtos
     * @return
     */
    Boolean updateImageMessageAndLabel(List<FileUpdateDto> fileUpdateDtos,List<Long> labelIds,Long enterpriseId);

}
