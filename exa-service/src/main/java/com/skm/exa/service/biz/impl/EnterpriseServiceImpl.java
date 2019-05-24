package com.skm.exa.service.biz.impl;

import com.skm.exa.common.enums.StatusEnum;
import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.utils.BeanMapper;
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

import java.util.*;

@Service
public class EnterpriseServiceImpl extends BaseServiceImpl<EnterpriseBean, EnterpriseDao> implements EnterpriseService {



    String correlationTableName = "administration_enterprise"; //关联表名

    /**
     * 获取所有企业信息
     * @return
     */
    @Override
    public List<EnterpriseDto> getEnterpriseList() {
        List<EnterpriseBean> enterpriseBeans = dao.getEnterpriseList();
        List<EnterpriseDto> enterpriseDtos = getEnterpriseImage(enterpriseBeans);
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
        if(enterpriseBean == null)
            return null;
        return getEnterpriseImage(enterpriseBean);
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
        List<EnterpriseDto> enterpriseDtos = getEnterpriseImage(enterpriseBeans);
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
    public Result<EnterpriseDto> addEnterprise(EnterpriseSaveDto enterpriseSaveDto, UnifyAdmin unifyAdmin) {
        EnterpriseBean enterpriseBean = BeanMapper.map(enterpriseSaveDto,EnterpriseBean.class);
        if(enterpriseBean == null)
            return Result.error(-1,"数据有误");
        enterpriseBean.setEntryId(unifyAdmin.getId());
        enterpriseBean.setEntryName(unifyAdmin.getName());
        enterpriseBean.setEntryDt(new Date());
        enterpriseBean.setUpdateId(unifyAdmin.getId());
        enterpriseBean.setUpdateName(unifyAdmin.getName());
        enterpriseBean.setUpdateDt(new Date());
        int is = dao.addEnterprise(enterpriseBean);
        if(is<=0)
            return Result.error(-1,"添加企业时失败");
        if(enterpriseBean.getId() == null)
            return Result.error(-1,"企业添加之后返回的ID有误");
        if(enterpriseSaveDto.getFileCorrelationSaveDtos() == null || enterpriseSaveDto.getFileCorrelationSaveDtos().size() == 0)
            return Result.success(BeanMapper.map(enterpriseBean,EnterpriseDto.class));
        Result<List<FileBean>> resultImages = addImage(enterpriseSaveDto.getFileCorrelationSaveDtos(),enterpriseBean.getId());
        List<FileBean> fileBeans = resultImages.getContent();
        EnterpriseDto enterpriseDto = BeanMapper.map(enterpriseBean,EnterpriseDto.class);
        enterpriseDto.setImageBeans(fileBeans);
        return Result.success(enterpriseDto);
    }

    /**
     * 更新企业
     * @return
     */
    @Override
    public Result<EnterpriseDto> updateEnterprise(EnterpriseUpdateDto enterpriseUpdateDto, UnifyAdmin unifyAdmin) {
        if(enterpriseUpdateDto == null)
            return null;
        EnterpriseBean enterpriseBean = BeanMapper.map(enterpriseUpdateDto,EnterpriseBean.class);
        enterpriseBean.setUpdateId(unifyAdmin.getId());
        enterpriseBean.setUpdateName(unifyAdmin.getName());
        enterpriseBean.setUpdateDt(new Date());
        int is = dao.updateEnterprise(enterpriseBean);
        if(is<=0)
            return Result.error(-1,"更新企业的时候发生错误");
        List<FileCorrelationSaveDto> fileCorrelationSaveDtos = enterpriseUpdateDto.getFileCorrelationSaveDtos();
        if(fileCorrelationSaveDtos == null || fileCorrelationSaveDtos.size() == 0)
            return Result.success(getEnterprise(enterpriseUpdateDto.getId()));
        Result<List<FileBean>> fileBean = updateImage(fileCorrelationSaveDtos,enterpriseUpdateDto.getId());
        Result<EnterpriseDto> result = BeanMapper.map(fileBean,Result.class);
        result.setContent(getEnterprise(enterpriseUpdateDto.getId()));
        return result;
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
    public EnterpriseDto setEnterpriseStatus(Long id) {
        EnterpriseBean enterpriseBean = dao.getEnterprise(id);
        if(enterpriseBean == null)
            return null;
        if(enterpriseBean.getStatus() == StatusEnum.NORMAL.getIndex())
            dao.setEnterpriseStatus(id,StatusEnum.FORBIDDEN.getIndex());
        if(enterpriseBean.getStatus() == StatusEnum.FORBIDDEN.getIndex())
            dao.setEnterpriseStatus(id,StatusEnum.NORMAL.getIndex());
        return getEnterprise(id);
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
     public EnterpriseDto getEnterpriseImage(EnterpriseBean enterpriseBean){
        if(enterpriseBean == null)
            return null;
        List<Long> enterpriseIds = new ArrayList<>();
         enterpriseIds.add(enterpriseBean.getId());
        List<FileBean> fileBeans = commonService.getFileList(enterpriseIds,"administration_enterprise");
        EnterpriseDto enterpriseDto = BeanMapper.map(enterpriseBean,EnterpriseDto.class);
        enterpriseDto.setImageBeans(fileBeans);
        return enterpriseDto;
     }


    /**
     * 获取图片
     * @param enterpriseBeans
     * @return
     */
    @Override
    public List<EnterpriseDto> getEnterpriseImage(List<EnterpriseBean> enterpriseBeans){
        if(enterpriseBeans == null || enterpriseBeans.size() == 0)
            return null;
        List<Long> enterpriseIds = new ArrayList<>();
        Map<Long,EnterpriseDto> mapEnterprise = new HashMap<>();
        for(EnterpriseBean enterpriseBean:enterpriseBeans){
            enterpriseIds.add(enterpriseBean.getId());
            mapEnterprise.put(enterpriseBean.getId(), BeanMapper.map(enterpriseBean,EnterpriseDto.class));
        }
        List<FileBean> fileBeans = commonService.getFileList(enterpriseIds,"administration_enterprise");
        if(fileBeans == null || fileBeans.size() == 0)
            return BeanMapper.mapList(enterpriseBeans,EnterpriseBean.class,EnterpriseDto.class);
        Map<Long,List<FileBean>> mapFile = new HashMap<>();
        for(FileBean fileBean:fileBeans){
            if(mapFile.containsKey(fileBean.getCorrelationId())){
                mapFile.get(fileBean.getCorrelationId()).add(fileBean);
            }else {
                List<FileBean> imageBeanList = new ArrayList<>();
                imageBeanList.add(fileBean);
                mapFile.put(fileBean.getCorrelationId(),imageBeanList);
            }
        }
        for(Long key:mapEnterprise.keySet()){
            if(mapFile.containsKey(key)){
                mapEnterprise.get(key).setImageBeans(mapFile.get(key));
            }
        }
        List<EnterpriseDto> enterpriseDtos = new ArrayList<>();
        for(Long key:mapEnterprise.keySet()){
            enterpriseDtos.add(mapEnterprise.get(key));
        }
        return enterpriseDtos;
    }


    /**
     * 向数据库添加图片关联
     * @param fileCorrelationSaveDtos
     * @return
     */
    @Transactional
    public Result<List<FileBean>> addImage(List<FileCorrelationSaveDto> fileCorrelationSaveDtos,Long enterpriseId){
        for(FileCorrelationSaveDto fileCorrelationSaveDto:fileCorrelationSaveDtos){
            fileCorrelationSaveDto.setCorrelationId(enterpriseId);
            fileCorrelationSaveDto.setCorrelationTableName(correlationTableName);
        }
        Result result = commonService.addFileCorrelation(fileCorrelationSaveDtos);
        List<Long> enterpriseIds = new ArrayList<>();
        for(FileCorrelationSaveDto fileCorrelationSaveDto:fileCorrelationSaveDtos){
            enterpriseIds.add(fileCorrelationSaveDto.getCorrelationId());
        }
        List<FileBean> fileBeans = commonService.getFileList(enterpriseIds,correlationTableName);
        return result.setContent(fileBeans);
    }




    /**
     * 删除图片及关联
     * @param list
     * @return
     */
    @Override
    public boolean deleteImage(List<Long> list){
        return commonService.deleteFile(list,correlationTableName);
    }


    /**
     * 更新图片
     * @param fileCorrelationSaveDtos
     * @param enterpriseId
     * @return
     */
    @Override
    public Result<List<FileBean>> updateImage(List<FileCorrelationSaveDto> fileCorrelationSaveDtos,Long enterpriseId){
        List<FileBean> fileBeans = getEnterprise(enterpriseId).getImageBeans();
        Map<Long,FileBean> mapFileBean = new HashMap<>();
        for (FileBean fileBean:fileBeans){
            mapFileBean.put(fileBean.getId(),fileBean);
        }
        if(fileBeans != null ||fileBeans.size() != 0){
            for(FileCorrelationSaveDto fileCorrelationSaveDto:fileCorrelationSaveDtos){
                for (Long f:fileCorrelationSaveDto.getFileId()){
                    for(Long key:mapFileBean.keySet()){
                        if(mapFileBean.containsKey(f))
                            mapFileBean.remove(f);
                    }
                }
            }
        }
        List<FileBean> deleteFileBeans = new ArrayList<>();
        for(Long key:mapFileBean.keySet()){
            deleteFileBeans.add(mapFileBean.get(key));
        }
        if(deleteFileBeans != null && deleteFileBeans.size() != 0){
            boolean delete = commonService.deleteFile(deleteFileBeans);
            if(!delete)
                return Result.error(-1,"删除现有图片时出错");
        }

        Result<List<FileBean>> resultImages = addImage(fileCorrelationSaveDtos,enterpriseId);
        return resultImages;
    }

}
