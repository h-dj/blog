package cn.hdj.hdjblog.model.params;

import lombok.Data;

/**
 * @author hdj
 * @version 1.0
 * @date 2019/9/8 16:59
 * @description:
 */
@Data
public class MenuForm {

    /**
     * 菜单名称
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
