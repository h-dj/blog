package cn.hdj.hdjblog.validator;

import cn.hdj.hdjblog.exception.MyException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author hdj
 * @date 2019/7/17 21:57
 * @Version 1.0
 * @Description: 验证工具
 */
public abstract class ValidatorUtils {
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
    /**
     * 校验对象
     *
     * @param object 待校验对象
     * @param groups 待校验的组
     * @throws MyException 校验不通过，则报MyException异常
     */
    public static void validateEntity(Object object, Class<?>... groups)
            throws MyException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            String msg = constraintViolations.stream().map(constant -> constant.getMessage()).collect(Collectors.joining(","));
            throw new MyException(msg);
        }
    }
}
