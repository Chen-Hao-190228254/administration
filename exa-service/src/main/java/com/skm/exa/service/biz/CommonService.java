package com.skm.exa.service.biz;

import com.skm.exa.domain.bean.*;
import com.skm.exa.persistence.dto.FileDeleteDto;
import com.skm.exa.persistence.dto.FileSaveDto;
import com.skm.exa.persistence.dto.FileSelectDto;
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
     * 获得标签
     * @return
     */
    List<LabelBean> getLabel(List<Long> labelIds);


    /**
     * 添加标签
     * @param name
     * @return
     */
    LabelBean addLabel(String name);


    /**
     * 通过关联的ID和关联的表名获得标签
     * @param correlationIds
     * @param correlationTableName
     * @return
     */
    List<CorrelationLabelBean> getCorrelationLabel(List<Long> correlationIds, String correlationTableName);


    /**
     * 添加标签关联信息
     * @param labelIds
     * @param correlationId
     * @param correlationTableName
     * @return
     */
    Boolean addLabelCorrelation(List<Long> labelIds,Long correlationId, String correlationTableName);

    /**
     * 删除标签关联信息
     * @param labelIds
     * @return
     */
    Boolean deleteLabelCorrelation(List<Long> labelIds);


    /**
     * 获取文件
     * @param fileSelectDto 选填
     * @return
     */
    List<FileBean> getFileList(FileSelectDto fileSelectDto);


    /**
     * 上传文件及往数据库添加上传后的文件详细
     * @param files 需要上传的文件
     * @return
     */
    List<FileBean> uploadFile(MultipartFile[] files);


    /**
     * 上传文件及往数据库添加上传后的文件详细
     * @param file 需要上传的文件
     * @return
     */
    FileBean uploadFile(MultipartFile file);





    /**
     * 往数据库添加文件关联
     * @param fileSaveDtos 关联的信息
     * @return
     */
    Boolean addFileMessage(List<FileSaveDto> fileSaveDtos);




    /**
     * 删除文件 (适用于删除整条数据关联的文件)
     * @param fileDeleteDto
     * @return
     */
    Boolean deleteFileMessage(FileDeleteDto fileDeleteDto);


}
