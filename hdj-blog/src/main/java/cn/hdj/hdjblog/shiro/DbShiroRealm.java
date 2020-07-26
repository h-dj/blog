package cn.hdj.hdjblog.shiro;


import cn.hdj.hdjblog.constaint.RedisCacheKeys;
import cn.hdj.hdjblog.entity.UserDO;
import cn.hdj.hdjblog.model.dto.UserDetailDTO;
import cn.hdj.hdjblog.shiro.service.ShiroService;
import cn.hdj.hdjblog.util.RedisUtils;
import cn.hutool.core.bean.BeanUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author hdj
 * @Description: 用户身份验证
 * @Version 1.0
 */
@Log4j2
public class DbShiroRealm extends AuthorizingRealm {


    @Autowired
    private ShiroService shiroService;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof DbToken;
    }

    /**
     * 登陆验证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        DbToken dbToken = (DbToken) token;
        String username = dbToken.getUser().getAccount();

        String key = String.format("%s%s", RedisCacheKeys.PWD_RETRY_KEY_PREFIX, username);
        //判断账户是否锁定
        if (redisUtils.hHasKey(key, RedisCacheKeys.IS_LOCK)) {
            throw new LockedAccountException();
        }

        log.debug("== 登录验证 ==");
        UserDO user = shiroService.findByUserName(username);
        if (user == null || user.getDeleted()) {
            throw new UnknownAccountException();
        }
        // 账户被锁定或禁用
        if (user.getLocked() || !user.getEnable()) {
            throw new LockedAccountException();
        }
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        BeanUtil.copyProperties(user, userDetailDTO);
        return new SimpleAuthenticationInfo(userDetailDTO, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), "dbRealm");
    }


    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }
}
