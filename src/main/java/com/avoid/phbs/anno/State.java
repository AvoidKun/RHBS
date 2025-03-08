package com.avoid.phbs.anno;

import com.avoid.phbs.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;


@Documented//元注解,表明这个是可以被抽取到帮助文档里
@Target({ElementType.FIELD})//说明这个元注解可以用在什么地方上
@Retention(RetentionPolicy.RUNTIME)//标识这个元注解会在哪个阶段被保留
@Constraint(validatedBy = {StateValidation.class })//谁给这个注解提供校验规则
public @interface State {
    //提供校验失败的提示信息
    String message() default "state参数的值只能是已发布或者草稿";

    //指定分组
    Class<?>[] groups() default { };

    //负载获取state注解的附加信息
    Class<? extends Payload>[] payload() default {};

}
