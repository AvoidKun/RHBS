package com.avoid.phbs.model;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Medicines {
    private Integer id;
    private String medicinename;//药名
    private String genericname;//俗名（感冒药、高血压药）
    private String description;//药物说明
    @Pattern(regexp = "^(盈余|告罄)$")
    private String state;//药物状态 盈余|告罄
    private Integer createAdmin;//创建管理员id
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
