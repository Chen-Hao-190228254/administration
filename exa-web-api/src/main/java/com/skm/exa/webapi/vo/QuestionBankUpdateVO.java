package com.skm.exa.webapi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Data
@ApiModel(value = "问题管理修改VO")
public class QuestionBankUpdateVO {
    @ApiModelProperty(value = "id")
    private Long id ;
    @ApiModelProperty(value = "企业名称")
    private Long enterpriseId;
    @ApiModelProperty(value = "企业名称")
    private String enterpriseName;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "技术类型 1.编程 2.算法 3.数据库 4.优化")
    @Range(min =  1, max = 4,message = "技术类型有误")
    private Long technologicalType;
    @ApiModelProperty(value = "题目类型 1.问答 2.单选 3.多选 4.编程")
    @Range(min =  1, max = 4 ,message = "题目类型有误")
    private Long topicType;
    @ApiModelProperty(value = "题目详情")
    private String topicDetails;
    @ApiModelProperty(value = "标签")
    private String label;
    @ApiModelProperty(value = "问题解答")
    private String answer;

    private List<OptionCodesUpdateVo> optionCodesUpdateVoList ;
}
