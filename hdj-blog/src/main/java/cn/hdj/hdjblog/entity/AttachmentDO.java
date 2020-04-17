package cn.hdj.hdjblog.entity;

import cn.hdj.hdjblog.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 附件表
 * </p>
 *
 * @author hdj
 * @since 2019-08-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_attachment")
public class AttachmentDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 附件名称
     */
    private String fileName;


    /**
     * 大小
     */
    private String fileSize;

    /**
     * 访问路径
     */
    private String url;


    /**
     * 后缀
     */
    private String fileSuffix;

    /**
     * 附件上传类型(0:本地)
     */
    private Integer type;

    /**
     * 宽
     */
    private Integer width;

    /**
     * 高
     */
    private Integer height;

    /**
     * 文件存放位置
     */
    private String path;

    /**
     * 创建人
     */
    private Long createBy;


}
