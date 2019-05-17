package com.skm.exa.service.biz.impl;

import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.service.UnifyAdminService;
import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.domain.bean.AdminBean;
import com.skm.exa.persistence.dao.AdminDao;
import com.skm.exa.persistence.qo.AdminQO;
import com.skm.exa.persistence.dto.AdminSaveDto;
import com.skm.exa.service.BaseServiceImpl;
import com.skm.exa.service.biz.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl extends BaseServiceImpl<AdminBean , AdminDao> implements UnifyAdminService , AdminService {
    @Override
    public UnifyAdmin loadAdminByUsername(String username) {

        AdminQO adminQO = new AdminQO();
        adminQO.setUsername(username);
        AdminBean adminBean = super.find(adminQO);
        if(adminBean == null)
            return null;
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

    public List<AdminBean> getAdminList(){
        return dao.getAdminList();
    }

    @Override
    public Result<AdminBean> addAdmin(AdminSaveDto adminSaveDto) {
        String username = adminSaveDto.getUsername();
        boolean is = super.has(username);
        if (is) {
            AdminBean adminBean = BeanMapper.map(adminSaveDto,AdminBean.class);
            Result<AdminBean> result = new Result<>();
            result.setMessage("账号已存在，请重新输入账号");
            result.setContent(adminBean);
            return result;
        }

        dao.insert(adminSaveDto);

        return null;
    }
}
