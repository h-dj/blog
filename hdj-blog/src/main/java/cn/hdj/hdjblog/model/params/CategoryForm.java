package cn.hdj.hdjblog.model.params;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author hdj
 * @Description:
 * @date 9/25/19
 */
@Data
public class CategoryForm {

    /**
     * 分类id
     */
    private Long id;
    /**
     * 分类名称
     */
    @NotBlank(message = "分类名称不能为空")
    private String categoryName;

    /**
     * 分类描述
     */
    private String description;


}
