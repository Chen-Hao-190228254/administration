package com.skm.exa.webapi.controller;

import com.skm.exa.common.enums.Msg;
import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.dto.*;
import com.skm.exa.persistence.qo.EnterpriseQO;
import com.skm.exa.service.biz.EnterpriseService;
import com.skm.exa.webapi.BaseController;
import com.skm.exa.webapi.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api(tags = "企业管理",description = "企业管理")
@RestController
@RequestMapping("/web/v1/enterprise")
public class EnterpriseController extends BaseController {

    @Autowired
    EnterpriseService enterpriseService;

    /**
     * 获得企业
     * @return
     */
    @ApiOperation(value = "获得所有企业", notes = "获得所有企业")
    @GetMapping("/getEnterpriseList")
    public Result<List<EnterpriseVo>> getEnterpriseList(){
        List<EnterpriseDto> list = enterpriseService.getEnterpriseList();
        List<EnterpriseVo> enterpriseVos = BeanMapper.mapList(list,EnterpriseDto.class,EnterpriseVo.class);
        return Result.success(enterpriseVos);
    }

    /**
     * 获得企业
     * @return
     */
    @ApiOperation(value = "根据ID获取企业", notes = "根据ID获取企业")
    @GetMapping("/getEnterprise/id")
    public Result<EnterpriseVo> getEnterprise(@ApiParam("需要获取企业的ID") @RequestParam("id") Long id){
        EnterpriseDto enterpriseDto = enterpriseService.getEnterprise(id);
        if(enterpriseDto == null)
            return Result.error(Msg.E40016);
        EnterpriseVo enterpriseVo = BeanMapper.map(enterpriseDto,EnterpriseVo.class);
        return Result.success(enterpriseVo);
    }

    /**
     * 分页获得企业
     * @return
     */
    @ApiOperation(value = "分页获得企业", notes = "分页获得企业")
    @PostMapping("/getEnterprisePage")
    public Result<Page<EnterpriseVo>> getEnterprisePage(@ApiParam("分页及条件信息") @RequestBody PageParam<QueryVo> queryVoPageParam){
        EnterpriseQO enterpriseQO = new EnterpriseQO(queryVoPageParam.getCondition().getKey(),queryVoPageParam.getCondition().getKey());
        PageParam<EnterpriseQO> pageParam = new PageParam<>(queryVoPageParam.getPage(),queryVoPageParam.getSize());
        pageParam.setCondition(enterpriseQO);
        Page<EnterpriseDto> page = enterpriseService.getEnterprisePage(pageParam);
        List<EnterpriseVo> enterpriseVo = BeanMapper.mapList(page.getContent(),EnterpriseDto.class,EnterpriseVo.class);
        Page<EnterpriseVo> enterpriseVoPage = BeanMapper.map(page,Page.class);
        enterpriseVoPage.setContent(enterpriseVo);
        return Result.success(enterpriseVoPage);
    }

    @ApiOperation("添加企业")
    @PostMapping("/addEnterprise")
    public Result addEnterprise(@ApiParam("需要添加的企业信息") @RequestBody EnterpriseSaveVo enterpriseSaveVo){
        EnterpriseSaveDto enterpriseSaveDto = BeanMapper.map(enterpriseSaveVo,EnterpriseSaveDto.class);
        enterpriseSaveDto.setFileSaveDtos(BeanMapper.mapList(enterpriseSaveVo.getFileSaveVos(), FileSaveVo.class, FileSaveDto.class));
        boolean is = enterpriseService.addEnterprise(enterpriseSaveDto,getCurrentAdmin());
        return is? Result.success():Result.error(Msg.E40019);
    }

    @ApiOperation("更新企业")
    @PutMapping("/updateEnterprise")
    public Result updateEnterprise(@ApiParam("需要更新的企业信息") @RequestBody EnterpriseUpdateVo enterpriseUpdateVo) {
        UnifyAdmin unifyAdmin = getCurrentAdmin();
        EnterpriseUpdateDto enterpriseUpdateDto = BeanMapper.map(enterpriseUpdateVo, EnterpriseUpdateDto.class);
        enterpriseUpdateDto.setFileUpdateDtos(BeanMapper.mapList(enterpriseUpdateVo.getFileUpdateVos(), FileUpdateVo.class, FileUpdateDto.class));
        boolean is = enterpriseService.updateEnterprise(enterpriseUpdateDto, unifyAdmin);
        return is? Result.success():Result.error(Msg.E40023);
    }

    /**
     * 删除企业
     * @param id
     * @return
     */
    @ApiOperation(value = "删除企业", notes = "删除企业")
    @DeleteMapping("/deleteEnterprise")
    public Result deleteEnterprise(@ApiParam("需要删除企业的ID") @RequestParam("id") Long id){
        boolean i = enterpriseService.deleteEnterprise(id);
        return i? Result.success():Result.error(Msg.E40022);
    }

    /**
     * 更改企业状态
     * @param setStatusVo
     * @return
     */
    @ApiOperation(value = "更改企业状态", notes = "更改企业状态/只能在禁用与启用的状态中切换")
    @PutMapping("/setEnterpriseStatus")
    public Result setEnterpriseStatus(@ApiParam("需要更改状态的企业的ID") @RequestBody SetStatusVo setStatusVo){
        boolean is = enterpriseService.updateEnterprise(new EnterpriseUpdateDto(setStatusVo.getId(),setStatusVo.getStatus()),getCurrentAdmin());
        return is? Result.success():Result.error(Msg.E40023);
    }

}
