package cn.hdj.hdjblog.controller;


import cn.hdj.hdjblog.constaint.RedisCacheKeys;
import cn.hdj.hdjblog.constaint.enums.ResponseCodeEnum;
import cn.hdj.hdjblog.exception.MyException;
import cn.hdj.hdjblog.model.vo.ResultVO;
import cn.hdj.hdjblog.util.RedisUtils;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

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

    /**
     * 生成验证码
     */
    @GetMapping("/captcha")
    public void responseCaptcha(HttpServletResponse response, String uuid) throws IOException {
        if (StrUtil.isEmpty(uuid)) {
            throw new MyException(ResponseCodeEnum.NO_UUID);
        }
        try (OutputStream outputStream = response.getOutputStream();) {
            LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
            String code = lineCaptcha.getCode();
            //设置验证码过期时间15秒
            redisUtils.set(String.format(RedisCacheKeys.REDIS_CAPTCHA, uuid), code, 15);
            lineCaptcha.write(outputStream);
        }
    }


}
