package cn.hdj.hdjblog.service;

import cn.hdj.hdjblog.entity.LogDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author hdj
 * @since 2019-06-07
 */
public interface LogService extends IService<LogDO> {

    /**
     * 保存日志
     *
     * @param logDO
     */
    void saveLog(LogDO logDO);
}
