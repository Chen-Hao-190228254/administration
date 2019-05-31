package com.skm.exa.webapi.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("输入条件搜索")
public class QuestionQueryVO  {
    @ApiModelProperty(value = "技术类型")
    private Long technologicalType;
    @ApiModelProperty(value = "题目类型")
    private Long topicType;
    @ApiModelProperty(value = "企业名称")
    private String enterpriseName;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "开始时间")
    private Date startDt;
    @ApiModelProperty(value = "结束时间")
    private Date endDt;
}
