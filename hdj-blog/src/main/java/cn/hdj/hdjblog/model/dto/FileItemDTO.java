package cn.hdj.hdjblog.model.dto;

import lombok.Data;

/**
 * @author hdj
 * @version 1.0
 * @date 12/8/19 12:00 PM
 * @description: 文件Item
 */
@Data
public class FileItemDTO {

    /**
     * id
     */
    private Long id;
    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件后缀
     */
    private String fileSuffix;
    /**
     * 文件大小
     */
    private String fileSize;

    /**
     * 文件访问url
     */
    private String url;

    /**
     * 文件存储路径
     */
    private String path;
}
