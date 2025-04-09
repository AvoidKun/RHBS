package com.avoid.phbs.service;

import com.avoid.phbs.model.Admin;

public interface AdminService {
    //查询
    Admin findByAdminName(String adminname);
    //注册
    void register(String adminname, String password);

    //修改
    void update(Admin admin);
    //密码
    void updatePwd(String newPwd);
}
