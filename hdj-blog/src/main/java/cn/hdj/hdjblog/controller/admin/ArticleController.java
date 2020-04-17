package cn.hdj.hdjblog.controller.admin;


import cn.hdj.hdjblog.aspect.annotation.RefreshEsMqSender;
import cn.hdj.hdjblog.aspect.annotation.SysLog;
import cn.hdj.hdjblog.constaint.RedisCacheNames;
import cn.hdj.hdjblog.entity.ArticleDO;
import cn.hdj.hdjblog.model.params.ArticleForm;
import cn.hdj.hdjblog.model.params.ArticleSearchForm;
import cn.hdj.hdjblog.model.vo.PageVO;
import cn.hdj.hdjblog.model.vo.ResultVO;
import cn.hdj.hdjblog.service.ArticleService;
import cn.hdj.hdjblog.validator.ValidatorUtils;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 文章信息表 前端控制器
 * </p>
 *
 * @author hdj
 * @since 2019-06-07
 */
@Api(tags = "文章管理")
@RestController
@RequestMapping("/api/admin/article")
@CacheConfig(cacheNames = {RedisCacheNames.ARTICLE, RedisCacheNames.ARCHIVE, RedisCacheNames.TAG, RedisCacheNames.CATEGORY})
public class ArticleController {

    @Autowired
    private ArticleService service;

    /**
     * 文章详情
     *
     * @return
     */
    @SysLog("文章详情")
    @GetMapping("/info/{id:\\d+}")
    @ApiOperation(value = "文章详情", httpMethod = "GET", response = ResultVO.class)
    public ResultVO info(@PathVariable("id") Long id) {
        return ResultVO.successJson(service.detail(String.valueOf(id)));
    }

    /**
     * 文章列表
     *
     * @return
     */
    @SysLog("文章列表")
    @GetMapping("/list")
    @ApiOperation(value = "文章列表", httpMethod = "GET", response = ResultVO.class)
    public ResultVO list(ArticleSearchForm form) {
        IPage<ArticleDO> page = service.page(
                form.getIPage(),
                Wrappers.<ArticleDO>lambdaQuery()
                        .select(ArticleDO::getId,
                                ArticleDO::getTitle,
                                ArticleDO::getAuthorName,
                                ArticleDO::getReadNum,
                                ArticleDO::getLikeNum,
                                ArticleDO::getCommentNum,
                                ArticleDO::getAllowComment,
                                ArticleDO::getAllowFeed,
                                ArticleDO::getRecommend,
                                ArticleDO::getTags,
                                ArticleDO::getStatus,
                                ArticleDO::getPublishTime,
                                ArticleDO::getCreateTime
                        )
                        .ne(ArticleDO::getStatus, 2)
                        .like(StrUtil.isNotEmpty(form.getTitle()), ArticleDO::getTitle, form.getTitle())
        );
        return ResultVO.successJson(PageVO.build(page));
    }

    /**
     * 保存文章
     *
     * @return
     */
    @RefreshEsMqSender(msg = "blog-add-article")
    @SysLog(value = "保存文章", ignoreParams = true)
    @PostMapping("/save")
    @ApiOperation(value = "保存文章", httpMethod = "POST", response = ResultVO.class)
    @CacheEvict(allEntries = true)
    public ResultVO save(@RequestBody ArticleForm form) {
        ValidatorUtils.validateEntity(form);
        service.saveArticle(form);
        return ResultVO.successJson(form.getId());
    }

    /**
     * 修改文章
     *
     * @return
     */
    @RefreshEsMqSender(msg = "blog-update-article")
    @SysLog(value = "修改文章", ignoreParams = true)
    @PutMapping("/edit/{id:\\d+}")
    @ApiOperation(value = "修改文章", httpMethod = "PUT", response = ResultVO.class)
    @CacheEvict(allEntries = true)
    public ResultVO edit(@PathVariable("id") Long id, @RequestBody ArticleForm form) {
        ValidatorUtils.validateEntity(form);
        service.editArticle(id, form);
        return ResultVO.successJson();
    }

    @RefreshEsMqSender(msg = "blog-delete-article")
    @SysLog("删除文章")
    @DeleteMapping("/delete")
    @CacheEvict(allEntries = true)
    @ApiOperation(value = "删除文章", httpMethod = "DELETE", response = ResultVO.class)
    public ResultVO deleteBatch(@RequestBody List<Long> articleIds) {
        service.deleteBatch(articleIds);
        return ResultVO.successJson();
    }


    @SysLog("导入文章")
    @PostMapping("/import")
    @CacheEvict(allEntries = true)
    @ApiOperation(value = "删除文章", httpMethod = "POST", response = ResultVO.class)
    public ResultVO importArticle(@RequestBody List<MultipartFile> files) {

        return ResultVO.successJson();
    }
}
