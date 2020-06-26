package cn.hdj.hdjblog.service;

import cn.hdj.hdjblog.config.SystemProperties;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;

import java.io.InputStream;
import java.util.Date;

/**
 * @author hdj
 * @version 1.0
 * @date 12/8/19 11:59 AM
 * @description:文件存储服务
 */
public abstract class FileStorageService {

    /**
     * 云存储配置信息
     */
    protected SystemProperties config;

    /**
     * 文件路径
     *
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 返回上传路径
     */
    public String getPath(String prefix, String suffix) {
        //生成uuid
        String uuid = IdUtil.fastSimpleUUID();
        //文件路径
        String path = DateUtil.format(new Date(), "yyyyMMdd") + "/" + uuid;
        if (StrUtil.isNotBlank(prefix)) {
            path = prefix + "/" + path;
        }
        if (StrUtil.startWith(suffix, ".")) {
            return path + suffix;
        }
        return path + "." + suffix;
    }


    /**
     * 文件上传
     *
     * @param data 文件字节数组
     * @param path 文件路径，包含文件名
     * @return 返回http地址
     */
    public abstract String upload(byte[] data, String path);

    /**
     * 文件上传
     *
     * @param data   文件字节数组
     * @param suffix 后缀
     * @return 返回http地址
     */
    public abstract String uploadSuffix(byte[] data, String suffix);

    /**
     * 文件上传
     *
     * @param inputStream 字节流
     * @param path        文件路径，包含文件名
     * @return 返回http地址
     */
    public abstract String upload(InputStream inputStream, String path);

    /**
     * 文件上传
     *
     * @param inputStream 字节流
     * @param suffix      后缀
     * @return 返回http地址
     */
    public abstract String uploadSuffix(InputStream inputStream, String suffix);
}
