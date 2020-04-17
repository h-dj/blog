package cn.hdj.hdjblog.service.impl;


import cn.hdj.hdjblog.dao.TagDao;
import cn.hdj.hdjblog.entity.TagArticleDO;
import cn.hdj.hdjblog.entity.TagDO;
import cn.hdj.hdjblog.exception.MyException;
import cn.hdj.hdjblog.service.TagArticleService;
import cn.hdj.hdjblog.service.TagService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author hdj
 * @since 2019-09-21
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagDao, TagDO> implements TagService {


    @Autowired
    private TagArticleService tagArticleService;


    @Override
    public void saveTagLink(List<TagDO> tagList, Long linkId) {
        Assert.notNull(linkId, "Article id must not be null");

        Optional.ofNullable(tagList)
                .ifPresent(tags -> {
                    List<TagArticleDO> tagLinkList = new ArrayList<>(tags.size());
                    tags.forEach(tagDO -> {
                        Long id = tagDO.getId();
                        TagArticleDO tagArticleDO = new TagArticleDO();
                        if (id == null) {
                            baseMapper.insert(tagDO);
                        }
                        tagArticleDO.setTagId(tagDO.getId());
                        tagArticleDO.setArticleId(linkId);
                        tagLinkList.add(tagArticleDO);
                    });
                    tagArticleService.saveBatch(tagLinkList);
                });
    }

    @Override
    public void removeTagLink(List<Long> linkIds) {
        if (CollectionUtil.isNotEmpty(linkIds)) {
            tagArticleService.remove(Wrappers.<TagArticleDO>lambdaQuery()
                    .in(TagArticleDO::getArticleId, linkIds)
            );
        }
    }

    @Override
    public void delete(List<Long> ids) {
        if (ids != null && ids.size() > 0) {
            List<TagArticleDO> list = tagArticleService.list(Wrappers.<TagArticleDO>lambdaQuery()
                    .in(TagArticleDO::getTagId, ids)
            );
            if (list != null && list.size() > 0) {
                throw new MyException("所选标签有有关联文章，无法删除！");
            }
            //删除标签
            baseMapper.deleteBatchIds(ids);
        }
    }

    @Override
    public List<TagDO> getTagListByArticleId(Long articleId) {
        return baseMapper.getTagListByArticleId(articleId);
    }

    
    @Override
    public List<Map<String, Integer>> groupCount() {
        return baseMapper.groupCount();
    }
}
