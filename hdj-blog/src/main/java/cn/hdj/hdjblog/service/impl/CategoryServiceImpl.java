package cn.hdj.hdjblog.service.impl;

import cn.hdj.hdjblog.dao.CategoryDao;
import cn.hdj.hdjblog.entity.CategoryDO;
import cn.hdj.hdjblog.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author hdj
 * @since 2019-09-21
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryDO> implements CategoryService {

}
