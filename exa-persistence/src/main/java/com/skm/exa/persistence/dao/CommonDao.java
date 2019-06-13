package com.skm.exa.persistence.dao;

import com.skm.exa.domain.bean.AreaBean;
import com.skm.exa.domain.bean.CorrelationLabelBean;
import com.skm.exa.domain.bean.FileBean;
import com.skm.exa.domain.bean.LabelBean;
import com.skm.exa.persistence.dto.FileSaveDto;
import com.skm.exa.persistence.dto.FileSelectDto;
import org.apache.ibatis.annotations.Param;
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
     * @param fileSelectDto  选填
     * @return
     */
    List<FileBean> getFileListMessage(FileSelectDto fileSelectDto);


    /**
     * 获取标签
     * @param labelIds
     * @return
     */
    List<LabelBean> getLabel(List<Long> labelIds);


    /**
     * 添加标签
     * @param labelBean
     * @return
     */
    int addLabel(LabelBean labelBean);


    /**
     * 通过关联ID和关联表名获得标签
     * @param correlationIds
     * @param correlationTableName
     * @return
     */
    List<CorrelationLabelBean> getCorrelationLabel(@Param("correlationIds") List<Long> correlationIds, @Param("correlationTableName") String correlationTableName);

    /**
     * 添加标签关联
     * @param labelIds
     * @param correlationId
     * @param correlationTableName
     * @return
     */
    int addLabelCorrelation(@Param("labelIds") List<Long> labelIds,@Param("correlationId")Long correlationId, @Param("correlationTableName")String correlationTableName);

    /**
     * 删除标签关联信息
     * @param labelIds
     * @return
     */
    int deleteLabelCorrelation(@Param("labelIds") List<Long> labelIds);





    /**
     * 添加文件关联
     * @param fileSaveDtos
     * @return
     */
    int addFileMessage(@Param("fileSaveDtos") List<FileSaveDto> fileSaveDtos);


    /**
     * 删除文件
     * @param fileIds 文件ID集合
     * @return
     */
    int deleteFileMessage(@Param("fileIds")List<Long> fileIds);


}
