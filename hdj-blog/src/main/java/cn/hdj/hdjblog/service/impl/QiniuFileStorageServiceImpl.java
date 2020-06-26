package cn.hdj.hdjblog.service.impl;

import cn.hdj.hdjblog.config.SystemProperties;
import cn.hdj.hdjblog.constaint.enums.ResponseCodeEnum;
import cn.hdj.hdjblog.exception.MyException;
import cn.hdj.hdjblog.service.FileStorageService;
import cn.hutool.core.io.IoUtil;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * @author hdj
 * @version 1.0
 * @date 12/8/19 12:04 PM
 * @description:
 */
@Slf4j
@Service
public class QiniuFileStorageServiceImpl extends FileStorageService {

    /**
     * 指定Region对象的配置类
     */
    private Configuration cfg;
    /**
     * 上传管理类
     */
    private UploadManager uploadManager;
    /**
     * 凭证
     */
    private Auth auth;
    /**
     * 上传token
     */
    private String upToken;

    public QiniuFileStorageServiceImpl(SystemProperties systemProperties) {
        this.config = systemProperties;
        cfg = new Configuration(Region.huanan());
        uploadManager = new UploadManager(cfg);
        auth = Auth.create(systemProperties.getOss().getQiniu().getAccessKey(), systemProperties.getOss().getQiniu().getSecretKey());
        upToken = auth.uploadToken(this.config.getOss().getQiniu().getBucketName());
    }

    @Override
    public String upload(byte[] data, String path) {
        try {
            upToken = auth.uploadToken(this.config.getOss().getQiniu().getBucketName());
            Response res = uploadManager.put(data, path, upToken);
            if (!res.isOK()) {
                throw new RuntimeException("上传七牛出错：" + res.toString());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new MyException(ResponseCodeEnum.OSS_CONFIG_ERROR);
        }
        return this.config.getOss().getQiniu().getDomain() + "/" + path;
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            byte[] data = IoUtil.readBytes(inputStream);
            return this.upload(data, path);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new MyException(ResponseCodeEnum.OSS_CONFIG_ERROR);
        }
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        return upload(data, getPath(this.config.getOss().getQiniu().getPrefix(), suffix));
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(this.config.getOss().getQiniu().getPrefix(), suffix));
    }
}
