package cn.hdj.hdjblog.aspect;

import cn.hdj.hdjblog.aspect.annotation.RefreshEsMqSender;
import cn.hdj.hdjblog.constaint.RabbitMqConstants;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author hdj
 * @version 1.0
 * @date 20/01/2020 21:49
 * @description: 刷新EsAOP
 */
@Slf4j
@Aspect
@Component
public class RefreshEsMqAspect {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 定义切入点
     */
    @SuppressWarnings("unchecked")
    @Pointcut("@annotation(cn.hdj.hdjblog.aspect.annotation.RefreshEsMqSender)")
    public void pointCut() {
    }

    @AfterReturning(pointcut = "pointCut()")
    public void after(JoinPoint point){
        Signature signature = point.getSignature();
        //获取定义的方法
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        RefreshEsMqSender refreshEsMqSender = method.getAnnotation(RefreshEsMqSender.class);
        rabbitTemplate.convertAndSend(RabbitMqConstants.REFRESH_ES_INDEX_QUEUE,RabbitMqConstants.REFRESH_ES_INDEX_QUEUE,refreshEsMqSender.msg());
    }
}
