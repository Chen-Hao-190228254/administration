package com.skm.exa.webapi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("文件更新模板")
public class FileUpdateVo {


    /**
     * 文件ID，用于判断文件是否为新增
     */
    @ApiModelProperty("文件ID，如果没有ID为新增文件")
    private Long id;


    /**
     * 文件名称
     */
    @ApiModelProperty("文件名称")
    private String name;

    /**
     * 文件地址
     */
    @ApiModelProperty("文件地址")
    private String url;

    /**
     * 文件大小
     */
    @ApiModelProperty("文件大小")
    private Double size;

    /**
     * 文件MD5数字唯一签名
     */
    @ApiModelProperty("文件MD5数字唯一签名")
    private String md5;

    /**
     * 关联表名称
     */
    @ApiModelProperty("关联表名称")
    private String correlationTableName;

    /**
     * 关联的ID
     */
    @ApiModelProperty("关联的ID")
    private Long correlationId;

    /**
     * 适用于的地方，如身份证正面，身份证反面，LOGO
     */
    @ApiModelProperty("适用于的地方，如身份证正面，身份证反面，LOGO")
    private String applyTo;


}
