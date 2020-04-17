package cn.hdj.hdjblog.service.impl;

import cn.hdj.hdjblog.dao.LogDao;
import cn.hdj.hdjblog.entity.LogDO;
import cn.hdj.hdjblog.service.LogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author hdj
 * @since 2019-06-07
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogDao, LogDO> implements LogService {

}
