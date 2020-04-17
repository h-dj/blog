package cn.hdj.hdjblog.controller.upload;

import cn.hdj.hdjblog.entity.AttachmentDO;
import cn.hdj.hdjblog.model.dto.FileItemDTO;
import cn.hdj.hdjblog.model.params.ImagesSearchForm;
import cn.hdj.hdjblog.model.vo.PageVO;
import cn.hdj.hdjblog.model.vo.ResultVO;
import cn.hdj.hdjblog.service.AttachmentService;
import cn.hdj.hdjblog.service.FileStorageService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hdj
 * @version 1.0
 * @date 12/8/19 11:30 AM
 * @description: 文件上传控制器
 */
@Api(tags = "图片上传")
@RestController
@RequestMapping(value = "/api/admin/images")
public class ImageUploadController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private AttachmentService attachmentService;

    @ApiOperation(value = "上传", httpMethod = "POST", response = ResultVO.class)
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultVO upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        if (file == null || file.isEmpty()) {
            return ResultVO.errorJson("上传文件不能为空！");
        }
        FileItemDTO store = fileStorageService.store(file, request);
        return ResultVO.successJson(store);
    }


    @ApiOperation(value = "图库列表", httpMethod = "GET", response = ResultVO.class)
    @GetMapping(value = "/list")
    public ResultVO list(ImagesSearchForm form) {
        IPage<AttachmentDO> page = attachmentService.page(form.getIPage(),
                Wrappers.<AttachmentDO>lambdaQuery()
                        .like(StrUtil.isNotEmpty(form.getFileName()), AttachmentDO::getFileName, form.getFileName())
                        .ge(form.getUploadTimeFrom() != null, AttachmentDO::getCreateTime, form.getUploadTimeFrom())
                        .le(form.getUploadTimeTo() != null, AttachmentDO::getCreateTime, form.getUploadTimeTo())
                        .orderByDesc(AttachmentDO::getUpdateTime)
        );
        return ResultVO.successJson(PageVO.build(page));
    }
}
