package com.skm.exa.webapi.controller;

import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.domain.bean.AdminBean;
import com.skm.exa.service.biz.AdminService;
import com.skm.exa.webapi.vo.AdminVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/getAdminList")
    public List<AdminVo> getAdminList(){
        List<AdminBean> adminBeans = adminService.getAdminList();
        List<AdminVo> adminVos = BeanMapper.mapList(adminBeans,AdminBean.class,AdminVo.class);
        return adminVos;
    }




}
