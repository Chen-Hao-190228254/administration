package com.skm.exa.domain.bean;


import com.skm.exa.domain.BaseBean;
import lombok.Data;

import java.util.Date;

/**
 * 用户管理
 */
@Data
public class UserManagementBean extends BaseBean {
    /*用户id*/
    private Long id;
    /*用户账号*/
    private String accountNumber;
    /*用户登录密码*/
    private String password;
    /*用户姓名*/
    private String name;
    /*用户头像*/
    private Long photoId;
    /*用户身份证*/
    private String card;
    /*用户身份证正面照面id*/
    private Long cardPhotoFrontId;
    /*用户身份证反面照片id*/
    private Long cardPhotoReverseId;
    /*用户手机号*/
    private String phone;
    /*用户籍贯*/
    private String nativePlace;
    /*用户邮箱*/
    private String email;
    /*用户qq*/
    private String qq;
    /*用户技能方向*/
    private String skill;
    /*用户报道时间*/
    private Date reportDt;
    /*用户离开时间*/
    private Date leaveDt;
    /*用户状态*/
    private Long status;
}

