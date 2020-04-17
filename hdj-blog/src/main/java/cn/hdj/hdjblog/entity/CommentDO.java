package cn.hdj.hdjblog.entity;

import cn.hdj.hdjblog.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 文章评论表
 * </p>
 *
 * @author hdj
 * @since 2019-08-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_comment")
public class CommentDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 评论人名称
     */
    private String commentName;

    /**
     * 评论人email
     */
    private String commentEmail;

    /**
     * 评论人链接
     */
    private String commentLink;

    /**
     * 评论人头像
     */
    private String commentAvatar;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 状态：0新建，1删除，2屏蔽
     */
    private Integer status;

    /**
     * 点赞数
     */
    private Integer likeNum;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 父评论id
     */
    private Long pId;

    /**
     * 用户UA
     */
    private String userAgent;

    /**
     * ip地址
     */
    private String ipAddress;


}
