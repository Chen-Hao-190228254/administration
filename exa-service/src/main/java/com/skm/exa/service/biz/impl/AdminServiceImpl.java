package com.skm.exa.service.biz.impl;

import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.service.UnifyAdminService;
import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.domain.bean.AdminBean;
import com.skm.exa.persistence.dao.AdminDao;
import com.skm.exa.persistence.dto.AdminUpdateDto;
import com.skm.exa.persistence.qo.AdminQO;
import com.skm.exa.persistence.dto.AdminSaveDto;
import com.skm.exa.service.BaseServiceImpl;
import com.skm.exa.service.biz.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AdminServiceImpl extends BaseServiceImpl<AdminBean , AdminDao> implements UnifyAdminService , AdminService {


    /**
     * 管理员登录服务接口
     * @param username 用户名
     * @return
     */
    @Override
    public UnifyAdmin loadAdminByUsername(String username) {
        AdminQO adminQO = new AdminQO();
        adminQO.setUsername(username);
        AdminBean adminBean = super.find(adminQO);
        if(adminBean == null) return null;
        UnifyAdmin unifyAdmin = new UnifyAdmin();
        unifyAdmin.setId(adminBean.getId());
        unifyAdmin.setUsername(adminBean.getUsername());
        unifyAdmin.setPassword(adminBean.getPassword());
        unifyAdmin.setName(adminBean.getName());
        unifyAdmin.setPhone(adminBean.getPhone());
        unifyAdmin.setEmail(adminBean.getEmail());
        unifyAdmin.setStatus(adminBean.getStatus());
        System.out.println("登录人的数据："+unifyAdmin.toString());
        return unifyAdmin;
    }


    /**
     * 获得所有管理员
     * @return
     */
    public List<AdminBean> getAdminList(){
        return dao.getAdminList();
    }


    /**
     * 获得指定ID的管理员
     * @param id
     * @return
     */
    public AdminBean getAdmin(Long id){
        return null;
    }




    /**
     * 添加管理员
     * @param adminSaveDto
     * @return
     */
    @Override
    @Transactional
    public Result<AdminBean> addAdmin(AdminSaveDto adminSaveDto, UnifyAdmin unifyAdmin) {
        String username = adminSaveDto.getUsername();
        AdminQO adminQO = new AdminQO();
        adminQO.setUsername(username);
        boolean is = super.has(adminQO);
        if (is) {
            AdminBean adminBean = BeanMapper.map(adminSaveDto,AdminBean.class);
            Result<AdminBean> result = new Result<>();
            result.setMessage("账号已存在，请重新输入账号");
            result.setContent(adminBean);
            System.out.println("账号已存在："+adminBean.toString());
            return result;
        }
        adminSaveDto.setEntryId(unifyAdmin.getId());
        adminSaveDto.setEntryName(unifyAdmin.getName());
        adminSaveDto.setEntryDt(new Date());
        adminSaveDto.setUpdateId(unifyAdmin.getId());
        adminSaveDto.setUpdateName(unifyAdmin.getName());
        adminSaveDto.setUpdateDt(new Date());
        int i = dao.insert(adminSaveDto);
        if(i<=0){
            AdminBean adminBean = BeanMapper.map(adminSaveDto,AdminBean.class);
            Result<AdminBean> result = new Result<>();
            result.setMessage("添加失败！");
            System.out.println("添加失败："+adminBean.toString());
            result.setContent(adminBean);
            return result;
        }else {
            AdminBean adminBean = super.find(adminQO);
            System.out.println("添加成功："+adminBean.toString());
            return Result.success(adminBean);
        }
    }


    /**
     * 更新管理员
     * @param adminUpDto
     * @param unifyAdmin
     * @return
     */
    @Override
    public AdminBean upAdmin(AdminUpdateDto adminUpDto, UnifyAdmin unifyAdmin) {
        return null;
    }
}
