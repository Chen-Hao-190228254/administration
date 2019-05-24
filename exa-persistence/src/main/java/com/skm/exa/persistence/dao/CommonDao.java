package com.skm.exa.persistence.dao;

import com.skm.exa.domain.bean.AreaBean;
import com.skm.exa.domain.bean.FileBean;
import com.skm.exa.persistence.dto.FileCorrelationSaveDto;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommonDao{

    /**
     * 根据父code，获得下一级的所有地址
     * @param code
     * @return
     */
    List<AreaBean> getAreaParentCode(Long code);


    /**
     * 获取文件
     * @param correlationIds 关联的ID
     * @param correlationTableName 关联的表名
     * @return
     */
    List<FileBean> getFileList(List<Long> correlationIds,String correlationTableName);



    /**
     * 文件上传后向数据库添加上传文件的信息
     * @param fileBeans
     * @return
     */
    int uploadFiles(@Param("list")List<FileBean> fileBeans);



    /**
     * 文件上传后向数据库添加上传文件的信息
     * @param fileBean
     * @return
     */
    int uploadFile(FileBean fileBean);








    /**
     * 添加文件关联
     * @param list
     * @return
     */
    int addFileCorrelation(FileCorrelationSaveDto list);


    /**
     * 删除指定ID文件
     * @param fileId 文件ID
     * @return
     */
    int deleteFile(@Param("list") List<Long> fileId);


}
