package cn.hdj.hdjblog.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author hdj
 * @version 1.0
 * @date 19/01/2020 21:50
 * @description:
 */
@Data
public class ArticleDTO implements Serializable {

    /**
     * 实体ID
     */
    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更改时间
     */
    private Date updateTime;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 概要
     */
    private String description;

    /**
     * 正文（markdown）
     */
    private String content;

    /**
     * 标签
     */
    private String tags;

    /**
     * 作者
     */
    private Long authorId;

    /**
     * 阅读量
     */
    private Integer readNum;

    /**
     * 评论数
     */
    private Integer commentNum;

    /**
     * 作者名称
     */
    private String authorName;

    /**
     * 状态，0：新建，1：发布，2：删除
     */
    private Integer status;

    /**
     * 是否允许评论：0：不允许，1允许
     */
    private Boolean allowComment;

    /**
     * 是否允许订阅：0不允许，不允许
     */
    private Boolean allowFeed;

    /**
     * 点赞数
     */
    private Integer likeNum;

    /**
     * 发布时间
     */
    private Date publishTime;

    /**
     * 0:普通文章，1：关于页
     */
    private Integer type;

    /**
     * 文章路径
     */
    private String slug;

    /**
     * 是否推荐
     */
    private Boolean recommend;

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 文章封面
     */
    private String cover;
}
