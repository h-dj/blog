package cn.hdj.hdjblog.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author hdj
 * @version 1.0
 * @date 12/12/19 10:59 PM
 * @description:
 */
@Data
@ApiModel(value = "ChunkForm", description = "分块上传文件对象")
public class ChunkForm {

    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "当前文件块，从1开始")
    private Integer chunkNumber;
    @ApiModelProperty(value = "分块大小")
    private Long chunkSize;
    @ApiModelProperty(value = "当前分块大小")
    private Long currentChunkSize;
    @ApiModelProperty(value = "总大小")
    private Long totalSize;
    @ApiModelProperty(value = "文件标识")
    private String identifier;
    @ApiModelProperty(value = "文件名")
    private String filename;
    @ApiModelProperty(value = "相对路径")
    private String relativePath;
    @ApiModelProperty(value = "总块数")
    private Integer totalChunks;
    @ApiModelProperty(value = "文件类型")
    private String type;
    @ApiModelProperty(value = "文件类型")
    private MultipartFile file;
}
