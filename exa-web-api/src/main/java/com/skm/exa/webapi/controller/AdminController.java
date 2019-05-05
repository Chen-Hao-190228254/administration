package com.skm.exa.webapi.controller;

import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.domain.bean.AdminBean;
import com.skm.exa.persistence.dto.AdminSaveDto;
import com.skm.exa.service.biz.AdminService;
import com.skm.exa.webapi.BaseController;
import com.skm.exa.webapi.vo.AdminSaveVo;
import com.skm.exa.webapi.vo.AdminVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/web/v1/admin")
public class AdminController extends BaseController {

    @Autowired
    AdminService adminService;


    /**
     * 获得所有管理员
     * @return
     */
    @PostMapping("/getAdminList")
    public List<AdminVo> getAdminList(){
        UnifyAdmin unifyAdmin = getCurrentAdmin();
        System.out.println(unifyAdmin.getName());
        List<AdminBean> adminBeans = adminService.getAdminList();
        List<AdminVo> adminVos = BeanMapper.mapList(adminBeans,AdminBean.class,AdminVo.class);
        return adminVos;
    }


    /**
     * 添加管理员
     * @param adminSaveVo
     * @return
     */
    @PostMapping("/addAdmin")
    public Result<AdminVo> addAdmin(@RequestBody AdminSaveVo adminSaveVo){
        UnifyAdmin unifyAdmin = getCurrentAdmin();
        AdminSaveDto adminSaveDto = BeanMapper.map(adminSaveVo, AdminSaveDto.class);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        adminSaveDto.setPassword(passwordEncoder.encode(adminSaveVo.getPassword()));
        Result<AdminBean> adminBeanResult = adminService.addAdmin(adminSaveDto, unifyAdmin);
        AdminVo adminVo = BeanMapper.map(adminBeanResult.getContent(),AdminVo.class);
        Result<AdminVo> adminVoResult = BeanMapper.map(adminBeanResult,Result.class);
        adminVoResult.setContent(adminVo);
        return adminVoResult;
    }



}
