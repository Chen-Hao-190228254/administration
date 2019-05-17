package com.skm.exa.persistence.qo;

import com.skm.exa.domain.bean.UserManagementBean;
import com.skm.exa.mybatis.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserManagementLikeQO extends UserManagementBean implements DynamicSearchable , Sortable {
    @ApiModelProperty(value = "名字")
    private String nameLike;    //名字
    @ApiModelProperty(value = "身份证")
    private String cardLike;    //身份证
    @ApiModelProperty(value = "用户账号")
    private String accountNumberLike; // 用户账号
    @ApiModelProperty(value = "电话")
    private String phoneLike;   //电话
    @ApiModelProperty(value = "籍贯")
    private String nativePlaceLike;  //籍贯
    @ApiModelProperty(value = "邮箱")
    private String emailLike;   //邮箱
    @ApiModelProperty(value = "qq")
    private String qqLike;      //qq
    @ApiModelProperty(value = "技能方向")
    private String skillLike;  // 技能方向
    @ApiModelProperty(value = "搜索环境")
    private List<SearchCondition> searchConditions;   //搜索环境
    @ApiModelProperty(value = "搜索环境组")
    private List<SearchConditionGroup> searchConditionGroups;  //搜索条件组
    @ApiModelProperty(value = "种类")
    private Sort sort;   //种类

}
