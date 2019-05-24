package com.skm.exa.service.biz;

import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.domain.bean.EnterpriseBean;
import com.skm.exa.domain.bean.FileBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.dto.EnterpriseDto;
import com.skm.exa.persistence.dto.EnterpriseSaveDto;
import com.skm.exa.persistence.dto.EnterpriseUpdateDto;
import com.skm.exa.persistence.dto.FileCorrelationSaveDto;
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
    Result<EnterpriseDto> addEnterprise(EnterpriseSaveDto enterpriseSaveDto, UnifyAdmin unifyAdmin);

    /**
     * 更新企业
     * @return
     */
    Result<EnterpriseDto> updateEnterprise(EnterpriseUpdateDto enterpriseUpdateDto,UnifyAdmin unifyAdmin);

    /**
     * 删除企业
     * @return
     */
    Boolean deleteEnterprise(Long id);


    /**
     * 设置企业状态
     * @return
     */
    EnterpriseDto setEnterpriseStatus(Long id);


//------------------------图片获取--------------------------------------


    /**
     * 获取图片
     * @param enterpriseBean 需要获取图片的Bean
     * @return
     */
    EnterpriseDto getEnterpriseImage(EnterpriseBean enterpriseBean);


    /**
     * 获取图片
     * @param enterpriseBeans 需要获取图片的List<Bean>
     * @return
     */
    List<EnterpriseDto> getEnterpriseImage(List<EnterpriseBean> enterpriseBeans );


    /**
     * 向数据库添加图片关联
     * @param fileCorrelationSaveDtos
     * @return
     */
    Result addImage(List<FileCorrelationSaveDto> fileCorrelationSaveDtos,Long enterpriseId);




    /**
     * 删除照片及关联
     * @param list
     * @return
     */
    boolean deleteImage(List<Long> list);



    Result<List<FileBean>> updateImage(List<FileCorrelationSaveDto> fileCorrelationSaveDtos, Long enterpriseId);

}
