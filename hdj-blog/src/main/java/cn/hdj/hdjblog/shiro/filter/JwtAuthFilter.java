package cn.hdj.hdjblog.shiro.filter;


import cn.hdj.hdjblog.constaint.ConfigConstaint;
import cn.hdj.hdjblog.constaint.enums.ResponseCodeEnum;
import cn.hdj.hdjblog.model.dto.UserDetailDTO;
import cn.hdj.hdjblog.model.vo.ResultVO;
import cn.hdj.hdjblog.shiro.JwtToken;
import cn.hdj.hdjblog.shiro.service.ShiroService;
import cn.hdj.hdjblog.util.JwtUtils;
import cn.hdj.hdjblog.util.MyWebUtils;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hdj
 * @Description: JWT认证过滤器
 * @Version 1.0
 */
@Slf4j
public class JwtAuthFilter extends AuthenticatingFilter {

    /**
     * token 过期前300秒 重新生成token
     */
    private final long TOKEN_REFRESH_INTERVAL = 300L;

    @Autowired
    private ShiroService shiroService;

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //是否是登录URL,是则放行
        return this.isLoginRequest(request, response);
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
        String jwtToken = getRequestToken((HttpServletRequest) servletRequest);
        if (StrUtil.isEmpty(jwtToken)) {
            return null;
        }
        return new JwtToken(jwtToken);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        String token = getRequestToken((HttpServletRequest) servletRequest);
        if (StrUtil.isEmpty(token)) {
            log.debug("toke is empty " + ((HttpServletRequest) servletRequest).getRequestURL().toString());
            MyWebUtils.responseWrite(servletResponse, ResultVO.errorJson(ResponseCodeEnum.TOKEN_GENERATOR_ILLEGAL));
            return false;
        }
        return executeLogin(servletRequest, servletResponse);
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        String newToken = null;
        if (token instanceof JwtToken) {
            JwtToken jwtToken = (JwtToken) token;
            UserDetailDTO user = (UserDetailDTO) subject.getPrincipal();
            boolean shouldRefresh = JwtUtils.isShouldRefreshToken(jwtToken.getToken(), TOKEN_REFRESH_INTERVAL);
            if (shouldRefresh) {
                newToken = shiroService.generateJwtToken(user.getEmail());
            }
        }
        if (StrUtil.isNotBlank(newToken)) {
            httpResponse.setHeader(ConfigConstaint.SYSTEM_TOKEN_HEADER, newToken);
        }
        return true;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        log.error(e.getMessage(), e);
        MyWebUtils.responseWrite(response, ResultVO.errorJson(ResponseCodeEnum.TOKEN_GENERATOR_ILLEGAL));
        return false;
    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest) {
        //从header中获取token
        String token = httpRequest.getHeader(ConfigConstaint.SYSTEM_TOKEN_HEADER);
        //如果header中不存在token，则从参数中获取token
        if (StrUtil.isEmpty(token)) {
            token = httpRequest.getParameter(ConfigConstaint.SYSTEM_TOKEN_HEADER);
        }
        return StrUtil.removePrefixIgnoreCase(token, "Bearer ");
    }
}
