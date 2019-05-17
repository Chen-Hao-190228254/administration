package com.skm.exa.webapi.controller;

import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.dto.EnterpriseDto;
import com.skm.exa.persistence.dto.EnterpriseSaveDto;
import com.skm.exa.persistence.dto.EnterpriseUpdateDto;
import com.skm.exa.persistence.qo.EnterpriseQO;
import com.skm.exa.service.biz.EnterpriseService;
import com.skm.exa.webapi.BaseController;
import com.skm.exa.webapi.vo.EnterpriseSaveVo;
import com.skm.exa.webapi.vo.EnterpriseUpdateVo;
import com.skm.exa.webapi.vo.EnterpriseVo;
import com.skm.exa.webapi.vo.QueryVo;
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
        UnifyAdmin unifyAdmin = getCurrentAdmin();
        EnterpriseSaveDto enterpriseSaveDto = BeanMapper.map(enterpriseSaveVo,EnterpriseSaveDto.class);
        Result<EnterpriseDto> enterpriseDto = enterpriseService.addEnterprise(enterpriseSaveDto,unifyAdmin);
        return null;
    }



    @ApiOperation("更新企业")
    @PostMapping("/updateEnterprise")
    public Result updateEnterprise(@ApiParam("需要更新的企业信息") @RequestBody EnterpriseUpdateVo enterpriseUpdateVo){
        UnifyAdmin unifyAdmin = getCurrentAdmin();
        EnterpriseUpdateDto enterpriseUpdateDto = BeanMapper.map(enterpriseUpdateVo,EnterpriseUpdateDto.class);
        Result<EnterpriseDto> enterpriseDto = enterpriseService.updateEnterprise(enterpriseUpdateDto,unifyAdmin);
        return null;
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
        if(i)
            return Result.success("删除成功");
        return Result.error(-1,"删除失败");
    }


    /**
     * 删除企业
     * @param id
     * @return
     */
    @ApiOperation(value = "更改企业状态", notes = "更改企业状态/只能在禁用与启用的状态中切换")
    @PutMapping("/deleteEnterprise")
    public Result setEnterpriseStatus(@ApiParam("需要更改状态的企业的ID") @RequestParam("id") Long id){
        EnterpriseDto enterpriseDto = enterpriseService.setEnterpriseStatus(id);
        return null;
    }


}
