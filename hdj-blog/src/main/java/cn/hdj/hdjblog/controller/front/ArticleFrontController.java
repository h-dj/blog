package cn.hdj.hdjblog.controller.front;

import cn.hdj.hdjblog.aspect.annotation.ArticleLike;
import cn.hdj.hdjblog.aspect.annotation.ArticleView;
import cn.hdj.hdjblog.aspect.annotation.SysLog;
import cn.hdj.hdjblog.constaint.RabbitMqConstants;
import cn.hdj.hdjblog.constaint.RedisCacheNames;
import cn.hdj.hdjblog.elasticsearch.ArticleRepository;
import cn.hdj.hdjblog.entity.CategoryDO;
import cn.hdj.hdjblog.model.params.ArticleSearchForm;
import cn.hdj.hdjblog.model.vo.ResultVO;
import cn.hdj.hdjblog.service.ArticleService;
import cn.hdj.hdjblog.service.CategoryService;
import cn.hdj.hdjblog.service.TagService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

/**
 * @author hdj
 * @Description:　前台博客文章控制器
 * @date 9/25/19
 */
@Api(tags = "(前台)文章")
@RestController
@RequestMapping("/api/articles")
public class ArticleFrontController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleRepository articleRepository;


    @Autowired
    private RabbitTemplate rabbitTemplate;


    @PostConstruct
    public void initIndex() {
        rabbitTemplate.convertAndSend(RabbitMqConstants.REFRESH_ES_INDEX_QUEUE, RabbitMqConstants.REFRESH_ES_INDEX_QUEUE, "init index");
    }
    /**
     * 搜索标题，描述，内容
     *
     * @param keyword
     * @return
     */
    @GetMapping("/search")
    public ResultVO search(@RequestParam("keyword") String keyword,
                           @RequestParam(value = "page", defaultValue = "1") Integer page,
                           @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        return ResultVO.successJson(articleRepository.pageSearch(keyword, page, pageSize));
    }


    @SysLog("front:文章列表")
    @GetMapping({"", "/"})
    @Cacheable(cacheNames = RedisCacheNames.ARTICLE)
    @ApiOperation(value = "文章列表", httpMethod = "GET", response = ResultVO.class)
    public ResultVO list(ArticleSearchForm form) {
        return this.articleService.articleList(form);
    }


    @ArticleView
    @SysLog("front:文章查看")
    @GetMapping("/{slug}")
    @ApiOperation(value = "文章详情", httpMethod = "GET", response = ResultVO.class)
    public ResultVO detail(@PathVariable("slug") String slug) {
        return ResultVO.successJson(articleService.detail(slug));
    }

    @ArticleLike
    @SysLog("front:文章点赞")
    @PutMapping("/like/{slug}")
    @ApiOperation(value = "文章点赞", httpMethod = "PUT", response = ResultVO.class)
    public ResultVO like(@PathVariable("slug") String slug) {
        return ResultVO.successJson();
    }


    @SysLog("front:标签")
    @GetMapping("/tags")
    @Cacheable(cacheNames = RedisCacheNames.TAG)
    @ApiOperation(value = "标签墙", httpMethod = "GET", response = ResultVO.class)
    public ResultVO tags() {
        return ResultVO.successJson(tagService.groupCount());
    }

    @SysLog("front:分类")
    @GetMapping("/categorys")
    @Cacheable(cacheNames = RedisCacheNames.CATEGORY)
    @ApiOperation(value = "标签墙", httpMethod = "GET", response = ResultVO.class)
    public ResultVO categorys() {
        return ResultVO.successJson(
               categoryService.list(Wrappers.<CategoryDO>lambdaQuery()
                       .select(CategoryDO::getId,CategoryDO::getCategoryName)
               )
        );
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
