package cn.hdj.hdjblog.controller.admin;


import cn.hdj.hdjblog.entity.FriendLinkDO;
import cn.hdj.hdjblog.model.params.FriendLinkForm;
import cn.hdj.hdjblog.model.params.FriendLinkSearchForm;
import cn.hdj.hdjblog.model.vo.PageVO;
import cn.hdj.hdjblog.model.vo.ResultVO;
import cn.hdj.hdjblog.service.FriendLinkService;
import cn.hdj.hdjblog.validator.ValidatorUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 友链 前端控制器
 * </p>
 *
 * @author hdj
 * @since 2020-01-14
 */
@RestController
@RequestMapping("/api/admin/friendLinks")
@Api(value = "友链")
public class FriendLinkController {

    @Autowired
    private FriendLinkService friendLinkService;


    @GetMapping(value = "/list")
    @ApiOperation(value = "友链列表", httpMethod = "GET", response = ResultVO.class)
    public ResultVO list(@ApiParam FriendLinkSearchForm roleForm) {
        IPage<FriendLinkDO> page = friendLinkService.page(roleForm.getIPage(), Wrappers.<FriendLinkDO>lambdaQuery()
                .eq(roleForm.getStatus() != null, FriendLinkDO::getStatus, roleForm.getStatus())
        );
        return ResultVO.successJson(PageVO.build(page));
    }

    @PostMapping(value = "/add")
    @ApiOperation(value = "友链列表", httpMethod = "POST", response = ResultVO.class)
    public ResultVO add(@ApiParam @RequestBody FriendLinkForm form) {
        ValidatorUtils.validateEntity(form);
        FriendLinkDO friendLinkDO = new FriendLinkDO();
        BeanUtil.copyProperties(form, friendLinkDO);
        friendLinkService.save(friendLinkDO);
        return ResultVO.successJson();
    }

    @PutMapping("/examine")
    @ApiOperation(value = "审核友链", httpMethod = "PUT", response = ResultVO.class)
    public ResultVO examine(@ApiParam @RequestBody FriendLinkForm form) {
        FriendLinkDO friendLinkDO = new FriendLinkDO();
        friendLinkDO.setId(form.getId());
        friendLinkDO.setStatus(form.getStatus());
        friendLinkService.updateById(friendLinkDO);
        return ResultVO.successJson();
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除链接", httpMethod = "DELETE", response = ResultVO.class)
    public ResultVO delete(@RequestBody List<Long> ids) {
        if (CollectionUtil.isNotEmpty(ids)) {
            friendLinkService.removeByIds(ids);
        }
        return ResultVO.successJson();
    }
}
