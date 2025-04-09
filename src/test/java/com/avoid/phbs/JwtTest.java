package com.avoid.phbs;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {
//    @Test
//    public void testGen(){
//        Map<String,Object> claims = new HashMap<>();
//        claims.put("id","1");
//        claims.put("username","张三三");
//        String  token = JWT.create()
//                .withClaim("user",claims)//添加载荷
//                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*24))//添加过期时间
//                .sign(Algorithm.HMAC256("Avoid"));//制定算法，配置秘钥
//
//        System.out.println(token);
//    }
//    @Test
//    public void  testParse(){
//        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoiMSIsInVzZXJuYW1lIjoi5byg5LiJ5LiJIn0sImV4cCI6MTczNzk3MTY0Nn0.3TvvR2hKUewE1Scddvl4qr9IiZi1JIcwSA15KAGyEfE";
//        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("Avoid")).build();//验证器
//        DecodedJWT decodedJWT = jwtVerifier.verify(token);//验证，并生成一个解析后的JWT对象
//        Map<String, Claim> claims = decodedJWT.getClaims();//得到所有载荷
//        System.out.println(claims.get("user"));
//
//    }
}
