package cn.hdj.hdjblog.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统日志
 * </p>
 *
 * @author hdj
 * @since 2019-09-24
 */
@Data
@TableName("t_log")
public class LogDO  implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 实体ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 更改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 操作标题
     */
    private String title;

    /**
     * 操作方法(GET,POST等)
     */
    private String method;

    /**
     * ip地址
     */
    private String ip;
    /**
     * ip 位置
     */
    private String ipAddress;

    /**
     * 执行时间
     */
    private Long time;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 请求url
     */
    private String url;

    /**
     * 异常
     */
    private String exception;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 等级
     */
    private String level;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

}
