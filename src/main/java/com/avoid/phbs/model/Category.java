package com.avoid.phbs.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Category {
    private Integer id;//主键ID
    private String catagoryName;//分类名称
    private String categoryAlias;//分类别名
    private Integer createUser;//创建人ID
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
