package com.skm.exa.webapi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "角色管理删除数据")
public class UserManagementDeleteVO {
    /*用户id*/
    @ApiModelProperty(value = "用户id")
    private Long id;
    /*用户账号*/
    @ApiModelProperty(value = "用户账号")
    private String accountNumber;
    /*用户登录密码*/
    @ApiModelProperty(value = "用户登录密码")
    private String password;
    /*用户姓名*/
    @ApiModelProperty(value = "用户姓名")
    private String name;
    /*用户头像*/
    @ApiModelProperty(value = "用户头像")
    private Long photoId;
    /*用户身份证*/
    @ApiModelProperty(value = "用户身份证")
    private String card;
    /*用户身份证正面照面id*/
    @ApiModelProperty(value = "用户身份证正面照片")
    private Long cardPhotoFrontId;
    /*用户身份证反面照片id*/
    @ApiModelProperty(value = "用户身份证反面照片")
    private Long cardPhotoReverseId;
    /*用户手机号*/
    @ApiModelProperty(value = "用户手机号")
    private String phone;
    /*用户籍贯*/
    @ApiModelProperty(value = "用户籍贯")
    private String nativePlace;
    /*用户邮箱*/
    @ApiModelProperty(value = "用户邮箱")
    private String email;
    /*用户qq*/
    @ApiModelProperty(value = "用户qq")
    private String qq;
    /*用户技能方向*/
    @ApiModelProperty(value = "用户技能方向")
    private String skill;
    /*用户报道时间*/
    @ApiModelProperty(value = "用户报道时间")
    private Date reportDt;
    /*用户离开时间*/
    @ApiModelProperty(value = "用户离开时间")
    private Date leaveDt;
    /*用户状态*/
    @ApiModelProperty(value = "用户状态")
    private Long status;
}
