package cn.hdj.hdjblog.entity;

import cn.hdj.hdjblog.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 分类表
 * </p>
 *
 * @author hdj
 * @since 2019-09-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_category")
public class CategoryDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 分类名称
     */
    private String categoryName;


    /**
     * 分类描述
     */
    private String description;


}
