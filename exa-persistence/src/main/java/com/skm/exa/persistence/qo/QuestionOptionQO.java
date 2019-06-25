package com.skm.exa.persistence.qo;

import com.skm.exa.domain.bean.OptionCodesBean;
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
public class QuestionOptionQO extends OptionCodesBean implements DynamicSearchable , Sortable {

    private List<SearchCondition> searchConditions;   //搜索环境
    @ApiModelProperty(value = "搜索条件组")
    private List<SearchConditionGroup> searchConditionGroups;  //搜索条件组
    @ApiModelProperty(value = "种类")
    private Sort sort;   //种类
}
