package cn.hdj.hdjblog.shiro;


import cn.hdj.hdjblog.constaint.RedisCacheKeys;
import cn.hdj.hdjblog.util.RedisUtils;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

/**
 * @author hdj
 * @Description: 登陆重试， 默认5次后锁定30分钟
 * @Version 1.0
 */
public class RetryLimitCredentialsMatcher extends HashedCredentialsMatcher {


    /**
     * 登陆重试key
     */
    private final static String RETRY_COUNT = "count";
    /**
     * 最大重试次数
     */
    private final static double RETRY_TIME = 5;
    /**
     * 锁住时间：30分钟
     */
    private final static long LOCK_TIME = 60 * 30;


    private RedisUtils redisUtils;

    public void setRedisUtils(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) throws JWTVerificationException {
        DbToken dbToken = (DbToken) authenticationToken;
        String username = dbToken.getUser().getAccount();


        String key = String.format("%s%s", RedisCacheKeys.PWD_RETRY_KEY_PREFIX, username);
        //判断密码重试次数
        if (!redisUtils.hHasKey(key, RETRY_COUNT)) {
            redisUtils.hincr(key, RETRY_COUNT, 0);
        }
        double retry = redisUtils.hincr(key, RETRY_COUNT, 1);
        if (retry > RETRY_TIME) {
            redisUtils.hput(key, RedisCacheKeys.IS_LOCK, true, LOCK_TIME);
            throw new LockedAccountException();
        }

        boolean match = super.doCredentialsMatch(authenticationToken, authenticationInfo);
        if (match) {
            //删除
            redisUtils.expire(key, 1);
        }
        return match;
    }

}
