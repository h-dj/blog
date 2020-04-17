package cn.hdj.hdjblog.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author hdj
 * @date 2019/7/25 22:30
 * @Version 1.0
 * @Description: 用户详情
 */
@Data
public class UserDetailDTO implements Serializable {

    private static final long serialVersionUID = -2826126367541073780L;
    /**
     * 实体ID
     */
    private Long id;
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
    @JsonIgnore
    private String password;

    /**
     * 加密盐
     */
    @JsonIgnore
    private String salt;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 状态：0：正常，1：禁用
     */
    private Boolean enable;
    /**
     * 删除状态：0：未删除，1：已删除
     */
    private Boolean deleted;
    /**
     * 锁定状态：0：未锁定，1：已锁定
     */
    private Boolean locked;

    /**
     * 备注
     */
    private String remark;

    /**
     * 登陆时间
     */
    private Date loginTime;


    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更改时间
     */
    private Date updateTime;
    /**
     * token盐
     */
    @JsonIgnore
    private String tokenSalt;
    /**
     * 角色
     */
    private Set<Long> roleIds;
    /**
     * 角色名称
     */
    private Set<String> roleNames;
    /**
     * 权限
     */
    private Set<String> permissions;
}
