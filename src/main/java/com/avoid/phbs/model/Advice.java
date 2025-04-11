package com.avoid.phbs.model;

import com.avoid.phbs.anno.State;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Advice {
    private Integer id;
    private String title;
    private String content;
    //@URL
    //@Pattern(regexp = "^(草稿|已发布)$")
    @State
    private String state;//发布状态 已发布|草稿
    private Integer userId;
    private Integer adminId;//管理员id
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
