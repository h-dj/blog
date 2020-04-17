package cn.hdj.hdjblog.service;

import cn.hdj.hdjblog.entity.RoleMenuDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色资源表 服务类
 * </p>
 *
 * @author hdj
 * @since 2019-06-07
 */
public interface RoleMenuService extends IService<RoleMenuDO> {

    /**
     * 通过角色id删除角色菜单关联
     *
     * @param roleIds
     * @return
     */
    void deleteBatchByRoleId(List<Long> roleIds);

    /**
     * 关联角色与权限
     *
     * @param roleId
     * @param menuIds
     */
    void correlationPermissions(Long roleId, Set<Long> menuIds);
}
