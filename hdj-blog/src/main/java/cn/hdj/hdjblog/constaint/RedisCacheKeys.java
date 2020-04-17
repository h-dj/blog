package cn.hdj.hdjblog.constaint;

/**
 * @author hdj
 * @Description:
 * @date 10/5/19
 */
public interface RedisCacheKeys {

    /**
     * token 键的前缀
     */
    String SYSTEM_SETTING_TOKEN_PREFIX = "user:token:";

    /**
     * 用户登录次数缓存key前缀
     */
    String PWD_RETRY_KEY_PREFIX = "user:login:retry:";
    /**
     * 用户是否所在锁住
     */
    String IS_LOCK = "is_locked";
    /**
     * 验证码key
     */
    String REDIS_CAPTCHA = "captcha:%s";

}
