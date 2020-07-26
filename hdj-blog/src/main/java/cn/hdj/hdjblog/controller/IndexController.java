package cn.hdj.hdjblog.controller;


import cn.hdj.hdjblog.constaint.enums.ResponseCodeEnum;
import cn.hdj.hdjblog.model.vo.ResultVO;
import cn.hdj.hdjblog.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * @author h_dj
 * @date: 2019/6/2 16:03
 * @description:
 */
@Controller
@Slf4j
public class IndexController {

    @Autowired
    private RedisUtils redisUtils;


    @ApiIgnore
    @GetMapping(value = "/error")
    @ResponseBody
    public Object error(HttpServletRequest request) {
        return ResultVO.errorJson(ResponseCodeEnum.PATH_NOT_FOUND);
    }

}
