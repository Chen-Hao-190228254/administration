package com.skm.exa.webapi.controller;

import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.domain.bean.AdminBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.dto.AdminSaveDto;
import com.skm.exa.persistence.qo.AdminQO;
import com.skm.exa.service.biz.AdminService;
import com.skm.exa.webapi.BaseController;
import com.skm.exa.webapi.vo.AdminSaveVo;
import com.skm.exa.webapi.vo.AdminVo;
import com.skm.exa.webapi.vo.QueryVo;
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
     * 根据ID获得指定的管理员
     * @param id
     * @return
     */
    @PostMapping("/getAdmin/id")
    public Result<AdminVo> getAdmin(@RequestParam("id") Long id){
        AdminBean adminBean = adminService.getAdmin(id);
        AdminVo adminVo = BeanMapper.map(adminBean,AdminVo.class);
        return Result.success(adminVo);
    }



    @PostMapping("/getAdminPage")
    public Result<Page<AdminVo>> getAdminPage(@RequestBody PageParam<QueryVo> pageParam){
        AdminQO adminQO = new AdminQO();
        adminQO.setUsernameLike(pageParam.getCondition().getKey());
        adminQO.setNameLike(pageParam.getCondition().getKey());
        PageParam<AdminQO> param = new PageParam<>(pageParam.getPage(),pageParam.getSize());
        param.setCondition(adminQO);
        Page<AdminBean> adminBeanPage = adminService.getAdminPage(param);
        Page<AdminVo> page = BeanMapper.map(adminBeanPage,Page.class);
        List<AdminVo> adminVos = BeanMapper.mapList(adminBeanPage.getContent(),AdminBean.class,AdminVo.class);
        page.setContent(adminVos);
        return Result.success(page);
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
