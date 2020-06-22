package cn.hdj.hdjblog.controller;


import cn.hdj.hdjblog.constaint.RedisCacheKeys;
import cn.hdj.hdjblog.constaint.enums.ResponseCodeEnum;
import cn.hdj.hdjblog.exception.MyException;
import cn.hdj.hdjblog.model.vo.ResultVO;
import cn.hdj.hdjblog.util.RedisUtils;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.ServletRequest;
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
    @ResponseBody
    public ResultVO responseCaptcha(HttpServletRequest request) throws IOException {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        String code = lineCaptcha.getCode();
        String key = SecureUtil.md5(String.format("%s-%s", request.getHeader("User-Agent"), IdUtil.fastSimpleUUID()));
        String imgBase64 = lineCaptcha.getImageBase64();
        //设置验证码过期时间15秒
        redisUtils.set(String.format(RedisCacheKeys.REDIS_CAPTCHA, key), code, 15);
        Dict dict = Dict.create()
                .set("k", key)
                .set("img", imgBase64);
        return ResultVO.successJson(dict);
    }


}
