package com.avoid.phbs.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PhysiologicalData {
    private Integer id ;
    private Integer createUser;//创建人ID
    @NotEmpty
    private String temperature;//体温
    @NotEmpty
    private String bloodGlucose;//血糖
    @NotEmpty
    private String bloodPressure;//血压
    @NotEmpty
    private String heart;//心率
    private String height;//身高
    private String weight;//体重
    private String step;//步数
    private String sleep;//睡眠
    @Pattern(regexp = "^(健康|不健康)$")
    private String state;//发布状态 健康|不健康
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
