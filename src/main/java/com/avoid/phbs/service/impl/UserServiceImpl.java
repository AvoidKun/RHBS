package com.avoid.phbs.service.impl;

import com.avoid.phbs.mapper.UserMapper;
import com.avoid.phbs.model.User;
import com.avoid.phbs.service.UserService;
import com.avoid.phbs.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUserName(String username) {
        User u = userMapper.findByUserName(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
        //密码加密;
        String md5String = Md5Util.getMD5String(password);
        //添加m
        userMapper.add(username,md5String);
    }
}
