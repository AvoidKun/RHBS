package com.avoid.phbs.model;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Integer id;//主键id
    private String username;//真实姓名
    private String password;//密码
    private String nickname;//昵称
    private String email;//邮件地址
    private String phone;//联系电话
    private String userPic;//头像地址
    private LocalDateTime createTime;//创建账号时间
    private LocalDateTime updateTime;//修改账号信息时间
}
