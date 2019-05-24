package com.skm.exa.service.biz.impl;

import com.skm.exa.common.enums.StatusEnum;
import com.skm.exa.common.utils.AliyunOSSClientUtil;
import com.skm.exa.common.object.Result;
import com.skm.exa.domain.bean.AreaBean;
import com.skm.exa.domain.bean.FileBean;
import com.skm.exa.domain.bean.StatusBean;

import com.skm.exa.persistence.dao.CommonDao;
import com.skm.exa.persistence.dto.FileCorrelationSaveDto;
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
     * 获得文件
     * @param correlationId 关联的ID
     * @param correlationTableName 关联的表名
     * @return
     */
    @Override
    public List<FileBean> getFileList(List<Long> correlationId,String correlationTableName) {
        return commonDao.getFileList(correlationId,correlationTableName);
    }


    /**
     * 上传文件及往数据库添加上传后的文件详细
     * @param files
     * @return
     */
    @Override
    public Result<List<Long>> uploadFile(MultipartFile[] files) {
        List<Map<String,String>> maps = new ArrayList<>();
        if(files.length == 0 || files == null)
            return Result.error(-1,"文件为空");
        for(MultipartFile file:files){
            if(file.isEmpty())
                return Result.error(-1,"文件长传失败");
            //往AliyunOSS上传文件、逐一上传
            Map<String,String> map = AliyunOSSClientUtil.uploadObject2OSS(file);
            maps.add(map);
        }
        List<FileBean> fileBeans = new ArrayList<>();
        for(Map<String,String> map:maps){
            FileBean fileBean = new FileBean();
            fileBean.setName(map.get("name"));
            fileBean.setUrl(map.get("url"));
            fileBean.setMd5(map.get("md5key"));
            fileBean.setSize(Double.parseDouble(map.get("filesize")));
            fileBeans.add(fileBean);
        }
        if(fileBeans.size() == 0 || fileBeans == null)
            return Result.error(-1,"向AliyunOSS添加文件时出错");

        //往数据库添加上传后的文件详细
        int i = commonDao.uploadFiles(fileBeans);
        if(i!=fileBeans.size())
            return Result.error(-1,"向数据库添加图片信息时发生错误");
        List<Long> imageIds = new ArrayList<>();
        for(FileBean imageBean:fileBeans){
            imageIds.add(imageBean.getId());
        }
        return Result.success(imageIds);
    }


    /**
     * 上传文件及往数据库添加上传后的文件详细
     * @param file
     * @return
     */
    @Override
    public Result<FileBean> uploadFile(MultipartFile file) {
        Map<String,String> map = AliyunOSSClientUtil.uploadObject2OSS(file);
        if(map == null || map.size() == 0)
            return Result.error(-1,"向AliyunOSS添加文件时出错");
        FileBean fileBean = new FileBean();
        fileBean.setName(map.get("name"));
        fileBean.setUrl(map.get("url"));
        fileBean.setMd5(map.get("md5key"));
        fileBean.setSize(Double.parseDouble(map.get("filesize")));
        int i = commonDao.uploadFile(fileBean);
        if(i<=0)
            return Result.error(-1,"向数据库添加图片信息时发生错误");
        return Result.success(fileBean);
    }


    /**
     * 向数据库添加文件关联
     * @param fileCorrelationSaveDtos
     * @return
     */
    @Override
    @Transactional
    public Result addFileCorrelation(List<FileCorrelationSaveDto> fileCorrelationSaveDtos){
        if(fileCorrelationSaveDtos.size() == 0 || fileCorrelationSaveDtos == null)
            return Result.success("没有文件信息");
        for(FileCorrelationSaveDto fileCorrelationSaveDto:fileCorrelationSaveDtos){
            if(fileCorrelationSaveDto.getFileId() == null || fileCorrelationSaveDto.getFileId().size() == 0)
                return Result.error(-1,"添加文件关联的时候，文件ID为空");
            int i = commonDao.addFileCorrelation(fileCorrelationSaveDto);
            if(i != fileCorrelationSaveDto.getFileId().size())
                return Result.error(-1,"向数据库添加文件关联时发生错误");
        }
        return Result.success("向数据库添加文件关联成功");
    }



    /**
     * 删除AliyunOSS上的文件及数据库文件关联
     * @param correlationId 要删除的文件的关联ID集合
     * @param correlationTableName 要删除的关联表名称
     * @return
     */
    @Override
    @Transactional
    public Boolean deleteFile(List<Long> correlationId, String correlationTableName) {
        List<FileBean> fileBeans = getFileList(correlationId,correlationTableName);
        if(fileBeans == null || fileBeans.size() == 0)
            return true;
        List<Long> fileId = new ArrayList<>();
        for(FileBean fileBean:fileBeans){
            fileId.add(fileBean.getId());
        }
        int i = commonDao.deleteFile(fileId);
        if(i != fileId.size())
            return false;
        //删除OSS上的图片
        if(fileBeans != null && fileBeans.size() != 0)
            deleteAliyunOSSFile(fileBeans);
        return true;
    }

    /**
     * 删除AliyunOSS上的文件及数据库文件关联
     * @param fileBeans 要删除的文件Beans
     * @return
     */
    @Override
    @Transactional
    public Boolean deleteFile(List<FileBean> fileBeans) {
        List<Long> fileIds = new ArrayList<>();
        if (fileBeans == null && fileBeans.size() == 0)
            return false;
        if(fileBeans != null && fileBeans.size() != 0)
            for(FileBean fileBean:fileBeans){
                fileIds.add(fileBean.getId());
            }
        int is = commonDao.deleteFile(fileIds);
        if(is<=0)
            return false;
        //删除OSS上的图片
        if(fileBeans != null && fileBeans.size() != 0)
            deleteAliyunOSSFile(fileBeans);
        return true;
    }


    /**
     * 删除阿里云OSS上的文件
     * @param fileBeans 文件信息
     */
    public void deleteAliyunOSSFile(List<FileBean> fileBeans){
        for(FileBean fileBean:fileBeans){
            System.out.println("删除的图片信息："+fileBean.toString());
            AliyunOSSClientUtil.deleteFile(fileBean.getName());
        }
    }
}
