package cn.hdj.hdjblog.service;

import cn.hdj.hdjblog.entity.RoleDO;
import cn.hdj.hdjblog.model.params.RoleForm;
import cn.hdj.hdjblog.model.vo.ResultVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统角色 服务类
 * </p>
 *
 * @author hdj
 * @since 2019-06-07
 */
public interface RoleService extends IService<RoleDO> {


    /**
     * 添加角色
     *
     * @param role
     * @return
     */
    ResultVO addRole(RoleForm role);

    /**
     * 修改角色
     *
     * @param roleId
     * @param role
     * @return
     */
    ResultVO editRole(Long roleId, RoleForm role);

    /**
     * 批量删除角色
     *
     * @param roleIds
     * @return
     */
    void deleteBatch(List<Long> roleIds);

    /**
     * 获取角色ID列表
     *
     * @param userId 用户ID
     * @return
     */
    Set<Long> queryRoleIdList(Long userId);


    /**
     * 获取角色详情
     *
     * @param roleId
     * @return
     */
    ResultVO roleInfo(Long roleId);
}
