package cn.hdj.hdjblog.dao;

import cn.hdj.hdjblog.entity.ArticleDO;
import cn.hdj.hdjblog.model.vo.TimeLineVO;
import cn.hdj.hdjblog.model.vo.TimelinePostVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 文章信息表 Mapper 接口
 * </p>
 *
 * @author hdj
 * @since 2019-06-07
 */
public interface ArticleDao extends BaseMapper<ArticleDO> {

    /**
     * 文章归档--按时间统计
     *
     * @return
     */
    List<TimeLineVO> archiveTimeLine(@Param("tag") String tag);

    /**
     * 按照年月查找文章
     *
     * @param year
     * @param month
     * @return
     */
    List<TimelinePostVO> listTimelinePost(@Param("year") Integer year, @Param("month") Integer month,@Param("tag") String tag);
}
