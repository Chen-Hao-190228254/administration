package com.skm.exa.webapi.controller;

import com.skm.exa.common.enums.Msg;
import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.domain.bean.RoleBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.dto.AdminDto;
import com.skm.exa.persistence.dto.AdminSaveDto;
import com.skm.exa.persistence.dto.AdminUpdateDto;
import com.skm.exa.persistence.qo.AdminQO;
import com.skm.exa.service.biz.AdminService;
import com.skm.exa.webapi.BaseController;
import com.skm.exa.webapi.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


@Api(tags = "管理员操作",description = "管理员操作")
@RestController
@RequestMapping("/web/v1/admin")
public class AdminController extends BaseController {

    @Autowired
    AdminService adminService;


    /**
     * 根据ID获得指定的管理员
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID获得管理员信息", notes = "根据ID获得管理员信息")
    @GetMapping("/getAdmin/id")
    @PreAuthorize("hasRole('ROLE_admin')")
    public Result<AdminVo> getAdmin(@ApiParam("需要获取管理员的ID") @RequestParam("id") Long id){
        AdminDto adminDto = adminService.getAdmin(id);
        if(adminDto == null)
            return Result.error(-1,"指定的ID不存在");
        return Result.success(getAdminVo(adminDto));
    }



    /**
     * 获得所有管理员
     * @return
     */
    @ApiOperation(value = "获得所有管理员信息", notes = "获得所有管理员信息")
    @GetMapping("/getAdminList")
    @PreAuthorize("hasAuthority('AT_SYSTEM')")
    public Result<List<AdminVo>> getAdminList(){
        List<AdminDto> adminDtos = adminService.getAdminList();
        return Result.success(getAdminVoList(adminDtos));
    }


    /**
     * 分页查询，可添加条件
     * @param pageParam
     * @return
     */
    @ApiOperation(value = "分页查询管理员", notes = "分页查询管理员")
    @PostMapping("/getAdminPage")
    public Result<Page<AdminVo>> getAdminPage(@ApiParam("分页及条件信息") @RequestBody PageParam<QueryVo> pageParam){
        AdminQO adminQO = new AdminQO();
        adminQO.setUsernameLike(pageParam.getCondition().getKey());
        adminQO.setNameLike(pageParam.getCondition().getKey());
        PageParam<AdminQO> param = new PageParam<>(pageParam.getPage(),pageParam.getSize());
        param.setCondition(adminQO);
        Page<AdminDto> adminDtoPage = adminService.getAdminPage(param);
        Page<AdminVo> page = BeanMapper.map(adminDtoPage,Page.class);
        List<AdminVo> adminVos = getAdminVoList(adminDtoPage.getContent());
        page.setContent(adminVos);
        return Result.success(page);
    }


    /**
     * 根据账号判读该账号是否已经存在
     * @param username
     * @return
     */
    @ApiOperation(value = "根据账号判读该账号是否已经存在", notes = "根据账号判读该账号是否已经存在")
    @GetMapping("/getAdminUsername/username")
    public Result getAdminUsername(@ApiParam("需要按账号查找的账号") @RequestParam("username") String username){
        boolean is = adminService.getAdminUsername(username);
        return Result.success(is);
    }




    /**
     * 添加管理员
     * @param adminSaveVo
     * @return
     */
    @ApiOperation(value = "添加管理员", notes = "添加管理员")
    @PostMapping("/addAdmin")
    public Result<AdminVo> addAdmin(@ApiParam("需要添加管理员的信息") @RequestBody AdminSaveVo adminSaveVo){
        UnifyAdmin unifyAdmin = getCurrentAdmin();
        AdminSaveDto adminSaveDto = BeanMapper.map(adminSaveVo, AdminSaveDto.class);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        adminSaveDto.setPassword(passwordEncoder.encode(adminSaveVo.getPassword()));
        Result<AdminDto> adminDtoResult = adminService.addAdmin(adminSaveDto, unifyAdmin);
        AdminVo adminVo = getAdminVo(adminDtoResult.getContent());
        Result<AdminVo> adminVoResult = BeanMapper.map(adminDtoResult,Result.class);
        adminVoResult.setContent(adminVo);
        return adminVoResult;
    }


