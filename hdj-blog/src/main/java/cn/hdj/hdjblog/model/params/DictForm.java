package cn.hdj.hdjblog.model.params;

import cn.hdj.hdjblog.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author hdj
 * @Description: 字典参数
 * @date 8/13/19
 */
@Data
public class DictForm {

    @NotNull(groups = UpdateGroup.class)
    private Long id;
    /**
     * 父类编号
     */
    @NotNull
    private Long pId;

    /**
     * 字典名称
     */
    @NotNull
    private String dictName;

    /**
     * 字典值
     */
    @NotNull
    private String dictValue;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态，0正常，1禁用
     */
    private Boolean enable;
}
