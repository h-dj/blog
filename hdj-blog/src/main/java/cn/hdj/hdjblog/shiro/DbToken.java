package cn.hdj.hdjblog.shiro;

import cn.hdj.hdjblog.model.params.LoginForm;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author hdj
 * @Description: 用户账户密码验证Token
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DbToken extends UsernamePasswordToken {
    private LoginForm user;

    public DbToken(LoginForm user) {
        super(user.getAccount(), user.getPassword() != null ? user.getPassword().toCharArray() : null);
        this.user = user;
    }

}
