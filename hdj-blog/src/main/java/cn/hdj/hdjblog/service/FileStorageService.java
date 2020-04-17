package cn.hdj.hdjblog.service;

import cn.hdj.hdjblog.model.dto.FileItemDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hdj
 * @version 1.0
 * @date 12/8/19 11:59 AM
 * @description:文件存储服务
 */
public interface FileStorageService {


    void init();

    /**
     * 存储文件
     *
     * @return
     */
    FileItemDTO store(MultipartFile file, HttpServletRequest request);
}
