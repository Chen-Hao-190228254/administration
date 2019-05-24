package com.skm.exa.webapi.vo;

import com.skm.exa.domain.bean.FileBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@ApiModel("企业返回模型")
@Data
public class EnterpriseVo implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value = "企业ID")
    private Long id;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    private String name;

    /**
     * 社会统一代码
     */
    @ApiModelProperty(value = "社会统一代码")
    private String code;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String location;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private Long phone;

    /**
     * 官方网址
     */
    @ApiModelProperty(value = "官方网址")
    private String url;

    /**
     * 技术要点
     */
    @ApiModelProperty(value = "技术要点")
    private String technique;

    /**
     * 所在地区
     */
    @ApiModelProperty(value = "所在地区")
    private String areaId;

    /**
     * 经营范围
     */
    @ApiModelProperty(value = "经营范围")
    private String businessScope;

    /**
     * 详细描述
     */
    @ApiModelProperty(value = "详细描述")
    private String detail;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private byte status;

    /**
     * 公司logo
     */
    @ApiModelProperty(value = "公司logo")
    private List<FileBean> imageBeans;

    /**
     * 添加人ID
     */
    @ApiModelProperty(value = "添加人ID")
    private Long entryId;

    /**
     * 添加人名称
     */
    @ApiModelProperty(value = "添加人名称")
    private String entryName;

    /**
     * 添加时间
     */
    @ApiModelProperty(value = "添加时间")
    private Date entryDt;

    /**
     * 更新人ID
     */
    @ApiModelProperty(value = "更新人ID")
    private Long updateId;

    /**
     * 更新人名称
     */
    @ApiModelProperty(value = "更新人名称")
    private String updateName;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateDt;

}
