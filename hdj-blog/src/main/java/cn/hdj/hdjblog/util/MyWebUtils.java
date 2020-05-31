package cn.hdj.hdjblog.util;

import cn.hdj.hdjblog.constaint.ConfigConstaint;
import cn.hdj.hdjblog.model.dto.UserDetailDTO;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.Resource;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

/**
 * @author hdj
 * @Description: Web相关工具
 * @Version 1.0
 */
@Slf4j
public class MyWebUtils {


    private final static String STR_XMLHTTPREQUEST = "XMLHttpRequest";
    private final static String STR_XREQUESTWITH = "x-request-with";
    private final static String STR_UNKNOWN = "unknown";
    private static final String IP_ADDR_LOCAL = "127.0.0.1";
    private static final String IP_ADDR_SQUID_HEADER = "x-forwarded-for";
    private static final String IP_ADDR_APACHE_HEADER = "Proxy-Client-IP";
    private static final String IP_ADDR_WEBLOGIC_HEADER = "WL-Proxy-Client-IP";
    private static final String IP_ADDER_REAL_HEADER = "X-Real-IP";

    private static final byte[] dbBinStr;

    static {
        Resource ip2region = ResourceUtil.getResourceObj("classpath:ip2region.db");
        dbBinStr = ip2region.readBytes();
    }

    /**
     * 输出JSON字符串到Response
     *
     * @param servletResponse
     * @param obj
     */
    public static void responseWrite(ServletResponse servletResponse, Object obj) {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            out = response.getWriter();
            out.write(JSONUtil.toJsonStr(obj));
            out.flush();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            IoUtil.close(out);
        }
    }


    /**
     * 获取IP地址
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        //Squid http代理服务器
        String ip = request.getHeader(IP_ADDR_SQUID_HEADER);
        //apache http代理服务器
        if (ip == null || ip.length() == 0 || STR_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(IP_ADDR_APACHE_HEADER);
        }
        //weblogic http代理服务器
        if (ip == null || ip.length() == 0 || STR_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(IP_ADDR_WEBLOGIC_HEADER);
        }
        //weblogic http代理服务器
        if (ip == null || ip.length() == 0 || STR_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(IP_ADDER_REAL_HEADER);
        }
        //本机
        if (ip == null || ip.length() == 0 || STR_UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (IP_ADDR_LOCAL.equals(ip)) {
                /** 根据网卡取本机配置的IP */
                try {
                    ip = InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    log.error("IpHelper error." + e.getMessage());
                }
            }
        }
        String[] ips = Optional.of(ip).orElse("").split(",");
        return "0:0:0:0:0:0:0:1".equals(ips[0]) ? "127.0.0.1" : ips[0];
    }


    /**
     * 根据ip获取详细地址
     *
     * @param ip
     * @return
     */
    public static String getCityInfo(String ip) {
        File file = null;
        try {
            DbConfig config = new DbConfig();
            DbSearcher searcher = new DbSearcher(config, dbBinStr);
            DataBlock dataBlock = searcher.memorySearch(ip);
            String address = dataBlock.getRegion().replace("0|", "");
            if (address.charAt(address.length() - 1) == '|') {
                address = address.substring(0, address.length() - 1);
            }
            return ConfigConstaint.REGION.equals(address) ? "内网IP" : address;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if (file != null && file.exists()) {
                file.delete();
            }
        }
        return "";
    }


    /**
     * 获取请求
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }

    /**
     * 获取域名
     *
     * @return
     */
    public static String getDaemon() {
        HttpServletRequest request = getRequest();
        return String.format("%s://%s:%s", request.getScheme(), request.getServerName(), request.getServerPort());
    }


    /**
     * 获取当前登陆用户
     *
     * @return
     */
    public static UserDetailDTO getCurrentUser() {
        UserDetailDTO user = (UserDetailDTO) SecurityUtils.getSubject().getPrincipal();
        return user;
    }

    /**
     * 获取当前用户账户
     *
     * @return
     */
    public static String getCurrentUserName() {
        return getCurrentUser().getEmail();
    }

    /**
     * 获取当前用户ID
     *
     * @return
     */
    public static Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

}
