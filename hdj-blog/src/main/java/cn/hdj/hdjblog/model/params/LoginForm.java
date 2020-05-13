package cn.hdj.hdjblog.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author hdj
 * @Description: 登陆Form
 * @Version 1.0
 */
@Data
@ApiModel(value = "LoginForm", description = "登陆")
public class LoginForm {
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "账户")
    private String account;

    @Override
    public String toString() {
        return "LoginForm{" +
                "account='" + account + '\'' +
                '}';
    }
}
