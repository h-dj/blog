package cn.hdj.hdjblog.model.params;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author hdj
 * @date 2019/7/21 11:55
 * @Version 1.0
 * @Description:
 */
@Data
public class BaseForm implements Serializable {
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private final static String ASC = "ascending";

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private final static String DESC = "descending";
    /**
     * 当前页
     */
    protected Integer page;
    /**
     * 页数
     */
    protected Integer pageSize;
    /**
     * 排序类型
     */
    protected String sortType;
    /**
     * 排序字段
     */
    protected String sort;


    @JsonIgnore
    public <T> IPage<T> getIPage() {
        if (page == null || page == 0) {
            page = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        Page<T> page = new Page<>(this.page, pageSize);

        Optional.ofNullable(sort)
                .ifPresent(strings -> {
                    if (StrUtil.equalsIgnoreCase(DESC, sortType)) {
                        page.addOrder(OrderItem.desc(sort));
                    } else if (StrUtil.equalsIgnoreCase(ASC, sortType)) {
                        page.addOrder(OrderItem.asc(sort));
                    } else {
                        page.addOrder(OrderItem.desc("update_time"));
                    }
                });
        return page;
    }
}
