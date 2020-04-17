package cn.hdj.hdjblog.controller.front;

import cn.hdj.hdjblog.aspect.annotation.SysLog;
import cn.hdj.hdjblog.constaint.RedisCacheNames;
import cn.hdj.hdjblog.elasticsearch.ArticleRepository;
import cn.hdj.hdjblog.entity.ArticleDO;
import cn.hdj.hdjblog.model.dto.ArticleDTO;
import cn.hdj.hdjblog.model.params.BaseForm;
import cn.hdj.hdjblog.model.vo.PageVO;
import cn.hdj.hdjblog.model.vo.ResultVO;
import cn.hdj.hdjblog.service.ArticleService;
import cn.hdj.hdjblog.service.TagService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author hdj
 * @Description:　前台博客文章控制器
 * @date 9/25/19
 */
@Api(tags = "(前台)文章")
@RestController
@RequestMapping("/api/articles")
public class ArticleFrontController {

    /**
     * 发布状态
     */
    private final static int STATUS_PUBLISH = 1;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleRepository articleRepository;


    /**
     * 搜索标题，描述，内容
     *
     * @param keyword
     * @return
     */
    @GetMapping("/search")
    public ResultVO search(@RequestParam("keyword") String keyword,
                           @RequestParam(value = "page", defaultValue = "1") Integer page,
                           @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) throws IOException {
        return ResultVO.successJson(articleRepository.search(keyword, page, pageSize));
    }


    /**
     * 文章列表
     *
     * @return
     */
    @SysLog("front:文章列表")
    @GetMapping({"", "/"})
    @Cacheable(cacheNames = RedisCacheNames.ARTICLE)
    @ApiOperation(value = "文章列表", httpMethod = "GET", response = ResultVO.class)
    public ResultVO list(BaseForm form) {
        IPage<ArticleDTO> page = articleService.page(
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
                        .eq(ArticleDO::getStatus, STATUS_PUBLISH)
        ).convert(articleDO -> {
            ArticleDTO articleDTO = new ArticleDTO();
            BeanUtil.copyProperties(articleDO, articleDTO);
            return articleDTO;

        });
        return ResultVO.successJson(PageVO.build(page));
    }

    /**
     * 文章详情
     *
     * @return
     */
    @SysLog("front:文章详情")
    @GetMapping("/{slug}")
    @Cacheable(cacheNames = RedisCacheNames.ARTICLE)
    @ApiOperation(value = "文章详情", httpMethod = "GET", response = ResultVO.class)
    public ResultVO detail(@PathVariable("slug") String slug) {
        return ResultVO.successJson(articleService.detail(slug));
    }


    @SysLog("front:标签")
    @GetMapping("/tags")
    @Cacheable(cacheNames = RedisCacheNames.TAG)
    @ApiOperation(value = "标签墙", httpMethod = "GET", response = ResultVO.class)
    public ResultVO tags() {
        return ResultVO.successJson(tagService.groupCount());
    }

    /**
     * @return
     */
    @SysLog("front:归档")
    @GetMapping("/archive")
    @Cacheable(cacheNames = RedisCacheNames.ARCHIVE)
    @ApiOperation(value = "归档", httpMethod = "GET", response = ResultVO.class)
    public ResultVO archive(@ApiParam(value = "标签") String tag) {
        return ResultVO.successJson(articleService.archive(tag));
    }
}
