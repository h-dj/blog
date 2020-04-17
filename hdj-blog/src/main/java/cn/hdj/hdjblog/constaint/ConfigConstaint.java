package cn.hdj.hdjblog.constaint;

/**
 * @author hdj
 * @Description: 系统常量
 * @Version 1.0
 */
public class ConfigConstaint {

    /**
     * token 过期时间
     */
    public final static int SYSTEM_TOKEN_TIMEOUT = 36000;


    /**
     * token header键
     */
    public static final String SYSTEM_TOKEN_HEADER = "x-auth-token";

    /**
     * 本机地址
     */
    public static final String REGION = "127.0.0.1";



    /**
     * 超级管理员账号id
     */
    public static final Long SUPER_ADMIN = 0L;
}
