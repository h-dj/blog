package cn.hdj.hdjblog.aspect;

import cn.hdj.hdjblog.dao.ArticleDao;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author hdj
 * @version 1.0
 * @date 30/05/2020 22:33
 * @description: 文章查看点赞 AOP
 */
@Slf4j
@Aspect
@Component
public class ArticleLikeAspect {

    @Autowired
    private ArticleDao articleDao;

    /**
     * 定义切入点
     */
    @SuppressWarnings("unchecked")
    @Pointcut("@annotation(cn.hdj.hdjblog.aspect.annotation.ArticleLike)")
    public void articleLikePointCut() {
    }

    @AfterReturning("articleLikePointCut()")
    public void afterReturning(JoinPoint joinPoint) {
        String slug = (String) joinPoint.getArgs()[0];
        articleDao.updateLikeNum(slug);
    }
}
