package cn.hdj.hdjblog.model.params;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author hdj
 * @Description:
 * @date 9/24/19
 */
@Data
public class TagForm {

    /**
     * 标签id
     */
    private Long id;
    /**
     * 标签名称
     */
    @NotBlank(message = "标签名不能为空!")
    String tagName;
}
