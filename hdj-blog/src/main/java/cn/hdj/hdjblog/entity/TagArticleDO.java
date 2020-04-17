package cn.hdj.hdjblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 文章标签表
 * </p>
 *
 * @author hdj
 * @since 2019-10-06
 */
@Data
@TableName("t_tag_article")
public class TagArticleDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 实体ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标签id
     */
    private Long tagId;

    /**
     * 文章id
     */
    private Long articleId;


}
