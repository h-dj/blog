package cn.hdj.hdjblog.entity;

import cn.hdj.hdjblog.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 系统用户
 * </p>
 *
 * @author hdj
 * @since 2019-08-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_user")
public class UserDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 加密盐
     */
    private String salt;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 状态：1：正常，0：禁用
     */
    private Boolean enable;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否删除
     */
    private Boolean deleted;

    /**
     * 是否锁定
     */
    @TableField(value = "is_locked")
    private Boolean locked;

    /**
     * 登陆时间
     */
    private Date loginTime;


}
