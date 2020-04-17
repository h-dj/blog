package cn.hdj.hdjblog.service.impl;

import cn.hdj.hdjblog.dao.CommentDao;
import cn.hdj.hdjblog.entity.CommentDO;
import cn.hdj.hdjblog.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章评论表 服务实现类
 * </p>
 *
 * @author hdj
 * @since 2019-06-07
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, CommentDO> implements CommentService {

}
