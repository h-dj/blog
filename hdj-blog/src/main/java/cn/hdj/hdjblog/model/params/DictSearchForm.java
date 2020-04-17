package cn.hdj.hdjblog.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hdj
 * @Description: 字典搜索参数
 * @date 8/13/19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "DictSearchForm", description = "字典搜索参数")
public class DictSearchForm extends BaseForm {

    /**
     * 字典名称
     */
    @ApiModelProperty(value = "字典名称")
    private String dictName;
}
