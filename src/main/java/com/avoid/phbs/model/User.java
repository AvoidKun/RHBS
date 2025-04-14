package com.avoid.phbs.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
public class User {
    @NonNull
    private Integer id;//主键id
    private String username;//真实姓名
    @JsonIgnore
    private String password;//密码
    @NonNull
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname;//昵称
    private String age;//年龄
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$",
            message = "出生日期格式应为yyyy-MM-dd")
    private String birth;//出生年月日
    private String sex;//性别
    @NotEmpty
    @Email
    private String email;//邮件地址
    @Pattern(regexp = "^\\S{11}$")
    private String phone;//联系电话
    private String userPic;//头像地址
    private LocalDateTime createTime;//创建账号时间
    private LocalDateTime updateTime;//修改账号信息时间
}
