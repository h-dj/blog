package cn.hdj.hdjblog.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 友链
 * </p>
 *
 * @author hdj
 * @since 2020-01-14
 */
@Data
@TableName("t_friend_link")
public class FriendLinkDO {

    private static final long serialVersionUID = 1L;

    /**
     * 实体ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 更改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 友链网站标题
     */
    private String title;

    /**
     * 状态:0新建，1审核通过
     */
    private Integer status;

    /**
     * 描述
     */
    private String remark;

    /**
     * 友链
     */
    private String url;

    /**
     * 通知邮箱
     */
    private String email;


}
