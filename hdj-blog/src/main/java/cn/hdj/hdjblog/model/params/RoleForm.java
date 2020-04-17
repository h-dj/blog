package cn.hdj.hdjblog.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * @author hdj
 * @date 2019/7/21 17:33
 * @Version 1.0
 * @Description: 角色form
 */
@Data
@ApiModel(value = "RoleForm", description = "角色表单")
public class RoleForm {

    /**
     * 角色id
     */
    private Long id;
    /**
     * 角色名称
     */
    @NotEmpty
    private String roleName;

    /**
     * 角色编码
     */
    @NotEmpty
    private String roleCode;
    /**
     * 描述
     */
    private String remark;

    /**
     * 状态：1启用，0禁用
     */
    private Boolean enable;

    /**
     * 菜单Id
     */
    @ApiModelProperty("菜单Id")
    private Set<Long> menuIds;
}
