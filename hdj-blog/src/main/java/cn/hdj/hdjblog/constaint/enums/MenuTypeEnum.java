package cn.hdj.hdjblog.constaint.enums;

import lombok.Getter;

/**
 * @author hdj
 * @date 2019/7/27 11:26
 * @Version 1.0
 * @Description: 菜单类型枚举
 */
@Getter
public enum MenuTypeEnum {

    /**
     * 目录
     */
    CATEGORY(0),
    /**
     * 菜单
     */
    MENU(1),
    /**
     * 按钮
     */
    BUTTON(2);
    private int type;

    MenuTypeEnum(int type) {
        this.type = type;
    }
}
