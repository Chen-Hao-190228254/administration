package com.skm.exa.service.biz.impl;

import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.common.utils.FileTidyingUtil;
import com.skm.exa.common.utils.SetCommonElement;
import com.skm.exa.domain.bean.EnterpriseBean;
import com.skm.exa.domain.bean.FileBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.dao.EnterpriseDao;
import com.skm.exa.persistence.dto.*;
import com.skm.exa.persistence.qo.EnterpriseQO;
import com.skm.exa.service.BaseServiceImpl;
import com.skm.exa.service.biz.CommonService;
import com.skm.exa.service.biz.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import java.util.*;

@Service
public class EnterpriseServiceImpl extends BaseServiceImpl<EnterpriseBean, EnterpriseDao> implements EnterpriseService {


    String correlationTableName = "administration_enterprise"; //关联表名


    @Autowired
    FileTidyingUtil fileTidyingUtil;

    /**
     * 获取所有企业信息
     * @return
     */
    @Override
    public List<EnterpriseDto> getEnterpriseList() {
        List<EnterpriseBean> enterpriseBeans = dao.select(null);
        return getEnterpriseImageMessage(enterpriseBeans);
    }


    /**
     * 根据ID获取企业
     * @param id
     * @return
     */
    @Override
    public EnterpriseDto getEnterprise(Long id) {
        EnterpriseBean enterpriseBean = dao.get(id);
        if(enterpriseBean == null)
            return null;
        return getEnterpriseImageMessage(enterpriseBean);
    }

    /**
     * 分页获取企业
     * @param pageParam
     * @return
     */
    @Override
    public Page<EnterpriseDto> getEnterprisePage(PageParam<EnterpriseQO> pageParam) {
        Page<EnterpriseBean> enterpriseBeanPage = dao.selectPage(pageParam);
        List<EnterpriseBean> enterpriseBeans = enterpriseBeanPage.getContent();
        List<EnterpriseDto> enterpriseDtos = getEnterpriseImageMessage(enterpriseBeans);
        Page<EnterpriseDto> page = BeanMapper.map(enterpriseBeanPage,Page.class);
        page.setContent(enterpriseDtos);
        return page;
    }

    /**
     * 添加企业
     * @return
     */
    @Override
    @Transactional
    public Boolean addEnterprise(EnterpriseSaveDto enterpriseSaveDto, UnifyAdmin unifyAdmin) {
        EnterpriseBean enterpriseBean = BeanMapper.map(enterpriseSaveDto,EnterpriseBean.class);
        if(enterpriseBean == null)
            return false;
        enterpriseBean = new SetCommonElement().setAdd(enterpriseBean,unifyAdmin);
        if(dao.insert(enterpriseBean)<=0)
            return false;
        if(enterpriseSaveDto.getFileSaveDtos() == null || enterpriseSaveDto.getFileSaveDtos().size() == 0)
            return true;
        //添加图片关联
        boolean is = addImageMessage(enterpriseSaveDto.getFileSaveDtos(),enterpriseBean.getId());
        return is;
    }

    /**
     * 更新企业
     * @return
     */
    @Override
    public Boolean updateEnterprise(EnterpriseUpdateDto enterpriseUpdateDto, UnifyAdmin unifyAdmin) {
        EnterpriseBean enterpriseBean = BeanMapper.map(enterpriseUpdateDto,EnterpriseBean.class);
        if(enterpriseBean == null)
            return false;
        enterpriseBean = new SetCommonElement().setupdate(enterpriseBean,unifyAdmin);
        int is = dao.update(enterpriseBean);
        if(is<=0)
            return false;
        if(enterpriseUpdateDto.getFileUpdateDtos() == null || enterpriseUpdateDto.getFileUpdateDtos().size() == 0)
            return true;
        boolean isUpdateImage = updateImageMessage(enterpriseUpdateDto.getFileUpdateDtos());
        return isUpdateImage;
    }

