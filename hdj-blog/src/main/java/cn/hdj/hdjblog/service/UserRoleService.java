package cn.hdj.hdjblog.service;

import cn.hdj.hdjblog.entity.UserRoleDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户角色 服务类
 * </p>
 *
 * @author hdj
 * @since 2019-06-07
 */
public interface UserRoleService extends IService<UserRoleDO> {

    /**
     * 删除用户角色关联
     *
     * @param roleIds
     */
    void deleteBatchByRoleId(List<Long> roleIds);
}
