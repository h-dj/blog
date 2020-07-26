package cn.hdj.hdjblog.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hdj
 * @version 1.0
 * @date 14/01/2020 22:26
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "友链搜索表单")
public class FriendLinkSearchForm extends BaseForm {

    @ApiModelProperty(value = "状态:0新建，1审核通过，2不通过")
    private Integer status;


}
