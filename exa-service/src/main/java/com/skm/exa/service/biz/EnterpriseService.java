package com.skm.exa.service.biz;

import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.domain.bean.EnterpriseBean;
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




//------------------------图片获取--------------------------------------


    /**
     * 获取图片
     * @param enterpriseBean 需要获取图片的Bean
     * @return
     */
    EnterpriseDto getEnterpriseImageMessage(EnterpriseBean enterpriseBean);


    /**
     * 获取图片
     * @param enterpriseBeans 需要获取图片的List<Bean>
     * @return
     */
    List<EnterpriseDto> getEnterpriseImageMessage(List<EnterpriseBean> enterpriseBeans );


    /**
     * 向数据库添加图片信息
     * @param fileSaveDtos
     * @return
     */
    Boolean addImageMessage(List<FileSaveDto> fileSaveDtos, Long enterpriseId);




    /**
     * 删除照片及关联
     * @param fileDeleteDto
     * @return
     */
    Boolean deleteImageMessage(FileDeleteDto fileDeleteDto);


    /**
     * 更新图片
     * @param fileUpdateDtos
     * @return
     */
    Boolean updateImageMessage(List<FileUpdateDto> fileUpdateDtos);

}
