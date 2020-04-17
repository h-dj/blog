package cn.hdj.hdjblog.controller.front;


import cn.hdj.hdjblog.entity.FriendLinkDO;
import cn.hdj.hdjblog.model.params.FriendLinkForm;
import cn.hdj.hdjblog.model.vo.ResultVO;
import cn.hdj.hdjblog.service.FriendLinkService;
import cn.hdj.hdjblog.validator.ValidatorUtils;
import cn.hutool.core.bean.BeanUtil;
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
@RequestMapping("/api/friendLinks")
@Api(value = "(前台)友链")
public class FriendLinkFrontController {

    @Autowired
    private FriendLinkService friendLinkService;


    @GetMapping(value = "/list")
    @ApiOperation(value = "友链列表", httpMethod = "GET", response = ResultVO.class)
    public ResultVO list() {
        List<FriendLinkDO> list = friendLinkService.list(Wrappers.<FriendLinkDO>lambdaQuery()
                .eq(FriendLinkDO::getStatus, 1)
        );
        return ResultVO.successJson(list);
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

}
