package cn.hdj.hdjblog.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hdj
 * @version 1.0
 * @date 12/01/2020 15:40
 * @description: 时间线
 */
@Data
public class TimeLineVO implements Serializable {
    /**
     * 年
     */
    private Integer  year;
    /**
     * 文章数量
     */
    private Integer count;

    private List<TimelineMonthVO> months;


}
