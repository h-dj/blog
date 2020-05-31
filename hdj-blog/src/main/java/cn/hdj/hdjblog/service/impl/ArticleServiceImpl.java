package cn.hdj.hdjblog.service.impl;


import cn.hdj.hdjblog.dao.ArticleDao;
import cn.hdj.hdjblog.entity.ArticleDO;
import cn.hdj.hdjblog.exception.MyException;
import cn.hdj.hdjblog.model.params.ArticleForm;
import cn.hdj.hdjblog.model.params.ArticleSearchForm;
import cn.hdj.hdjblog.model.vo.*;
import cn.hdj.hdjblog.service.ArticleService;
import cn.hdj.hdjblog.service.CategoryService;
import cn.hdj.hdjblog.service.TagService;
import cn.hdj.hdjblog.util.MyWebUtils;
import cn.hdj.hdjblog.util.RedisUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 文章信息表 服务实现类
 * </p>
 *
 * @author hdj
 * @since 2019-06-07
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, ArticleDO> implements ArticleService {

    @Autowired
    private TagService tagService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisUtils redisUtils;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveArticle(ArticleForm form) {
        long id = IdUtil.getSnowflake(0, 0).nextId();
        if (StrUtil.isNotEmpty(form.getSlug())) {
            int count = this.count(Wrappers.<ArticleDO>lambdaQuery()
                    .eq(ArticleDO::getSlug, form.getSlug()));

            if (count > 0) {
                throw new MyException("该slug 已存在！");
            }
        } else {
            form.setSlug(String.valueOf(id));
        }
        form.setId(id);
        ArticleDO articleDO = new ArticleDO();
        BeanUtil.copyProperties(form, articleDO);
        Optional.ofNullable(form.getTagList())
                .ifPresent(list -> {
                    String tags = list.stream()
                            .map(v -> v.getTagName())
                            .collect(Collectors.joining(","));
                    articleDO.setTags(tags);
                });
        articleDO.setAuthorId(MyWebUtils.getCurrentUserId());
        articleDO.setAuthorName(MyWebUtils.getCurrentUserName());
        if (articleDO.getStatus() == 1) {
            articleDO.setPublishTime(new Date());
        }
        //保存文章
        baseMapper.insert(articleDO);
        //保存标签
        tagService.saveTagLink(form.getTagList(), articleDO.getId());
        form.setId(articleDO.getId());
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void editArticle(Long id, ArticleForm form) {

        int count = this.count(Wrappers.<ArticleDO>lambdaQuery()
                .eq(ArticleDO::getSlug, form.getSlug())
                .ne(ArticleDO::getId, id)
        );
        if (count > 0) {
            throw new MyException("该slug 已存在！");
        }

        ArticleDO articleDO = new ArticleDO();
        BeanUtil.copyProperties(form, articleDO);
        articleDO.setId(id);
        Optional.ofNullable(form.getTagList())
                .ifPresent(list -> {
                    String tags = list.stream().map(v -> v.getTagName())
                            .collect(Collectors.joining(","));
                    articleDO.setTags(tags);
                });
        if (articleDO.getStatus() == 1 && articleDO.getPublishTime() == null) {
            articleDO.setPublishTime(new Date());
        }
        baseMapper.updateById(articleDO);
        //删除标签关联
        tagService.removeTagLink(Arrays.asList(id));
        //保存标签
        tagService.saveTagLink(form.getTagList(), articleDO.getId());

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(List<Long> articleIds) {
        if (CollectionUtil.isNotEmpty(articleIds)) {
            //删除文章管理的标签
            tagService.removeTagLink(articleIds);

            this.update(Wrappers.<ArticleDO>lambdaUpdate()
                    .set(ArticleDO::getStatus, 2)
                    .in(ArticleDO::getId, articleIds)
            );
        }
    }

    @Override
    public ArticleForm detail(String slug) {
        ArticleDO articleDO = baseMapper.selectOne(Wrappers.<ArticleDO>lambdaQuery()
                .eq(ArticleDO::getSlug, slug)
                .or()
                .eq(ArticleDO::getId, slug)
        );
        ArticleForm articleForm = new ArticleForm();
        BeanUtil.copyProperties(articleDO, articleForm);
        articleForm.setTagList(tagService.getTagListByArticleId(articleDO.getId()));
        articleForm.setCategoryName(categoryService.getById(articleDO.getCategoryId()).getCategoryName());
        return articleForm;
    }

    @Override
    public List<TimeLineVO> archive(String tag) {
        //查出按时间统计文章
        List<TimeLineVO> timeLineList = baseMapper.archiveTimeLine(tag);
        for (TimeLineVO timeline : timeLineList) {
            List<TimelineMonthVO> timelineMonthList = new ArrayList<>();
            for (int i = Calendar.UNDECIMBER; i > 0; i--) {
                List<TimelinePostVO> postList = baseMapper.listTimelinePost(timeline.getYear(), i, tag);
                if (CollectionUtil.isEmpty(postList)) {
                    continue;
                }
                TimelineMonthVO month = new TimelineMonthVO();
                month.setCount(postList.size());
                month.setMonth(i);
                month.setPosts(postList);
                timelineMonthList.add(month);
            }
            timeline.setMonths(timelineMonthList);
        }
        return timeLineList;
    }

    @Override
    public ResultVO articleList(ArticleSearchForm form) {
        IPage<ArticleDO> page = this.page(
                form.getIPage(),
                Wrappers.<ArticleDO>lambdaQuery()
                        .select(ArticleDO::getId,
                                ArticleDO::getTitle,
                                ArticleDO::getAuthorName,
                                ArticleDO::getReadNum,
                                ArticleDO::getRecommend,
                                ArticleDO::getTags,
                                ArticleDO::getPublishTime,
                                ArticleDO::getDescription,
                                ArticleDO::getCover,
                                ArticleDO::getSlug
                        )
                        .eq(ArticleDO::getStatus, 1)
        );

        return ResultVO.successJson(PageVO.build(page));
    }

    @Override
    public void publishArticle(Long articleId) {
        ArticleDO articleDO = this.baseMapper.selectById(articleId);
        if (articleDO == null || articleDO.getStatus().equals(2)) {
            throw new MyException("文章不存在!");
        }
        if (articleDO.getStatus().equals(1)) {
            throw new MyException("文章已发布!");
        }
        articleDO.setStatus(1);
        this.baseMapper.updateById(articleDO);
    }

}
