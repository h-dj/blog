package cn.hdj.hdjblog.service.impl;

import cn.hdj.hdjblog.dao.UserRoleDao;
import cn.hdj.hdjblog.entity.UserRoleDO;
import cn.hdj.hdjblog.service.UserRoleService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户角色 服务实现类
 * </p>
 *
 * @author hdj
 * @since 2019-06-07
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRoleDO> implements UserRoleService {

    @Override
    public void deleteBatchByRoleId(List<Long> roleIds) {
        baseMapper.delete(Wrappers.<UserRoleDO>lambdaQuery()
                .in(UserRoleDO::getRoleId, roleIds)
        );
    }
}
