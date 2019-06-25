package com.skm.exa.service.biz.impl;

import com.skm.exa.common.enums.StatusEnum;
import com.skm.exa.common.utils.AliyunOSSClientUtil;
import com.skm.exa.domain.bean.*;
import com.skm.exa.persistence.dao.CommonDao;
import com.skm.exa.persistence.dto.*;
import com.skm.exa.service.biz.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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
            StatusBean statusBean = new StatusBean(s.getIndex(),s.getName());
            statusBeans.add(statusBean);
        }
        return statusBeans;
    }




//<-----------------------------------地址操作------------------------------------------------------------>

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
     * 通过code获取当前code的信息
     * @param code
     * @return
     */
    @Override
    public List<AreaBean> getAreaCode(Long code){
        return commonDao.getAreaCode(code);
    }

    /**
     * 根据code获取上一级地址
     * @param code
     * @return
     */
    @Override
    public AreaBean getByCodeParent(Long code) {
        return commonDao.getByCodeParent(code);
    }




//<-----------------------------------标签操作------------------------------------------------------------>

    /**
     * 获得标签
     * @return
     */
    @Override
    public List<LabelBean> getLabel(List<Long> labelIds) {
        return commonDao.getLabel(labelIds);
    }

    /**
     * 添加标签
     * @param name
     * @return
     */
    @Override
    public LabelBean addLabel(String name) {
        LabelBean labelBean = new LabelBean(name);
        commonDao.addLabel(labelBean);
        return labelBean;
    }

    /**
     * 通过关联的ID和关联的表名获得标签
     * @param correlationIds
     * @param correlationTableName
     * @return
     */
    @Override
    public List<CorrelationLabelBean> getCorrelationLabel(List<Long> correlationIds,String correlationTableName){
        return commonDao.getCorrelationLabel(correlationIds,correlationTableName);
    }

    /**
     * 添加标签关联
     * @param labelIds
     * @param correlationId
     * @param correlationTableName
     * @return
     */
    @Override
    @Transactional
    public Boolean addLabelCorrelation(List<Long> labelIds,Long correlationId, String correlationTableName){
        return commonDao.addLabelCorrelation(labelIds,correlationId,correlationTableName) == labelIds.size();
    }

    /**
     * 删除标签关联信息
     * @param labelIds
     * @return
     */
    @Override
    @Transactional
    public Boolean deleteLabelCorrelation(List<Long> labelIds){
        return commonDao.deleteLabelCorrelation(labelIds) == labelIds.size();
    }



//<-----------------------------------文件操作------------------------------------------------------------>

    /**
     * 获得文件
     * @param fileSelectDto 选填
     * @return
     */
    @Override
    public List<FileBean> getFileList(FileSelectDto fileSelectDto) {
        return commonDao.getFileListMessage(fileSelectDto);
    }


    /**
     * 获取所有文件信息
     * @return
     */
    @Override
    public List<FileBean> getAllFileMessage() {
        return commonDao.getAllFileMessage();
    }


    /**
     * 上传文件及往数据库添加上传后的文件详细
     * @param files
     * @return
     */
    @Override
    public List<FileBean> uploadFile(MultipartFile[] files) {
        if(files == null || files.length == 0)
            return null;
        List<Map<String,String>> maps = new ArrayList<>();
        for(MultipartFile file:files){
            if(file.isEmpty())
                return null;
            //往AliyunOSS上传文件、逐一上传
            Map<String,String> map = AliyunOSSClientUtil.uploadObject2OSS(file);
            maps.add(map);
        }
        List<FileBean> fileBeans = new ArrayList<>();
        for(Map<String,String> map:maps){
            FileBean fileBean = new FileBean(map.get("name"),map.get("url"),Double.parseDouble(map.get("filesize")),map.get("md5key"));
            fileBeans.add(fileBean);
        }
        if(fileBeans == null || fileBeans.size() == 0)
            return null;
        return fileBeans;
    }


    /**
     * 上传文件返回上传信息
     * @param file
     * @return
     */
    @Override
    public FileBean uploadFile(MultipartFile file) {
        Map<String,String> map = AliyunOSSClientUtil.uploadObject2OSS(file);
        if(map == null || map.size() == 0)
            return null;
        FileBean fileBean = new FileBean(map.get("name"),map.get("url"),Double.parseDouble(map.get("filesize")),map.get("md5key"));
        return fileBean;
    }


    /**
     * 向数据库添加文件信息
     * @param fileSaveDtos
     * @return
     */
    @Override
    @Transactional
    public Boolean addFileMessage(List<FileSaveDto> fileSaveDtos){
        if(fileSaveDtos == null  ||  fileSaveDtos.size() == 0)
            return true;
        int i = commonDao.addFileMessage(fileSaveDtos);
        if(i != fileSaveDtos.size())
            return false;
        return true;
    }



    /**
     * 删除AliyunOSS上的文件及数据库文件关联
     * @param fileDeleteDto 要删除的文件的集合
     * @return
     */
    @Override
    @Transactional
    public Boolean deleteFileMessage(FileDeleteDto fileDeleteDto) {
        int i = commonDao.deleteFileMessage(fileDeleteDto.getFileIds());
        if(i != fileDeleteDto.getFileIds().size())
            return false;

        //删除OSS上的图片
        if(fileDeleteDto.getFilenames() != null && fileDeleteDto.getFilenames().size() != 0){
            for(String filename:fileDeleteDto.getFilenames()){
                AliyunOSSClientUtil.deleteFile(filename);
            }
        }
        return true;
    }






//    /**
//     * 删除AliyunOSS上的文件及数据库文件关联
//     * @param fileBeans 要删除的文件Beans
//     * @return
//     */
//    @Override
//    @Transactional
//    public Boolean deleteFile(List<FileBean> fileBeans) {
//        List<Long> fileIds = new ArrayList<>();
//        if (fileBeans == null && fileBeans.size() == 0)
//            return false;
//        if(fileBeans != null && fileBeans.size() != 0)
//            for(FileBean fileBean:fileBeans){
//                fileIds.add(fileBean.getId());
//            }
//        int is = commonDao.deleteFile(fileIds);
//        if(is<=0)
//            return false;
//        //删除OSS上的图片
//        if(fileBeans != null && fileBeans.size() != 0)
//            deleteAliyunOSSFile(fileBeans);
//        return true;
//    }


//    /**
//     * 删除阿里云OSS上的文件
//     * @param filenames 文件名称
//     */
//    public void deleteAliyunOSSFile(List<String> filenames){
//        for(String filename:filenames){
//            System.out.println("删除的图片信息："+filename.toString());
//
//        }
//    }
}
