package com.avoid.phbs.model;

import com.avoid.phbs.anno.State;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Evaluation {
    private Integer id;
    @Pattern(regexp = "^\\S{1,10}$")
    private String type;
    @NotEmpty
    private String rating;
    @NotEmpty
    private String content;
    @Pattern(regexp = "^(投诉|赞美)$")
    private String state;//发布状态 投诉|赞美
    private Integer createUser;//创建人id
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
