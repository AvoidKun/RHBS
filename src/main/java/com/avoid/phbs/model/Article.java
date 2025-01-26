package com.avoid.phbs.model;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Article {
    private Integer id;
    private String title;
    private String content;
    private String state;//发布状态 已发布|草稿
    private Integer categoryId;
    private Integer createUser;//创建人id
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
