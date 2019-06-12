package com.skm.exa.service.biz.impl;

import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.common.utils.FileAndLabelTidyingUtil;
import com.skm.exa.common.utils.SetCommonElement;
import com.skm.exa.domain.bean.CorrelationLabelBean;
import com.skm.exa.domain.bean.EnterpriseBean;
import com.skm.exa.domain.bean.FileBean;
import com.skm.exa.domain.bean.LabelBean;
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
    CommonService commonService;

    @Autowired
    FileAndLabelTidyingUtil fileAndLabelTidyingUtil;

    /**
     * 获取所有企业信息
     * @return
     */
    @Override
    public List<EnterpriseDto> getEnterpriseList() {
        List<EnterpriseBean> enterpriseBeans = dao.select(null);
        return getEnterpriseImageMessageAndLabel(enterpriseBeans);
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
        return getEnterpriseImageMessageAndLabel(enterpriseBean);
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
        List<EnterpriseDto> enterpriseDtos = getEnterpriseImageMessageAndLabel(enterpriseBeans);
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
        if((enterpriseSaveDto.getFileSaveDtos() == null || enterpriseSaveDto.getFileSaveDtos().size() == 0) && (enterpriseSaveDto.getLabelIds() == null || enterpriseSaveDto.getLabelIds().size() == 0))
            return true;
        //添加图片及标签关联
        boolean is = addImageMessageAndLabel(enterpriseSaveDto.getFileSaveDtos(),enterpriseSaveDto.getLabelIds(),enterpriseBean.getId());
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
        boolean isUpdateImage = updateImageMessage(enterpriseUpdateDto.getFileUpdateDtos(),enterpriseUpdateDto.getLabelIds(),enterpriseUpdateDto.getId());
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
        if((enterpriseDto.getImageBeans() == null || enterpriseDto.getImageBeans().size() == 0)
                && (enterpriseDto.getLabelBeans() == null || enterpriseDto.getLabelBeans().size() == 0))
            return true;
        //图片ID集合
        List<Long> fileIds = new ArrayList<>();
        //图片名称集合
        List<String> filenames = new ArrayList<>();
        if(enterpriseDto.getImageBeans() != null || enterpriseDto.getImageBeans().size() != 0){
            for(FileBean fileBean:enterpriseDto.getImageBeans()){
                fileIds.add(fileBean.getId());
                filenames.add(fileBean.getName());
            }
        }
        //标签集合
        List<Long> labelIds = new ArrayList<>();
        List<CorrelationLabelBean> correlationLabelBeans = commonService.getCorrelationLabel(new ArrayList<>(Collections.singleton(id)),correlationTableName);
        if(correlationLabelBeans != null || correlationLabelBeans.size() != 0){
            for(CorrelationLabelBean labelBean:correlationLabelBeans){
                labelIds.add(labelBean.getCid());
            }
        }
        return deleteImageMessageAndLabel(new FileDeleteDto(fileIds,filenames),labelIds);
    }




