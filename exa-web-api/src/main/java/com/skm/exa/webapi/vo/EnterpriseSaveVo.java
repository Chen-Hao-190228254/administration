package com.skm.exa.webapi.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Data
@ApiModel("添加企业模型")
public class EnterpriseSaveVo {


    /**
     * 公司名称
     */
    @ApiModelProperty("公司名称")
    private String name;

    /**
     * 社会统一代码
     */
    @ApiModelProperty("社会统一代码")
    private String code;

    /**
     * 地址
     */
    @ApiModelProperty("地址")
    private String location;

    /**
     * 联系电话
     */
    @ApiModelProperty("联系电话")
    private Long phone;

    /**
     * 官方网址
     */
    @ApiModelProperty("官方网址")
    private String url;

    /**
     * 技术要点
     */
    @ApiModelProperty("技术要点")
    private String technique;

    /**
     * 所在地区
     */
    @ApiModelProperty("所在地区")
    private String areaId;

    /**
     * 经营范围
     */
    @ApiModelProperty("经营范围")
    private String businessScope;

    /**
     * 详细描述
     */
    @ApiModelProperty("详细描述")
    private String detail;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private Byte status;


    /**
     * 图片
     */
    @ApiModelProperty("图片")
    private MultipartFile image;


}
