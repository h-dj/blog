package cn.hdj.hdjblog.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hdj
 * @Version 1.0
 * @Description: 用户搜索参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserSearchForm", description = "用户搜索参数")
public class UserSearchForm extends BaseForm {

    /**
     * 搜索字段：key
     */
    @ApiModelProperty(value = "邮箱或用户名")
    private String key;



}
