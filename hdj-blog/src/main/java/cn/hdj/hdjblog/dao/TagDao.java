package cn.hdj.hdjblog.dao;

import cn.hdj.hdjblog.entity.TagDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author hdj
 * @since 2019-09-21
 */
public interface TagDao  extends BaseMapper<TagDO> {

    /**
     * 通过文章ID 获取标签列表
     * @param articleId
     * @return
     */
    List<TagDO> getTagListByArticleId(@Param("articleId") Long articleId);

    /**
     * 标签统计
     * @return
     */
    List<Map<String, Integer>> groupCount();

}
