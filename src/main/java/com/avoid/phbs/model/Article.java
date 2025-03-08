package com.avoid.phbs.model;

import com.avoid.phbs.anno.State;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Article {
    private Integer id;
    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String title;
    @NotEmpty
    private String content;
//@URL
    //@Pattern(regexp = "^(草稿|已发布)$")
    @State
    private String state;//发布状态 已发布|草稿
    @NotNull
    private Integer categoryId;
    private Integer createUser;//创建人id
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
