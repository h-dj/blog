package cn.hdj.hdjblog.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hdj
 * @Description:　验证器
 * @date 9/25/19
 */
public class MyModularRealmAuthenticator extends ModularRealmAuthenticator {

    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {

        assertRealmsConfigured();
        Collection<Realm> realms = getRealms();
        List<Realm> realmList = realms.stream()
                .filter(v -> v.supports(authenticationToken))
                .collect(Collectors.toList());
        if (realmList.size() == 1) {
            return doSingleRealmAuthentication(realmList.iterator().next(), authenticationToken);
        } else {
            return doMultiRealmAuthentication(realmList, authenticationToken);
        }
    }
}
