package cn.hdj.hdjblog.service;

import cn.hdj.hdjblog.entity.TagDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author hdj
 * @since 2019-09-21
 */
public interface TagService extends IService<TagDO> {

    /**
     * 保存标签关联
     *
     * @param tagList
     * @param linkId
     */
    void saveTagLink(List<TagDO> tagList, Long linkId);

    /**
     * 删除标签关联
     *
     * @param linkIds
     */
    void removeTagLink(List<Long> linkIds);


    /**
     * 删除标签
     *
     * @param ids
     */
    void delete(List<Long> ids);

    /**
     * 通过文章ID 获取标签列表
     *
     * @param articleId
     * @return
     */
    List<TagDO> getTagListByArticleId(Long articleId);

    /**
     * 按标签名称分组统计
     *
     * @return
     */
    List<Map<String, Integer>> groupCount();

}
