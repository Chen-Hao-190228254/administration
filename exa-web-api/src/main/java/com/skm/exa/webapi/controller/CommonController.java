package com.skm.exa.webapi.controller;


import com.skm.exa.common.enums.Msg;
import com.skm.exa.common.object.Result;
import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.domain.bean.AreaBean;
import com.skm.exa.domain.bean.FileBean;
import com.skm.exa.domain.bean.LabelBean;
import com.skm.exa.domain.bean.StatusBean;
import com.skm.exa.service.biz.CommonService;
import com.skm.exa.webapi.vo.AreaVo;
import com.skm.exa.webapi.vo.LabelVo;
import com.skm.exa.webapi.vo.StatusVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Api(tags = "通用接口",description = "通用接口")
@RestController
@RequestMapping("/web/v1/common")
public class CommonController {



    @Autowired
    CommonService commonService;


    /**
     * 状态获取
     * @return
     */
    @ApiOperation(value = "获得所有状态", notes = "获得所有状态，便于下拉框是选用")
    @GetMapping("/getAdmin")
    public Result getStatus(){
        List<StatusVo> statusVos = BeanMapper.mapList(commonService.getStatus(), StatusBean.class,StatusVo.class);
        return Result.success(statusVos);
    }

    /**
     * 地址获取
     * @param code
     * @return
     */
    @ApiOperation(value = "根据父地址的CODE获得下一级的所有地址、省级ParentCode为0", notes = "根据父地址的CODE获得下一级的所有地址，便于下拉框是选用")
    @GetMapping("/getAreaParentCode/code")
    public Result getAreaParentCode(@RequestParam("code") Long code){
        List<AreaVo> areaVos = BeanMapper.mapList(commonService.getAreaParentCode(code), AreaBean.class,AreaVo.class);
        return Result.success(areaVos);
    }


    /**
     * 获得标签
     * @return
     */
    @ApiOperation("获取标签")
    @GetMapping("/getLabel")
    public Result<List<LabelVo>> getLabel(){
        List<LabelVo> labelVos = BeanMapper.mapList(commonService.getLabel(null), LabelBean.class,LabelVo.class);
        return Result.success(labelVos);
    }


    /**
     * 添加标签
     * @return
     */
    @ApiOperation("添加标签")
    @GetMapping("/addLabel/name")
    private Result<LabelVo> addLabel(@ApiParam("标签名称") @RequestParam("name") String name){
        LabelBean labelBean = commonService.addLabel(name);
        if(labelBean.getId() == null || labelBean.getId() == 0)
            return Result.error(Msg.E40019);
        return Result.success(BeanMapper.map(labelBean,LabelVo.class));
    }




    /**
     * 图片上传
     * @param files
     * @return
     */
    @ApiOperation(value = "多文件上传文件、图片", notes = "多文件上传文件、图片")
    @PostMapping("/uploadFiles")
    public Result<List<FileBean>> uploadFiles(@ApiParam("需要上传的文件") @RequestBody MultipartFile[] files){
        if(files == null || files.length == 0)
            return Result.error(Msg.E40017);
        List<FileBean> fileBeans = commonService.uploadFile(files);
        return Result.success(fileBeans);
    }

    /**
     * 图片上传
     * @param file
     * @return
     */
    @ApiOperation(value = "上传文件、图片", notes = "上传文件、图片")
    @PostMapping("/uploadFile")
    public Result<FileBean> uploadFile(@ApiParam("需要上传的文件") MultipartFile file){
        if(file == null)
            return Result.error(Msg.E40017);
        FileBean result = commonService.uploadFile(file);
        if(result == null)
            return Result.error(Msg.E40020);
        return Result.success(result);
    }





}
