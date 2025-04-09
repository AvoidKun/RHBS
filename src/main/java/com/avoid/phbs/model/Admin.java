package com.avoid.phbs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
public class Admin {
    @NonNull
    private Integer id;//主键id
    private String adminname;//真实姓名
    @JsonIgnore
    private String password;//密码
    @NotEmpty
    @Email
    private String email;//邮件地址
    @Pattern(regexp = "^\\S{11}$")
    private String phone;//联系电话
    private String position;//所属部门
    private LocalDateTime createTime;//创建账号时间
    private LocalDateTime updateTime;//修改账号信息时间
}
