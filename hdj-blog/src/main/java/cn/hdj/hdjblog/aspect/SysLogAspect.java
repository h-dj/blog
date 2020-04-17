package cn.hdj.hdjblog.aspect;


import cn.hdj.hdjblog.aspect.annotation.SysLog;
import cn.hdj.hdjblog.constaint.enums.LevelEnum;
import cn.hdj.hdjblog.entity.LogDO;
import cn.hdj.hdjblog.service.LogService;
import cn.hdj.hdjblog.util.MyWebUtils;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hdj
 * @Description: web日志记录切面
 * @date 9/25/19
 */
@Slf4j
@Aspect
@Component
public class SysLogAspect {

    @Autowired
    private LogService logService;

    private ThreadLocal<LogDO> logPool = new ThreadLocal<>();

    /**
     * 定义切入点
     */
    @Pointcut("@annotation(cn.hdj.hdjblog.aspect.annotation.SysLog) || @within(cn.hdj.hdjblog.aspect.annotation.SysLog)")
    public void webLogPointCut() {
    }


    /**
     * 前置通知
     *
     * @param joinPoint 　连接点
     */
    @Before("webLogPointCut()")
    public void before(JoinPoint joinPoint) {

        //获取当前时间戳
        long start = System.currentTimeMillis();

        Signature signature = joinPoint.getSignature();
        //获取定义的方法
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        //获取标题
        String title = signature.toShortString();
        SysLog sysLog = method.getAnnotation(SysLog.class);
        if (sysLog != null) {
            if (sysLog.ignore()) {
                return;
            }
            if (StrUtil.isNotEmpty(sysLog.value())) {
                title = sysLog.value();
            }
        }

        //记录日志信息
        final LogDO logDO = new LogDO();
        HttpServletRequest request = MyWebUtils.getRequest();
        //获取请求uri
        String requestURI = request.getRequestURI();
        //获取请求方法
        String requestMethod = request.getMethod();

        if (sysLog == null || !sysLog.ignoreParams()) {
            //获取请求参数
            Object[] parameters = joinPoint.getArgs();
            if (parameters != null && parameters.length > 0) {
                Map<String, Object> map = Arrays.stream(parameters)
                        .filter(v -> filterArgs(v))
                        .collect(Collectors.toMap(k -> k.getClass().getSimpleName(), v -> v));
                logDO.setParams(JSONUtil.toJsonStr(map.values()));
            }
        }

        String userAgent = request.getHeader("User-Agent");
        UserAgent ua = UserAgentUtil.parse(userAgent);
        logDO.setBrowser(ua.getBrowser().getName());
        logDO.setOs(ua.getOs().getName());
        logDO.setUrl(requestURI);
        logDO.setUserAgent(userAgent);
        logDO.setMethod(requestMethod);
        logDO.setTitle(title);
        logDO.setTime(start);
        logDO.setIp(MyWebUtils.getIpAddr(request));
        logDO.setIpAddress(MyWebUtils.getCityInfo(logDO.getIp()));
        logDO.setLevel(sysLog.logLevel().name());
        logPool.set(logDO);
    }

    /**
     * 后置返回通知
     *
     * @param joinPoint
     */
    @AfterReturning("webLogPointCut()")
    public void afterReturning(JoinPoint joinPoint) {
        LogDO logDO = logPool.get();
        try {
            if (logDO != null) {
                long time = System.currentTimeMillis() - logDO.getTime();
                logDO.setTime(time);
                logService.save(logDO);
            }
        } finally {
            if (logDO != null) {
                log.debug("请求:{}，耗时:{}ms", logDO.getUrl(), logDO.getTime());
            }
            clearCache();
        }
    }

    /**
     * 后置异常通知
     */
    @AfterThrowing(pointcut = "webLogPointCut()", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Exception exception) {
        try {
            LogDO logDO = logPool.get();
            if (logDO != null) {
                long time = System.currentTimeMillis() - logDO.getTime();
                logDO.setTime(time);
                String message = ExceptionUtil.getMessage(exception);
                logDO.setLevel(LevelEnum.ERROR.name());
                logDO.setException(Base64.encode(message));
                logService.save(logDO);
            }
        } finally {
            clearCache();
        }
    }


    /**
     * 清除缓存
     */
    private void clearCache() {
        logPool.remove();
    }

    /**
     * 过滤参数
     *
     * @param arg
     * @return
     */
    private boolean filterArgs(Object arg) {
        if (arg == null) return false;
        boolean flag = arg instanceof ServletRequest
                || arg instanceof ServletResponse
                || arg instanceof Filter
                || arg.getClass().getAnnotation(PathVariable.class) != null;

        return !flag;
    }
}
