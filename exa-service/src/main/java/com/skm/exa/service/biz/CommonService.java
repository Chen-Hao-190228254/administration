package com.skm.exa.service.biz;

import com.skm.exa.common.object.Result;
import com.skm.exa.domain.bean.AreaBean;
import com.skm.exa.domain.bean.FileBean;
import com.skm.exa.domain.bean.StatusBean;
import com.skm.exa.persistence.dto.FileCorrelationSaveDto;
import org.springframework.web.multipart.MultipartFile;

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
     * 获取文件
     * @param correlationId 关联的ID
     * @param correlationTableName 关联的表名
     * @return
     */
    List<FileBean> getFileList(List<Long> correlationId, String correlationTableName);


    /**
     * 上传文件及往数据库添加上传后的文件详细
     * @param files 需要上传的文件
     * @return
     */
    Result<List<Long>> uploadFile(MultipartFile[] files);


    /**
     * 上传文件及往数据库添加上传后的文件详细
     * @param file 需要上传的文件
     * @return
     */
    Result<FileBean> uploadFile(MultipartFile file);





    /**
     * 往数据库添加文件关联
     * @param fileCorrelationSaveDtos 关联的信息
     * @return
     */
    Result addFileCorrelation(List<FileCorrelationSaveDto> fileCorrelationSaveDtos);




    /**
     * 删除文件 (适用于删除整条数据关联的文件)
     * @param correlationId
     * @param correlationTableName
     * @return
     */
    Boolean deleteFile(List<Long> correlationId, String correlationTableName);


    /**
     * 删除文件 （适用于已知文件信息）
     * @param fileBeans
     * @return
     */
    Boolean deleteFile(List<FileBean> fileBeans);


}
