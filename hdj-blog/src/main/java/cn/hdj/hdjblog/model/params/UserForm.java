package cn.hdj.hdjblog.model.params;

import cn.hdj.hdjblog.validator.group.AddGroup;
import cn.hdj.hdjblog.validator.group.UpdateGroup;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Set;

/**
 * @author hdj
 * @date 2019/7/21 16:52
 * @Version 1.0
 * @Description: 用于注册，添加，修改用户的表单
 */
@Data
@ApiModel(value = "UserForm", description = "用户表单")
public class UserForm implements Serializable {


    private static final long serialVersionUID = -4586737032689638183L;
    /**
     * 用户名称
     */
    @Length(max = 10, message = "用户名长度不能超过10个字符")
    @NotBlank(message = "用户名不能为空")
    private String userName;
    /**
     * 账号
     */
    @NotBlank(message = "账户不能为空", groups = {AddGroup.class})
    private String email;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空", groups = {AddGroup.class})
    @Null(groups = {UpdateGroup.class})
    private String password;

    /**
     * 头像
     */
    private String avatar;
    /**
     * 启用状态
     */
    private Boolean enable;

    /**
     * 备注
     */
    private String remark;
    /**
     * 角色IDs
     */
    private Set<Long> roleIds;
}
