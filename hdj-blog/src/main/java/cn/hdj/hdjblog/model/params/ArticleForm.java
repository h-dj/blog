package cn.hdj.hdjblog.model.params;

import cn.hdj.hdjblog.entity.TagDO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author hdj
 * @Description:
 * @date 8/13/19
 */
@Data
public class ArticleForm implements Serializable {

    private Long id;

    /**
     * 文章标题
     */
    @NotBlank(message = "标题不能未空")
    private String title;

    /**
     * 0:普通文章，1：关于页
     */
    private Integer type;
    /**
     * 概要
     */
    private String description;

    /**
     * 正文（markdown）
     */
    private String content;
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
     * 是否允许订阅：0允许，1不允许
     */
    private Boolean allowFeed;

    /**
     * 是否推荐
     */
    private Boolean recommend;

    /**
     * 是否置顶
     */
    private Boolean top;

    /**
     * 标签
     */
    private List<TagDO> tagList;

    /**
     * 分类
     */
    private Long categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 标签
     */
    private String tags;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 发布时间
     */
    private Date publishTime;

    /**
     * 自定义路径
     */
    private String slug;

    /**
     * 文章封面
     */
    private String cover;

    /**
     * 阅读量
     */
    private Integer readNum;

    /**
     * 评论数
     */
    private Integer commentNum;

    /**
     * 点赞数
     */
    private Integer likeNum;

}
