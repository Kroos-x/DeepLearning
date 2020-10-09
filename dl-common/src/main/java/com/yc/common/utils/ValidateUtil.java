package com.yc.common.utils;

import com.google.common.collect.Iterables;
import com.yc.common.validate.ValidateResult;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 功能描述:
 *
 * @Author: xieyc
 * @Date: 2020-06-12
 * @Version: 1.0.0
 */
public class ValidateUtil {

    public static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    public ValidateUtil() {
    }

    /**
     * 校验参数核心方法
     */
    public static <T> ValidateResult validate(T object) {
        ValidateResult validateResult = new ValidateResult();
        validateResult.setResult(true);
        Set<ConstraintViolation<T>> constraintViolations = VALIDATOR.validate(object, new Class[0]);
        ConstraintViolation<T> constraintViolation = (ConstraintViolation) Iterables.getFirst(constraintViolations, (Object) null);
        if (constraintViolation != null) {
            validateResult.setResult(false);
            validateResult.setParamName(constraintViolation.getPropertyPath().toString());
            validateResult.setMsg(constraintViolation.getMessage());
        }
        return validateResult;
    }

}
