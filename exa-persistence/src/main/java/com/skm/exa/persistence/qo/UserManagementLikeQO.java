package com.skm.exa.persistence.qo;

import com.skm.exa.domain.bean.UserManagementBean;
import com.skm.exa.mybatis.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserManagementLikeQO extends UserManagementBean implements DynamicSearchable , Sortable {
    private String nameLike;    //名字
    private String cardLike;    //身份证
    private String accountNumberLike; // 用户账号
    private String phoneLike;   //电话
    private String nativePlaceLike;  //籍贯
    private String emailLike;   //邮箱
    private String qqLike;      //qq
    private String skillLike;  // 技能方向
    private List<SearchCondition> searchConditions;   //搜索环境
    private List<SearchConditionGroup> searchConditionGroups;  //搜索条件组
    private Sort sort;   //种类

}
