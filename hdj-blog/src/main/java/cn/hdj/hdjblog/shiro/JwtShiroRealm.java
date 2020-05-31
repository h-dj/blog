package cn.hdj.hdjblog.shiro;


import cn.hdj.hdjblog.constaint.enums.ResponseCodeEnum;
import cn.hdj.hdjblog.exception.MyException;
import cn.hdj.hdjblog.model.dto.UserDetailDTO;
import cn.hdj.hdjblog.shiro.service.ShiroService;
import cn.hdj.hdjblog.util.JwtUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * @author hdj
 * @ClassName: JwtShiroRealm
 * @Description: JWT身份认证
 * @date 2019-03-29 17:23
 * @Version 1.0
 */
@Log4j2
public class JwtShiroRealm extends AuthorizingRealm {

    @Autowired
    protected ShiroService shiroService;

    public JwtShiroRealm() {
        super(null, new JwtCredentialsMatcher());
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 认证信息.(身份验证) : Authentication 是用来验证用户身份
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
        JwtToken jwtToken = (JwtToken) authcToken;
        String token = jwtToken.getToken();
        log.debug("== JWT验证 ==");
        UserDetailDTO user = shiroService.getJwtTokenInfo(JwtUtils.getUsername(token));
        if (user == null) {
            // token过期，请重新登录
            throw new MyException(ResponseCodeEnum.TOKEN_GENERATOR_EXPIRE);
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getTokenSalt(), "jwtRealm");
        return authenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        UserDetailDTO account = (UserDetailDTO) principals.getPrimaryPrincipal();
        Set<String> permissions = shiroService.queryAllPerms(account.getId());
        Set<String> rokeKeys = shiroService.queryAllRoles(account.getId());
        account.setPermissions(permissions);
        simpleAuthorizationInfo.setRoles(rokeKeys);
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }
}
