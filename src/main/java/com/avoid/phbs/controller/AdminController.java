package com.avoid.phbs.controller;

import com.avoid.phbs.model.Admin;
import com.avoid.phbs.model.Result;
import com.avoid.phbs.model.User;
import com.avoid.phbs.service.AdminService;
import com.avoid.phbs.utils.JwtUtil;
import com.avoid.phbs.utils.Md5Util;
import com.avoid.phbs.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/admin")
@Validated
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{2,4}$") String adminname, @Pattern(regexp = "^\\S{5,16}$") String password) {
        Admin a =adminService.findByAdminName(adminname);
        if(a == null){
            //没有占用
            adminService.register(adminname,password);
            return Result.success();
        }else {
            return Result.error("管理名已经被占用");
        }
    }
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{2,4}$") String adminname,@Pattern(regexp = "^\\S{5,16}$") String password){
        //根据管理名查询管理员
        Admin loginAdmin = adminService.findByAdminName(adminname);
        //判断
        if(loginAdmin == null){
            return Result.error("管理名错误");
        }
        //判断密码
        if(Md5Util.getMD5String(password).equals(loginAdmin.getPassword())){
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",loginAdmin.getId());
            claims.put("adminname",loginAdmin.getAdminname());
            String token = JwtUtil.genToken(claims);
            //将token储存到redis中
            ValueOperations operations = stringRedisTemplate.opsForValue();
            operations.set(token,token,3, TimeUnit.DAYS);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    @GetMapping("/adminInfo")
    public Result<Admin> adminInfo(){
        Map<String,Object> map = ThreadLocalUtil.get();
        String adminname =(String) map.get("adminname");
        Admin admin =adminService.findByAdminName(adminname);
        return Result.success(admin);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated Admin admin){
        adminService.update(admin);
        return Result.success();
    }
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params,@RequestHeader("Authorization") String token){
        //校验
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");
        if(!StringUtils.hasLength(oldPwd)|| !StringUtils.hasLength(newPwd)||!StringUtils.hasLength(rePwd)){
            return Result.error("缺少必要要素");
        }
        //原密码是否正确
        //调用adminService根据用户名拿到原密码，再与old_pwd比对
        Map<String,Object> map =ThreadLocalUtil.get();
        String adminname = (String) map.get("adminname");
        Admin loginAdmin = adminService.findByAdminName(adminname);
        if (loginAdmin == null) {
            return Result.error("管理员账号不存在");
        }
        if (!loginAdmin.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码错误");
        }
        if(!rePwd.equals(newPwd)){
            return Result.error("俩次填写的新密码不一样");
        }
        //调用service完成密码更新
        adminService.updatePwd(newPwd);
        ValueOperations<String,String> operations =stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();
    }

}