    /**
     * 更新管理员
     * @param adminUpdateVo
     * @return
     */
    @ApiOperation(value = "更新管理员", notes = "更新管理员")
    @PutMapping("/updateAdmin")
    public Result<AdminVo> updateAdmin(@ApiParam("需要更新的管理员的信息") @RequestBody AdminUpdateVo adminUpdateVo){
        UnifyAdmin unifyAdmin = getCurrentAdmin();
        AdminUpdateDto adminUpdateDto = BeanMapper.map(adminUpdateVo,AdminUpdateDto.class);
        Result<AdminDto> adminDtoResult = adminService.updateAdmin(adminUpdateDto,unifyAdmin);
        AdminVo adminVo = getAdminVo(adminDtoResult.getContent());
        Result<AdminVo> result = BeanMapper.map(adminDtoResult,Result.class);
        result.setContent(adminVo);
        return result;
    }


    /**
     * 更改管理员密码
     * @param password
     * @return
     */
    @ApiOperation(value = "更改管理员密码", notes = "更改管理员密码")
    @PutMapping("/updatePassword")
    public Result<Boolean> updatePassword(@ApiParam("需要更新的管理员的信息") @RequestBody PasswordUpdateVo password){
        if(!password.getPassword1().equals(password.getPassword2()))
            return Result.error(-1,"两次输入密码的不一致");
        boolean i = password.getPassword1().matches("^[\\w_.@]{5,20}$");
        if(i){
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            return adminService.updatePassword(passwordEncoder.encode(password.getPassword1()),password.getId());
        }else {
            Result<Boolean> result = new Result<>(-1,"密码格式有误");
            result.setContent(false);
            return result;
        }
    }



    /**
     * 更改管理员密码
     * @param id
     * @return
     */
    @ApiOperation(value = "更改管理员状态", notes = "更改管理员状态")
    @PutMapping("/updateStatus/id")
    public Result<AdminVo> updateStatus(@ApiParam("需要更新的管理员ID") @RequestParam("id") Long id){
        Result<AdminDto> adminDto = adminService.updateStatus(id);
        AdminVo adminVo = getAdminVo(adminDto.getContent());
        Result<AdminVo> result = BeanMapper.map(adminDto,Result.class);
        result.setContent(adminVo);
        return result;
    }





    /**
     * 删除管理员
     * @param id
     * @return
     */
    @ApiOperation(value = "删除管理员", notes = "删除管理员")
    @DeleteMapping("/deleteAdmin/id")
    public Result<Boolean> deleteAdmin(@ApiParam("需要删除管理员的ID") @RequestParam("id") Long id){
        boolean is = adminService.deleteAdmin(id);
        return is ? Result.success() : Result.error(Msg.E40000);
    }







    /**
     * 类型转换     根据AdminDto转换为AdminVo
     * @param adminDto
     * @return
     */
    public AdminVo getAdminVo(AdminDto adminDto){
        if(adminDto == null)
            return null;
        List<RoleVo> roleVos = BeanMapper.mapList(adminDto.getRoleBeans(), RoleBean.class,RoleVo.class);
        AdminVo adminVo = BeanMapper.map(adminDto,AdminVo.class);
        adminVo.setRoleVos(roleVos);
        return adminVo;
    }


    /**
     * 类型转换    根据List<AdminDto>转换为List<AdminVo>
     * @param adminDtos
     * @return
     */
    public List<AdminVo> getAdminVoList(List<AdminDto> adminDtos){
        List<AdminVo> adminVos = BeanMapper.mapList(adminDtos,AdminDto.class,AdminVo.class);
        Map<Long,AdminVo> mapAdminVo = new HashMap<>();
        for(AdminVo adminVo:adminVos){
            mapAdminVo.put(adminVo.getId(),adminVo);
        }
        for(AdminDto adminDto:adminDtos){
            List<RoleVo> roleVos = BeanMapper.mapList(adminDto.getRoleBeans(),RoleBean.class,RoleVo.class);
            mapAdminVo.get(adminDto.getId()).setRoleVos(roleVos);
        }
        adminVos = new ArrayList<>();
        for (Long adminId:mapAdminVo.keySet()){
            adminVos.add(mapAdminVo.get(adminId));
        }
        return adminVos;
    }



}
