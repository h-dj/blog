package cn.hdj.hdjblog.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @author hdj
 * @version 1.0
 * @date 12/01/2020 16:22
 * @description: 时间线归档--月份
 */
@Data
public class TimelineMonthVO {
    private Integer month;
    private Integer count;
    private List<TimelinePostVO> posts;
}
