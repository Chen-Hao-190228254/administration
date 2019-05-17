package com.skm.exa.persistence.qo;

import com.skm.exa.domain.bean.QuestionBankBean;
import com.skm.exa.mybatis.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionBankLikeQO extends QuestionBankBean implements DynamicSearchable , Sortable {
    @ApiModelProperty(value = "标题")
    private String titleLike;
    @ApiModelProperty(value = "题目类型")
    private String labelLike;
    @ApiModelProperty(value = "企业名称")
    private String enterpriseNameLike;
    @ApiModelProperty(value = "搜索环境")
    private List<SearchCondition> searchConditions;   //搜索环境
    @ApiModelProperty(value = "搜索条件组")
    private List<SearchConditionGroup> searchConditionGroups;  //搜索条件组
    @ApiModelProperty(value = "种类")
    private Sort sort;   //种类
}
