package cn.hdj.hdjblog.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author hdj
 * @version 1.0
 * @date 12/8/19 10:23 PM
 * @description: 图库搜索参数
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ImagesSearchForm", description = "图库搜索参数")
public class ImagesSearchForm extends BaseForm {

    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "上传时间开始")
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    private Date uploadTimeFrom;

    @ApiModelProperty(value = "上传时间结束")
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    private Date uploadTimeTo;
}
