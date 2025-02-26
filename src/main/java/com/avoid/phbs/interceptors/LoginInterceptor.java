package com.avoid.phbs.interceptors;

import com.avoid.phbs.model.Result;
import com.avoid.phbs.utils.JwtUtil;
import com.avoid.phbs.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //令牌验证
        String token = request.getHeader("Authorization");
        try{
            Map<String,Object> claims = JwtUtil.parseToken(token);
            //将数据储存到ThreadLocal中
            ThreadLocalUtil.set(claims);
            //成功
            return true;
        } catch (Exception e){
            //失败了
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空Threadlocal中的数值
        ThreadLocalUtil.remove();
    }
}