    /**
     * 删除企业
     * @return
     */
    @Override
    @Transactional
    public Boolean deleteEnterprise(Long id) {
        //获取要删除的图片信息
        EnterpriseDto enterpriseDto = getEnterprise(id);
        if(enterpriseDto == null)
            return true;
        if(dao.delete(id)<=0)
            return false;
        if(enterpriseDto.getImageBeans() == null || enterpriseDto.getImageBeans().size() == 0)
            return true;
        List<FileBean> fileBeans = enterpriseDto.getImageBeans();
        //图片ID集合
        List<Long> fileIds = new ArrayList<>();

        //图片名称集合
        List<String> filenames = new ArrayList<>();
        for(FileBean fileBean:fileBeans){
            fileIds.add(fileBean.getId());
            filenames.add(fileBean.getName());
        }
        return deleteImageMessage(new FileDeleteDto(fileIds,filenames));
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
     public EnterpriseDto getEnterpriseImageMessage(EnterpriseBean enterpriseBean){
        if(enterpriseBean == null)
            return null;
        List<FileBean> fileBeans = commonService.getFileList(new FileSelectDto(correlationTableName,new ArrayList<>(Collections.singleton(enterpriseBean.getId())) ));
        EnterpriseDto enterpriseDto = fileTidyingUtil.get(fileBeans,enterpriseBean,EnterpriseDto.class);
        return enterpriseDto;
     }


    /**
     * 获取图片
     * @param enterpriseBeans
     * @return
     */
    @Override
    public List<EnterpriseDto> getEnterpriseImageMessage(List<EnterpriseBean> enterpriseBeans){
        if(enterpriseBeans == null)
            return null;
        List<Long> correlationIds = new ArrayList<>();
        for(EnterpriseBean enterpriseBean:enterpriseBeans){
            correlationIds.add(enterpriseBean.getId());
        }
        List<FileBean> fileBeans = commonService.getFileList(new FileSelectDto(correlationTableName,correlationIds));
        List<EnterpriseDto> enterpriseDtos = fileTidyingUtil.getList(fileBeans,enterpriseBeans,EnterpriseBean.class,EnterpriseDto.class);
        return enterpriseDtos;
    }

    /**
     * 向数据库添加图片信息
     * @param fileSaveDtos
     * @return
     */
    @Validated
    @Transactional
    public Boolean addImageMessage(List<FileSaveDto> fileSaveDtos,Long enterpriseId){
        for(FileSaveDto fileCorrelationSaveDto:fileSaveDtos){
            fileCorrelationSaveDto.setCorrelationId(enterpriseId);
            fileCorrelationSaveDto.setCorrelationTableName(correlationTableName);
        }
        return commonService.addFileMessage(fileSaveDtos);
    }

    /**
     * 删除图片
     * @param fileDeleteDto
     * @return
     */
    @Override
    @Transactional
    public Boolean deleteImageMessage(FileDeleteDto fileDeleteDto){
        return commonService.deleteFileMessage(fileDeleteDto);
    }


    /**
     * 更新图片
     * @param fileUpdateDtos
     * @return
     */
    @Override
    @Transactional
    public Boolean updateImageMessage(List<FileUpdateDto> fileUpdateDtos){
        if(fileUpdateDtos == null || fileUpdateDtos.size() == 0)
            return true;
        List<FileBean> fileBeans = getEnterprise(fileUpdateDtos.get(0).getCorrelationId()).getImageBeans();
        for(int i = 0; i < fileBeans.size(); i++){
            for(int j = 0; j < fileUpdateDtos.size(); j++){
                if(fileUpdateDtos.get(j).getId() == fileBeans.get(i).getId()) {
                    fileBeans.remove(i);
                    fileUpdateDtos.remove(j);
                }
            }
        }
        //删除图片
        if(fileBeans != null && fileBeans.size() != 0){
            //图片ID集合
            List<Long> fileIds = new ArrayList<>();

            //图片名称集合
            List<String> filenames = new ArrayList<>();
            for(FileBean fileBean:fileBeans){
                fileIds.add(fileBean.getId());
                filenames.add(fileBean.getName());
            }
            boolean is = deleteImageMessage(new FileDeleteDto(fileIds,filenames));
            if(!is)
                return false;
        }

        //添加图片
        if(fileUpdateDtos != null && fileUpdateDtos.size() != 0){
            boolean is = addImageMessage(BeanMapper.mapList(fileUpdateDtos,FileUpdateDto.class,FileSaveDto.class),fileUpdateDtos.get(0).getCorrelationId());
            if(!is)
                return false;
        }
        return true;
    }




}
