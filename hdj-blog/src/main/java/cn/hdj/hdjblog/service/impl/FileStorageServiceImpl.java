package cn.hdj.hdjblog.service.impl;

import cn.hdj.hdjblog.config.SystemProperties;
import cn.hdj.hdjblog.entity.AttachmentDO;
import cn.hdj.hdjblog.exception.MyException;
import cn.hdj.hdjblog.model.dto.FileItemDTO;
import cn.hdj.hdjblog.service.AttachmentService;
import cn.hdj.hdjblog.service.FileStorageService;
import cn.hdj.hdjblog.util.FileUtils;
import cn.hdj.hdjblog.util.MyWebUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author hdj
 * @version 1.0
 * @date 12/8/19 12:04 PM
 * @description:
 */
@Slf4j
@Service
public class FileStorageServiceImpl implements FileStorageService {


    @Autowired
    private AttachmentService attachmentService;

    /**
     * 文件存储路径
     */
    private Path rootLocation;
    private String baseURI;

    public FileStorageServiceImpl(SystemProperties systemProperties) {
        this.rootLocation = Paths.get(systemProperties.getFileUploadDir());
        baseURI = systemProperties.getFileBaseUrl();
    }

    @PostConstruct
    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new MyException("文件保存路径创建失败！");
        }
    }

    @Override
    public FileItemDTO store(MultipartFile file, HttpServletRequest request) {

        boolean flag = false;
        Path path = null;
        FileItemDTO fileItemDTO = null;
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (filename.contains("..")) {
                // 安全检查
                throw new MyException("不能保存文件到当前路径外的路径：　" + filename);
            }

            String fileSuffix = FileUtil.extName(filename);
            if (!FileUtils.isPicture(fileSuffix)) {
                throw new MyException("只能上传图片类型：　" + filename);
            }
            //生成路径
            String dirPath = FileUtils.makeDirPath(filename);
            String genFileName = FileUtils.genFileName(fileSuffix);
            String fileSize = FileUtils.genFileSize(file.getSize());
            path = rootLocation.resolve(Paths.get(dirPath, genFileName));
            if (path != null) {
                path.toFile().mkdirs();
                path.toFile().createNewFile();
            }

            AttachmentDO attachmentDO = new AttachmentDO();
            attachmentDO.setCreateBy(MyWebUtils.getCurrentUserId());
            attachmentDO.setFileName(filename);
            attachmentDO.setFileSize(fileSize);
            attachmentDO.setPath(path.toString());
            attachmentDO.setFileSuffix(fileSuffix);
            attachmentDO.setType(0);
            attachmentDO.setUrl(String.format("%s%s%s%s", MyWebUtils.getDaemon(), baseURI, dirPath, genFileName));


            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            }
            flag = attachmentService.save(attachmentDO);
            fileItemDTO = new FileItemDTO();
            BeanUtil.copyProperties(attachmentDO, fileItemDTO);
        } catch (IOException e) {
            flag = false;
            log.error(e.getMessage(), e);
            throw new MyException("Failed to store file " + filename);
        } finally {
            if (!flag) {
                FileUtils.delete(path);
            }
        }
        return fileItemDTO;
    }
}
