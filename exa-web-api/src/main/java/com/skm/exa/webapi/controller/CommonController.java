package com.skm.exa.webapi.controller;


import com.skm.exa.common.enums.StatusEnum;
import com.skm.exa.common.object.Result;
import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.domain.bean.AreaBean;
import com.skm.exa.domain.bean.StatusBean;
import com.skm.exa.service.biz.CommonService;
import com.skm.exa.webapi.vo.AreaVo;
import com.skm.exa.webapi.vo.StatusVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "通用接口",description = "通用接口")
@RestController
@RequestMapping("/web/v1/common")
public class CommonController {



    @Autowired
    CommonService commonService;



    @ApiOperation(value = "获得所有状态", notes = "获得所有状态，便于下拉框是选用")
    @GetMapping("/getAdmin")
    public Result getStatus(){
        List<StatusVo> statusVos = BeanMapper.mapList(commonService.getStatus(), StatusBean.class,StatusVo.class);
        return Result.success(statusVos);
    }


    @ApiOperation(value = "根据父地址的CODE获得下一级的所有地址、省级ParentCode为0", notes = "根据父地址的CODE获得下一级的所有地址，便于下拉框是选用")
    @GetMapping("/getAreaParentCode/code")
    public Result getAreaParentCode(@RequestParam("code") Long code){
        List<AreaVo> areaVos = BeanMapper.mapList(commonService.getAreaParentCode(code), AreaBean.class,AreaVo.class);
        return Result.success(areaVos);
    }



}
