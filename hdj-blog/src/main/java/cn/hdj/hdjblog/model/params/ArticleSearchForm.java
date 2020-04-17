package cn.hdj.hdjblog.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hdj
 * @Description: 文章搜索表单
 * @date 8/13/19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel
public class ArticleSearchForm extends BaseForm {

    @ApiModelProperty(value = "文章标题")
    private String title;
}
