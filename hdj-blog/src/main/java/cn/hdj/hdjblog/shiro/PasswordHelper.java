package cn.hdj.hdjblog.shiro;

import cn.hdj.hdjblog.entity.UserDO;
import cn.hdj.hdjblog.util.JwtUtils;
import lombok.NonNull;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author hdj
 * @Description: 密码加密
 * @Version 1.0
 */
@Component
public class PasswordHelper {


    public void encryptPassword(@NonNull UserDO user) {
        user.setSalt(JwtUtils.generateSalt());
        String newPassword = encryptPassword(user.getPassword(), user.getSalt());
        user.setPassword(newPassword);
    }

    public String encryptPassword(String pwd, String salt) {
        Objects.requireNonNull(pwd);
        return new SimpleHash(
                Sha256Hash.ALGORITHM_NAME,
                pwd,
                ByteSource.Util.bytes(salt),
                2
        ).toHex();
    }
}
