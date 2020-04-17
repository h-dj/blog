package cn.hdj.hdjblog.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author hdj
 * @version 1.0
 * @date 14/01/2020 22:44
 * @description:
 */
@Data
@ApiModel(value = "FriendLinkForm", description = "友链表单")
public class FriendLinkForm {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "状态:0新建，1审核通过，2不通过")
    private Integer status;

    @ApiModelProperty(value = "描述")
    private String remark;

    @NotEmpty
    @ApiModelProperty(value = "友链")
    private String url;

    @ApiModelProperty(value = "通知邮箱")
    private String email;

    @ApiModelProperty(value = "昵称")
    private String title;
}
