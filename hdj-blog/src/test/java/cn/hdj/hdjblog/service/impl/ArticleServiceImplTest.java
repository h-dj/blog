package cn.hdj.hdjblog.service.impl;

import cn.hdj.hdjblog.HdjBlogApplicationTests;
import cn.hdj.hdjblog.entity.ArticleDO;
import cn.hdj.hdjblog.service.ArticleService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author hdj
 */
@SuppressWarnings({"unchecked"})
public class ArticleServiceImplTest extends HdjBlogApplicationTests {

    @Autowired
    private ArticleService articleService;


    @Test
    public void recommandsTest() {
        articleService.list(
                Wrappers.<ArticleDO>lambdaQuery()
                        .select(ArticleDO::getId,
                                ArticleDO::getTitle,
                                ArticleDO::getAuthorName,
                                ArticleDO::getReadNum,
                                ArticleDO::getLikeNum,
                                ArticleDO::getCommentNum,
                                ArticleDO::getRecommend,
                                ArticleDO::getTags,
                                ArticleDO::getStatus,
                                ArticleDO::getPublishTime
                        )
                        .eq(ArticleDO::getRecommend, true)
                        .ne(ArticleDO::getStatus, 2)
                        .orderByDesc(ArticleDO::getUpdateTime, ArticleDO::getLikeNum, ArticleDO::getReadNum)
                        .last("limit 0,10")
        );

    }

}