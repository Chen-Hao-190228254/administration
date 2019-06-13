package com.skm.exa.persistence.dto;


import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class UserManagementAddDto {
    /*用户账号*/
    private String accountNumber;
    /*用户登录密码*/
    private Long password;
    /*用户姓名*/
    private String name;
    /*用户身份证*/
    private String card;
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

    private List<FileSaveDto> fileSaveDtos;
}
