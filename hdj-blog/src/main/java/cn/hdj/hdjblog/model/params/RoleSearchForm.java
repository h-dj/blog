package cn.hdj.hdjblog.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hdj
 * @date 2019/7/21 17:43
 * @Version 1.0
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "RoleSearchForm", description = "角色搜索表单")
public class RoleSearchForm extends BaseForm {
    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    private String roleName;


}
