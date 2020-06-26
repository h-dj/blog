package cn.hdj.hdjblog.controller.upload;

import cn.hdj.hdjblog.constaint.ConfigConstaint;
import cn.hdj.hdjblog.entity.AttachmentDO;
import cn.hdj.hdjblog.model.params.ImagesSearchForm;
import cn.hdj.hdjblog.model.vo.PageVO;
import cn.hdj.hdjblog.model.vo.ResultVO;
import cn.hdj.hdjblog.service.AttachmentService;
import cn.hdj.hdjblog.service.impl.QiniuFileStorageServiceImpl;
import cn.hdj.hdjblog.util.MyWebUtils;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author hdj
 * @version 1.0
 * @date 12/8/19 11:30 AM
 * @description: 文件上传控制器
 */
@Api(tags = "图片上传")
@RestController
@RequestMapping(value = "/api/admin/images")
public class OssUploadController {

    @Autowired
    private QiniuFileStorageServiceImpl fileStorageService;

    @Autowired
    private AttachmentService attachmentService;

    @RequiresPermissions("sys-tool:picture:upload")
    @ApiOperation(value = "上传", httpMethod = "POST", response = ResultVO.class)
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultVO upload(MultipartFile file, HttpServletRequest request) throws IOException {
        if (file == null || file.isEmpty()) {
            return ResultVO.errorJson("上传文件不能为空！");
        }
        Long currentUserId = MyWebUtils.getCurrentUserId();
        String filename = file.getOriginalFilename();

        String suffix = StrUtil.subAfter(filename, ".", true);
        String url = fileStorageService.uploadSuffix(file.getInputStream(), suffix);
        AttachmentDO attachmentDO = new AttachmentDO();
        attachmentDO.setCreateBy(currentUserId);
        attachmentDO.setFileName(filename);
        attachmentDO.setFileSuffix(suffix);
        attachmentDO.setFileSize(NumberUtil.toStr(file.getSize()));
        attachmentDO.setUrl(url);
        attachmentService.save(attachmentDO);
        return ResultVO.successJson(attachmentDO);
    }


    @RequiresPermissions("sys-tool:picture:search")
    @ApiOperation(value = "图库列表", httpMethod = "GET", response = ResultVO.class)
    @GetMapping(value = "/list")
    public ResultVO list(ImagesSearchForm form) {
        Long currentUserId = MyWebUtils.getCurrentUserId();

        IPage<AttachmentDO> page = attachmentService.page(form.getIPage(),
                Wrappers.<AttachmentDO>lambdaQuery()
                        .eq(currentUserId != ConfigConstaint.SUPER_ADMIN, AttachmentDO::getCreateBy, currentUserId)
                        .like(StrUtil.isNotEmpty(form.getFileName()), AttachmentDO::getFileName, form.getFileName())
                        .apply(form.getUploadTimeFrom() != null && form.getUploadTimeTo() != null,
                                "date_format(create_time,'%Y-%m-%d') >= {0} and date_format(create_time,'%Y-%m-%d') <= {1}",
                                DateUtil.format(form.getUploadTimeFrom(),"yyyy-MM-dd"),
                                DateUtil.format(form.getUploadTimeTo(),"yyyy-MM-dd")
                        )
                        .orderByDesc(AttachmentDO::getUpdateTime)
        );
        return ResultVO.successJson(PageVO.build(page));
    }
}
