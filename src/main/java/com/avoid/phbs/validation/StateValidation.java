package com.avoid.phbs.validation;

import com.avoid.phbs.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

//给那个注解提供校验规则，将来校验的数据类型
public class StateValidation implements ConstraintValidator<State,String> {
    /**
     *
     * @param value 将来要校验的数据
     * @param constraintValidatorContext
     * @return返回F，校验不通过，反之通过
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
       if(value == null){
           return false;
       }
       if (value.equals("已发布") || value.equals("草稿")){
           return true;
       }
        return false;
    }
}
