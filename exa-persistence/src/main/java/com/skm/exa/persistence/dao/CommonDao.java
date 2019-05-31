package com.skm.exa.persistence.dao;

import com.skm.exa.domain.bean.AreaBean;
import com.skm.exa.domain.bean.FileBean;
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
