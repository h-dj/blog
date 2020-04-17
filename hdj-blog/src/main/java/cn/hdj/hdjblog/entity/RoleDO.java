package cn.hdj.hdjblog.entity;

import cn.hdj.hdjblog.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统角色
 * </p>
 *
 * @author hdj
 * @since 2019-08-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_role")
public class RoleDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编码
     */
    private String roleCode;
    /**
     * 描述
     */
    private String remark;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 是否删除
     */
    private Boolean deleted;
}
