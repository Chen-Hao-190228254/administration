package com.skm.exa.domain.bean;

import com.skm.exa.domain.BaseBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
public class UserCodesBean extends BaseBean {
    private Long id ; //id
    private String codes;  // 代码
    private String codeName;        //代码名称
    private Long status;        //状态
    private Long editStatus;     //是否可编辑状态
}
