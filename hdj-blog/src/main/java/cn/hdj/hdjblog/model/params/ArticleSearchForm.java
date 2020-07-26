package cn.hdj.hdjblog.model.params;

import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hdj
 * @Description: 文章搜索表单
 * @date 8/13/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class ArticleSearchForm extends BaseForm {

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章分类")
    private Long categoryId;

    @ApiModelProperty(value = "是否推荐")
    private Boolean recommend;

    @JsonIgnore
    public List<OrderItem> getOrderList() {
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(OrderItem.desc("top"));
        if (BooleanUtil.isTrue(recommend)) {
            orderItems.add(OrderItem.desc("recommend"));
        } else {
            orderItems.add(OrderItem.desc("update_time"));
            orderItems.add(OrderItem.desc("like_num"));
        }
        return orderItems;
    }

}
