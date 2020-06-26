package cn.hdj.hdjblog.service.impl;

import cn.hdj.hdjblog.HdjBlogApplicationTests;
import cn.hutool.core.io.FileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author hdj
 * @version 1.0
 * @date 25/06/2020 00:25
 * @description:
 */
class QiniuFileStorageServiceImplTest extends HdjBlogApplicationTests {

    @Autowired
    private QiniuFileStorageServiceImpl qiniuFileStorageService;

    @Test
    void upload() {
        String file = "/home/hdj/Pictures/Hummingbird_by_Shu_Le.jpg";
        Path path = Paths.get(file);

        String mimeType = FileUtil.getMimeType(file);
        System.out.println(mimeType);
        String upload = qiniuFileStorageService.uploadSuffix(FileUtil.getInputStream(path), FileUtil.getType(path.toFile()));
        System.out.println(upload);
    }

    @Test
    void testUpload() {
    }

    @Test
    void uploadSuffix() {
    }

    @Test
    void testUploadSuffix() {
    }
}