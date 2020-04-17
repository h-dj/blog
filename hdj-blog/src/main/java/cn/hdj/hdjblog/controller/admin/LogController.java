package cn.hdj.hdjblog.controller.admin;


import cn.hdj.hdjblog.entity.LogDO;
import cn.hdj.hdjblog.model.params.BaseForm;
import cn.hdj.hdjblog.model.vo.PageVO;
import cn.hdj.hdjblog.model.vo.ResultVO;
import cn.hdj.hdjblog.service.LogService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统日志 前端控制器
 * </p>
 *
 * @author hdj
 * @since 2019-06-07
 */
@Api(tags = "系统日志")
@RestController
@RequestMapping("/api/admin/logs")
public class LogController{

    @Autowired
    private LogService service;

    @GetMapping("list")
    @ApiOperation(value = "系统日志列表", httpMethod = "GET", response = ResultVO.class)
    public ResultVO listPage(String level, BaseForm pageForm) {
        IPage<LogDO> page = service.page(pageForm.getIPage(),
                Wrappers.<LogDO>lambdaQuery()
                        .eq(StrUtil.isNotEmpty(level), LogDO::getLevel, level)
                        .orderByDesc(LogDO::getCreateTime)
        );
        return ResultVO.successJson(PageVO.build(page));
    }
}
