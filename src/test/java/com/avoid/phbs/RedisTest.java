package com.avoid.phbs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@SpringBootTest//单元测试方法执行之前会先初始化Spring容器
public class RedisTest {
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//    @Test
//    public void testSet(){
//        ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
//        operations.set("username","zhangsansan");
//        operations.set("id","1",15, TimeUnit.SECONDS);
//    }
//    @Test
//    public void testGet(){
//        ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
//        System.out.println(operations.get("username"));
//    }
}
