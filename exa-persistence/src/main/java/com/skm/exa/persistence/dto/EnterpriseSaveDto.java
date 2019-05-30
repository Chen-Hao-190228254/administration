package com.skm.exa.persistence.dto;

import com.skm.exa.domain.bean.FileBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Data
public class EnterpriseSaveDto {

    /**
     * 公司名称
     */
    private String name;

    /**
     * 社会统一代码
     */
    private String code;

    /**
     * 地址
     */
    private String location;

    /**
     * 联系电话
     */
    private Long phone;

    /**
     * 官方网址
     */
    private String url;

    /**
     * 技术要点
     */
    private String technique;

    /**
     * 所在地区
     */
    private String areaId;

    /**
     * 经营范围
     */
    private String businessScope;

    /**
     * 详细描述
     */
    private String detail;

    /**
     * 状态
     */
    private Byte status;


    /**
     * 图片
     */
    private List<FileSaveDto> fileSaveDtos;

}
