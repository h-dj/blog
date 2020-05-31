package cn.hdj.hdjblog.controller.admin;


import cn.hdj.hdjblog.aspect.annotation.SysLog;
import cn.hdj.hdjblog.constaint.RedisCacheNames;
import cn.hdj.hdjblog.entity.ArticleDO;
import cn.hdj.hdjblog.entity.CategoryDO;
import cn.hdj.hdjblog.exception.MyException;
import cn.hdj.hdjblog.model.params.BaseForm;
import cn.hdj.hdjblog.model.params.CategoryForm;
import cn.hdj.hdjblog.model.vo.PageVO;
import cn.hdj.hdjblog.model.vo.ResultVO;
import cn.hdj.hdjblog.service.ArticleService;
import cn.hdj.hdjblog.service.CategoryService;
import cn.hdj.hdjblog.validator.ValidatorUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 分类表 前端控制器
 * </p>
 *
 * @author hdj
 * @since 2019-09-24
 */
@Api(tags = "文章分类")
@RestController
@RequestMapping("/api/admin/categorys")
@CacheConfig(cacheNames = {RedisCacheNames.CATEGORY})

public class CategoryController {

    @Autowired
    private CategoryService service;

    @Autowired
    private ArticleService articleService;


    @RequiresPermissions("article:category:search")
    @SysLog("分类列表")
    @ApiOperation(value = "分类列表", httpMethod = "GET", response = ResultVO.class)
    @GetMapping("/list")
    public ResultVO list(String categoryName, BaseForm form) {
        IPage page = service.page(
                form.getIPage(),
                Wrappers.<CategoryDO>lambdaQuery()
                        .like(StrUtil.isNotEmpty(categoryName), CategoryDO::getCategoryName, categoryName)
        );
        return ResultVO.successJson(PageVO.build(page));
    }

    @GetMapping("/selectList")
    public ResultVO selectable() {
        List<CategoryDO> list = service.list();
        return ResultVO.successJson(list);
    }

    @RequiresPermissions("article:category:info")
    @SysLog("分类详情")
    @ApiOperation(value = "分类详情", httpMethod = "GET", response = ResultVO.class)
    @GetMapping("/info/{id}")
    public ResultVO list(@PathVariable("id") Long categoryId) {
        return ResultVO.successJson(service.getById(categoryId));
    }

    @RequiresPermissions("article:category:add")
    @SysLog("添加分类")
    @ApiOperation(value = "添加分类", httpMethod = "POST", response = ResultVO.class)
    @PostMapping("/save")
    @CacheEvict(allEntries = true)
    public ResultVO save(@RequestBody CategoryForm categoryForm) {
        ValidatorUtils.validateEntity(categoryForm);
        CategoryDO categoryDO = new CategoryDO();
        BeanUtil.copyProperties(categoryForm, categoryDO);
        service.save(categoryDO);
        return ResultVO.successJson();
    }

    @RequiresPermissions("article:category:edit")
    @SysLog("更新分类")
    @ApiOperation(value = "更新分类", httpMethod = "PUT", response = ResultVO.class)
    @PutMapping("/update/{id}")
    @CacheEvict(allEntries = true)
    public ResultVO update(@PathVariable("id") Long id, @RequestBody CategoryForm categoryForm) {
        ValidatorUtils.validateEntity(categoryForm);
        CategoryDO categoryDO = new CategoryDO();
        BeanUtil.copyProperties(categoryForm, categoryDO);
        categoryDO.setId(id);
        service.updateById(categoryDO);
        return ResultVO.successJson();
    }


    @RequiresPermissions("article:category:delete")
    @SysLog("删除分类")
    @DeleteMapping("/delete")
    @CacheEvict(allEntries = true)
    @ApiOperation(value = "删除分类", httpMethod = "DELETE", response = ResultVO.class)
    public ResultVO delete(@RequestBody List<Long> ids) {
        if (ids != null && ids.size() > 0) {
            int count = articleService.count(Wrappers.<ArticleDO>lambdaQuery()
                    .in(ArticleDO::getCategoryId, ids)
            );
            if (count > 0) {
                throw new MyException("所选分类下有文章，无法删除！");
            }
            //删除分类
            service.removeByIds(ids);
        }
        return ResultVO.successJson();
    }
}
