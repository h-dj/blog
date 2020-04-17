package cn.hdj.hdjblog.entity;

import cn.hdj.hdjblog.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统菜单
 * </p>
 *
 * @author hdj
 * @since 2019-08-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_menu")
public class MenuDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 资源名称
     */
    private String menuName;

    /**
     * 菜单类型：0目录，1菜单，2按钮
     */
    private Integer type;

    /**
     * parentId
     */
    private Long parentId;

    /**
     * 父菜单名称
     */
    private String parentName;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 链接
     */
    private String url;

    /**
     * 是否删除
     */
    private Boolean deleted;

    /**
     * 图标
     */
    private String icon;

    /**
     * 组件名
     */
    private String component;

    /**
     * 是否隐藏
     */
    private Boolean hidden;
}
