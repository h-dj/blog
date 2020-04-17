package cn.hdj.hdjblog.controller.admin;


import cn.hdj.hdjblog.aspect.annotation.SysLog;
import cn.hdj.hdjblog.constaint.RedisCacheNames;
import cn.hdj.hdjblog.entity.TagDO;
import cn.hdj.hdjblog.model.params.BaseForm;
import cn.hdj.hdjblog.model.params.TagForm;
import cn.hdj.hdjblog.model.vo.PageVO;
import cn.hdj.hdjblog.model.vo.ResultVO;
import cn.hdj.hdjblog.service.TagService;
import cn.hdj.hdjblog.validator.ValidatorUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author hdj
 * @since 2019-09-21
 */
@Api(tags = "标签")
@RestController
@RequestMapping("/api/admin/tags")
@CacheConfig(cacheNames = {RedisCacheNames.TAG})
public class TagController {

    @Autowired
    private TagService service;

    @ApiOperation(value = "标签下拉列表", httpMethod = "GET", response = ResultVO.class)
    @GetMapping("/selectList")
    public ResultVO selectable() {
        List<TagDO> tagList = service.list(Wrappers.<TagDO>lambdaQuery()
                .select(TagDO::getId, TagDO::getTagName)
        );
        return ResultVO.successJson(tagList);
    }

    @SysLog("标签列表")
    @ApiOperation(value = "标签列表", httpMethod = "GET", response = ResultVO.class)
    @GetMapping("/list")
    public ResultVO list(String tagName, BaseForm page) {
        IPage<TagDO> tagList = service.page(page.getIPage(),
                Wrappers.<TagDO>lambdaQuery()
                        .like(StrUtil.isNotEmpty(tagName), TagDO::getTagName, tagName)
        );
        return ResultVO.successJson(PageVO.build(tagList));
    }


    @SysLog("标签详情")
    @ApiOperation(value = "标签详情", httpMethod = "GET", response = ResultVO.class)
    @GetMapping("/info/{id}")
    public ResultVO info(@PathVariable("id") Long tagId) {
        return ResultVO.successJson(service.getById(tagId));
    }


    @SysLog("添加标签")
    @ApiOperation(value = "添加标签", httpMethod = "POST", response = ResultVO.class)
    @PostMapping("/save")
    @CacheEvict(allEntries = true)
    public ResultVO save(@RequestBody TagForm form) {
        ValidatorUtils.validateEntity(form);
        TagDO tagDO = new TagDO();
        BeanUtil.copyProperties(form, tagDO);
        service.save(tagDO);
        return ResultVO.successJson();
    }


    @SysLog("更新标签")
    @ApiOperation(value = "更新标签", httpMethod = "PUT", response = ResultVO.class)
    @PutMapping("/update/{id}")
    @CacheEvict(allEntries = true)
    public ResultVO edit(@PathVariable("id") Long id, @RequestBody TagForm form) {
        ValidatorUtils.validateEntity(form);
        TagDO tagDO = new TagDO();
        BeanUtil.copyProperties(form, tagDO);
        tagDO.setId(id);
        service.updateById(tagDO);
        return ResultVO.successJson();
    }


    @SysLog("删除标签")
    @ApiOperation(value = "删除标签", httpMethod = "DELETE", response = ResultVO.class)
    @DeleteMapping("/delete")
    @CacheEvict(allEntries = true)
    public ResultVO delete(@RequestBody List<Long> ids) {
        service.delete(ids);
        return ResultVO.successJson();
    }
}
