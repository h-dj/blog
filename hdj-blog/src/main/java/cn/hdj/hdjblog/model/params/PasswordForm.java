package cn.hdj.hdjblog.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author hdj
 * @date 2019/7/16 22:23
 * @Version 1.0
 * @Description:
 */
@Data
@ApiModel(value = "PasswordForm", description = "更改密码表单")
public class PasswordForm {

    @NotEmpty(message = "密码不能为空")
    @ApiModelProperty(value = "旧密码")
    private String password;

    @NotEmpty(message = "密码不能为空")
    @ApiModelProperty(value = "新密码")
    private String newPassword;

}
