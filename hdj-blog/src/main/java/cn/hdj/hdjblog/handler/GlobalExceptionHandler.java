package cn.hdj.hdjblog.handler;

import cn.hdj.hdjblog.constaint.enums.ResponseCodeEnum;
import cn.hdj.hdjblog.exception.MyException;
import cn.hdj.hdjblog.model.vo.ResultVO;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @author h_dj
 * @Date: 2019/6/2 16:43
 * @Description: 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义异常
     *
     * @param ex 异常信息
     * @return 返回前端异常信息
     */
    @ExceptionHandler(MyException.class)
    public ResultVO exception(MyException ex) {
        log.error("错误详情：" + ex.getMessage(), ex);
        return ResultVO.errorJson(
                ex.getMsg(),
                ex.getCode()
        );
    }

    /**
     * token 验证异常
     *
     * @param ex 异常信息
     * @return 返回前端异常信息
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(JWTVerificationException.class)
    public ResultVO exception(JWTVerificationException ex) {
        log.error("错误详情：" + ex.getMessage(), ex);
        return ResultVO.errorJson(ResponseCodeEnum.TOKEN_GENERATOR_EXPIRE);
    }

    /**
     * 处理路径找不到异常
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResultVO handlerNoFoundException(NoHandlerFoundException e) {
        log.error("错误详情：" + e.getMessage(), e);
        return ResultVO.errorJson(ResponseCodeEnum.PATH_NOT_FOUND);
    }

    /**
     * 插入数据库重复数据异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public ResultVO handleDuplicateKeyException(DuplicateKeyException e) {
        log.error("错误详情：" + e.getMessage(), e);
        return ResultVO.errorJson(ResponseCodeEnum.DUPLICATE_KEY);
    }


    /**
     * 未授权异常
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthorizationException.class)
    public ResultVO handleAuthorizationException(AuthorizationException e) {
        log.error("错误详情：" + e.getMessage(), e);
        return ResultVO.errorJson(ResponseCodeEnum.NO_AUTH);
    }

    /**
     * 账号验证异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResultVO handleUnknownAccountException(AuthenticationException e) {
        log.error("错误详情：" + e.getMessage(), e);
        if (e instanceof UnknownAccountException) {
            return ResultVO.errorJson(ResponseCodeEnum.USERNAME_OR_PASSWORD_WRONG);
        }
        if (e instanceof LockedAccountException) {
            return ResultVO.errorJson(ResponseCodeEnum.LOCK_ACCOUNT);
        }
        if (e instanceof IncorrectCredentialsException) {
            return ResultVO.errorJson(ResponseCodeEnum.USERNAME_OR_PASSWORD_WRONG);
        }
        return ResultVO.errorJson(ResponseCodeEnum.LOGIN_FAIL);

    }

    /**
     * 系统其它异常
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResultVO handleException(Exception e) {
        log.error("错误详情：" + e.getMessage(), e);
        return ResultVO.errorJson();
    }
}