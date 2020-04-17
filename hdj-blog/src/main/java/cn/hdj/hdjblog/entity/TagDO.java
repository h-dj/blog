package cn.hdj.hdjblog.entity;

import cn.hdj.hdjblog.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 标签表
 * </p>
 *
 * @author hdj
 * @since 2019-08-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_tag")
public class TagDO extends BaseEntity {

    private static final long serialVersionUID = -5255126140166632995L;
    /**
     * 标签名称
     */
    private String tagName;

}
