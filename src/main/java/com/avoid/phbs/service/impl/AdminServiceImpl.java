package com.avoid.phbs.service.impl;

import com.avoid.phbs.mapper.AdminMapper;
import com.avoid.phbs.model.Admin;
import com.avoid.phbs.service.AdminService;
import com.avoid.phbs.utils.Md5Util;
import com.avoid.phbs.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin findByAdminName(String adminname) {
        Admin a = adminMapper.findByAdminName(adminname);
        return a;
    }

    @Override
    public void register(String adminname, String password) {
        //加密
        String md5String = Md5Util.getMD5String(password);
        //添加加密
        adminMapper.add(adminname,md5String);
    }

    @Override
    public void update(Admin admin) {
        admin.setUpdateTime(LocalDateTime.now());
        adminMapper.update(admin);
    }

    @Override
    public void updatePwd(String newPwd) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        adminMapper.updatePwd(Md5Util.getMD5String(newPwd),id);
    }
}
