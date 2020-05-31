package cn.hdj.hdjblog.config;

import cn.hdj.hdjblog.shiro.*;
import cn.hdj.hdjblog.shiro.filter.JwtAuthFilter;
import cn.hdj.hdjblog.util.RedisUtils;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author hdj
 * @version 1.0
 */
@Configuration
public class ShiroConfig {

    /**
     * 验证过滤器
     */
    @Bean
    public JwtAuthFilter jwtFilter() {
        return new JwtAuthFilter();
    }

    /**
     * 不加入Servlet Filter中
     */
    @Bean
    public FilterRegistrationBean<JwtAuthFilter> filterRegistrationBean() {
        FilterRegistrationBean<JwtAuthFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(jwtFilter());
        filterRegistrationBean.setEnabled(false);
        return filterRegistrationBean;
    }


    @Bean
    public Realm dbShiroRealm(RedisUtils redisUtils) {
        DbShiroRealm myShiroRealm = new DbShiroRealm();

        RetryLimitCredentialsMatcher retryLimitCredentialsMatcher = new RetryLimitCredentialsMatcher();
        retryLimitCredentialsMatcher.setRedisUtils(redisUtils);
        retryLimitCredentialsMatcher.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);
        retryLimitCredentialsMatcher.setHashIterations(2);

        myShiroRealm.setCredentialsMatcher(retryLimitCredentialsMatcher);
        return myShiroRealm;
    }

    @Bean
    public Realm jwtShiroRealm() {
        JwtShiroRealm myShiroRealm = new JwtShiroRealm();
        myShiroRealm.setCredentialsMatcher(new JwtCredentialsMatcher());
        return myShiroRealm;
    }


    /**
     * 关闭session
     */
    @Bean
    public SessionStorageEvaluator sessionStorageEvaluator() {
        DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }

    /**
     * 验证器
     */
    @Bean
    public Authenticator authenticator() {
        return new MyModularRealmAuthenticator();
    }

    /**
     * 设置过滤器链
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {

        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        //设置登陆的url
        factoryBean.setLoginUrl("/api/admin/user/signIn");
        factoryBean.setSecurityManager(securityManager);
        //设置过滤器
        Map<String, Filter> filters = factoryBean.getFilters();
        filters.put("authToken", jwtFilter());
        factoryBean.setFilters(filters);

        // 自定义url规则使用LinkedHashMap有序Map
        Map<String, String> filterMap = new LinkedHashMap<>();
        // 两个url规则都可以匹配同一个url，只执行第一个
        filterMap.put("/api/admin/user/signIn", "anon");
        filterMap.put("/api/admin/**", "authToken");
        factoryBean.setFilterChainDefinitionMap(filterMap);
        return factoryBean;
    }


    /**
     * 自动代理
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    /**
     * 开启注解通知
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
