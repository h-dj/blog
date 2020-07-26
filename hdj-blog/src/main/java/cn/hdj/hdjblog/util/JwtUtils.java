package cn.hdj.hdjblog.util;


import cn.hdj.hdjblog.constaint.RedisCacheKeys;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * @author hdj
 * @Description: JWT 工具类
 * @Version 1.0
 */
public class JwtUtils {

    private final static String SUBJECT_NAME = "hdj-blog";
    private final static String CLAIM_NAME = "username";


    private JwtUtils() {
    }

    /**
     * @return token中包含的签发时间
     */
    public static Date getIssuedAt(String token) throws JWTDecodeException {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getIssuedAt();
    }

    /**
     * @return token中包含的用户名
     */
    public static String getUsername(String token) throws JWTDecodeException {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("username").asString();
    }

    /**
     * 生成签名,expireTime后过期
     *
     * @param username 用户名
     * @param time     过期时间s
     * @return 加密的token
     */
    public static String sign(String username, String salt, long time) {
        Date date = Date.from(LocalDateTime.now().plusMinutes(time).toInstant(ZoneOffset.ofHours(8)));
        Algorithm algorithm = Algorithm.HMAC256(salt);
        // 附带username信息
        return JWT.create()
                .withSubject(SUBJECT_NAME)
                .withClaim(CLAIM_NAME, username)
                .withExpiresAt(date)
                .withIssuedAt(new Date())
                .sign(algorithm);
    }

    /**
     * token是否过期
     *
     * @return true：过期
     */
    public static boolean isTokenExpired(String token) {
        Date now = Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(8)));
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getExpiresAt().before(now);
    }

    /**
     * 是否应该刷新token
     *
     * @param token
     * @param refreshTime 多久间隔刷新
     * @return
     */
    public static boolean isShouldRefreshToken(String token, long refreshTime) {
        DecodedJWT jwt = JWT.decode(token);
        LocalDateTime expireTime = LocalDateTime.ofInstant(jwt.getExpiresAt().toInstant(), ZoneId.systemDefault());
        return LocalDateTime.now().plusSeconds(refreshTime).isAfter(expireTime);
    }

    /**
     * 生成随机盐,长度32位
     *
     * @return
     */
    public static String generateSalt() {
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        return secureRandom.nextBytes(16).toHex();
    }

    /**
     * 生成token的键
     *
     * @param account
     * @return
     */
    public static String genKey(String account) {
        return String.format("%s%s", RedisCacheKeys.SYSTEM_SETTING_TOKEN_PREFIX, account);
    }

    /**
     * 验证token
     *
     * @param salt
     * @param username
     * @param token
     */
    public static void verify(String salt, String username, String token) {
        Algorithm algorithm = Algorithm.HMAC256(salt);
        JWTVerifier verifier = JWT.require(algorithm)
                .withSubject(SUBJECT_NAME)
                .withClaim(CLAIM_NAME, username)
                .build();
        verifier.verify(token);
    }
}