//---------------------------------图片及标签处理----------------------------------------------



    /**
     * 获取图片及标签
     * @param enterpriseBean
     * @return
     */
     @Override
     public EnterpriseDto getEnterpriseImageMessageAndLabel(EnterpriseBean enterpriseBean){
        if(enterpriseBean == null)
            return null;
        List<FileBean> fileBeans = commonService.getFileList(new FileSelectDto(correlationTableName,new ArrayList<>(Collections.singleton(enterpriseBean.getId())) ));
        List<CorrelationLabelBean> correlationLabelBeans = commonService.getCorrelationLabel(new ArrayList<>(Collections.singleton(enterpriseBean.getId())),correlationTableName);
        EnterpriseDto enterpriseDto = fileAndLabelTidyingUtil.get(fileBeans,correlationLabelBeans,enterpriseBean,EnterpriseDto.class);
        return enterpriseDto;
     }


    /**
     * 获取图片及标签
     * @param enterpriseBeans
     * @return
     */
    @Override
    public List<EnterpriseDto> getEnterpriseImageMessageAndLabel(List<EnterpriseBean> enterpriseBeans){
        if(enterpriseBeans == null)
            return null;
        List<Long> correlationIds = new ArrayList<>();
        for(EnterpriseBean enterpriseBean:enterpriseBeans){
            correlationIds.add(enterpriseBean.getId());
        }
        List<FileBean> fileBeans = commonService.getFileList(new FileSelectDto(correlationTableName,correlationIds));
        List<CorrelationLabelBean> correlationLabelBeans = commonService.getCorrelationLabel(correlationIds,correlationTableName);
        List<EnterpriseDto> enterpriseDtos = fileAndLabelTidyingUtil.getList(fileBeans,correlationLabelBeans,enterpriseBeans,EnterpriseBean.class,EnterpriseDto.class);
        return enterpriseDtos;
    }


    /**
     * 向数据库添加图片及标签信息
     * @param fileSaveDtos
     * @return
     */
    @Validated
    @Transactional
    public Boolean addImageMessageAndLabel(List<FileSaveDto> fileSaveDtos,List<Long> labelIds,Long enterpriseId){
        boolean result = true;
        if(fileSaveDtos != null && fileSaveDtos.size() != 0){
            for(FileSaveDto fileCorrelationSaveDto:fileSaveDtos){
                fileCorrelationSaveDto.setCorrelationId(enterpriseId);
                fileCorrelationSaveDto.setCorrelationTableName(correlationTableName);
            }
            result = commonService.addFileMessage(fileSaveDtos);
        }
        System.out.println(labelIds.size());
        if (labelIds != null && labelIds.size() != 0) {
            result = commonService.addLabelCorrelation(labelIds,enterpriseId,correlationTableName);
        }
        return result;
    }

    /**
     * 删除图片及标签信息
     * @param fileDeleteDto
     * @return
     */
    @Override
    @Transactional
    public Boolean deleteImageMessageAndLabel(FileDeleteDto fileDeleteDto, List<Long> labelIds){
        boolean result = true;
        if(fileDeleteDto.getFileIds() != null && fileDeleteDto.getFileIds().size() !=0)
            result = commonService.deleteFileMessage(fileDeleteDto);
        if(labelIds != null && labelIds.size() != 0)
            result = commonService.deleteLabelCorrelation(labelIds);
        return result;
    }


    /**
     * 更新图片及标签
     * @param fileUpdateDtos
     * @return
     */
    @Override
    @Transactional
    public Boolean updateImageMessage(List<FileUpdateDto> fileUpdateDtos,List<Long> labelIds,Long enterpriseId){
        if((fileUpdateDtos == null || fileUpdateDtos.size() == 0) && (labelIds == null || labelIds.size() == 0))
            return true;
        List<FileBean> fileBeans = commonService.getFileList(new FileSelectDto(correlationTableName , new ArrayList<>(Collections.singleton(enterpriseId))));
        for(int i = 0; i < fileBeans.size(); i++){
            for(int j = 0; j < fileUpdateDtos.size(); j++){
                if(fileUpdateDtos.get(j).getId() == fileBeans.get(i).getId()) {
                    fileBeans.remove(i);
                    fileUpdateDtos.remove(j);
                }
            }
        }
        List<CorrelationLabelBean> correlationLabelBeans = commonService.getCorrelationLabel(new ArrayList<>(Collections.singleton(enterpriseId)),correlationTableName);
        if(correlationLabelBeans != null && correlationLabelBeans.size() != 0){
            for(int i = 0 ; i < correlationLabelBeans.size() ; i ++){
                for(int j = 0 ; j < labelIds.size() ; j++){
                    if(correlationLabelBeans.get(i).getId() == labelIds.get(j)){
                        correlationLabelBeans.remove(i);
                        labelIds.remove(j);
                    }
                }
            }
        }
        //删除的图片ID集合
        List<Long> deleteFileIds = new ArrayList<>();
        //删除的图片名称集合
        List<String> deleteFilenames = new ArrayList<>();
        //删除的标签ID集合
        List<Long> deleteLabelIds = new ArrayList<>();
        if(fileBeans != null && fileBeans.size() != 0){
            for(FileBean fileBean:fileBeans){
                deleteFileIds.add(fileBean.getId());
                deleteFilenames.add(fileBean.getName());
            }
        }
        if (correlationLabelBeans != null && correlationLabelBeans.size() != 0){
            for(CorrelationLabelBean correlationLabelBean:correlationLabelBeans){
                deleteLabelIds.add(correlationLabelBean.getCid());
            }
        }
        //删除图片及标签
        boolean is = deleteImageMessageAndLabel(new FileDeleteDto(deleteFileIds,deleteFilenames),deleteLabelIds);
        if(!is)
            return false;
        //添加图片及标签
        is = addImageMessageAndLabel(BeanMapper.mapList(fileUpdateDtos,FileUpdateDto.class,FileSaveDto.class),labelIds,enterpriseId);
        if(!is)
            return false;
        return true;
    }




}
