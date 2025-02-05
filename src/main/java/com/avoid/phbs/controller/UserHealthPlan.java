package com.avoid.phbs.controller;

import com.avoid.phbs.model.Result;
import com.avoid.phbs.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/plan")
public class UserHealthPlan {
    @GetMapping("/list")
    public Result<String> list(/*@RequestHeader(name = "Authorization") String token, HttpServletResponse response*/){
        //验证token

//        try{
//            Map<String,Object>claims = JwtUtil.parseToken(token);
//            return Result.success("所有的计划数据..");
//        } catch (Exception e){
//            //失败了
//            response.setStatus(401);
//            return Result.error("未登录");
//        }
        return Result.success("所有的计划数据..");
    }
}
