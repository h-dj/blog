package cn.hdj.hdjblog.model.params;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hdj
 * @version 1.0
 * @date 2019/9/7 23:40
 * @description: 菜单搜索表单
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuSearchForm extends BaseForm {

    /**
     * 父菜单Id
     */
    public Long parentId;
}
