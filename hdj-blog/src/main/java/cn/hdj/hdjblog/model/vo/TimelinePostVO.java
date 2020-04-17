package cn.hdj.hdjblog.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author hdj
 * @version 1.0
 * @date 12/01/2020 16:22
 * @description: 时间线归档--文章
 */
@Data
public class TimelinePostVO {
    private Long id;
    private String slug;
    private String title;
    private Date createTime;
}
