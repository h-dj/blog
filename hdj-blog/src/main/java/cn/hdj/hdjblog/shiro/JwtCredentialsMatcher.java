package cn.hdj.hdjblog.shiro;


import cn.hdj.hdjblog.model.dto.UserDetailDTO;
import cn.hdj.hdjblog.util.JwtUtils;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

/**
 * @author hdj
 * @Description: JWT凭证匹配
 * @Version 1.0
 */
@Slf4j
public class JwtCredentialsMatcher implements CredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) throws JWTVerificationException {
        String token = (String) authenticationToken.getCredentials();
        Object stored = authenticationInfo.getCredentials();
        String salt = stored.toString();
        UserDetailDTO user = (UserDetailDTO) authenticationInfo.getPrincipals().getPrimaryPrincipal();
        JwtUtils.verify(salt, user.getEmail(), token);
        return true;

    }

}
