package cn.hdj.hdjblog.service.impl;

import cn.hdj.hdjblog.constaint.ConfigConstaint;
import cn.hdj.hdjblog.dao.RoleDao;
import cn.hdj.hdjblog.entity.RoleDO;
import cn.hdj.hdjblog.entity.RoleMenuDO;
import cn.hdj.hdjblog.entity.UserRoleDO;
import cn.hdj.hdjblog.model.params.RoleForm;
import cn.hdj.hdjblog.model.vo.ResultVO;
import cn.hdj.hdjblog.service.RoleMenuService;
import cn.hdj.hdjblog.service.RoleService;
import cn.hdj.hdjblog.service.UserRoleService;
import cn.hdj.hdjblog.util.MyWebUtils;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统角色 服务实现类
 * </p>
 *
 * @author hdj
 * @since 2019-06-07
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, RoleDO> implements RoleService {


    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO addRole(RoleForm role) {

        int count = count(Wrappers.<RoleDO>lambdaQuery()
                .and(sql -> sql.eq(RoleDO::getRoleName, role.getRoleName())
                        .or()
                        .eq(RoleDO::getRoleCode, role.getRoleCode())
                )
                .eq(RoleDO::getDeleted, false)
        );
        if (count > 0) {
            return ResultVO.errorJson("角色名称或者角色编码重复！");
        }
        RoleDO roleDO = new RoleDO();
        BeanUtil.copyProperties(role, roleDO);
        Long userId = MyWebUtils.getCurrentUserId();
        roleDO.setCreateBy(userId);
        baseMapper.insert(roleDO);

        Set<Long> menuIds = role.getMenuIds();
        if (menuIds != null && menuIds.size() > 0) {
            roleMenuService.correlationPermissions(roleDO.getId(), menuIds);
        }
        return ResultVO.successJson();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO editRole(Long roleId, RoleForm role) {
        int count = count(
                Wrappers.<RoleDO>lambdaQuery()
                        .ne(RoleDO::getId, roleId)
                        .eq(RoleDO::getRoleName, role.getRoleName())
                        .eq(RoleDO::getDeleted, false)
        );
        if (count > 0) {
            return ResultVO.errorJson("角色名称或者角色编码重复！");
        }
        Long userId = MyWebUtils.getCurrentUserId();

        RoleDO roleDO = new RoleDO();
        BeanUtil.copyProperties(role, roleDO);

        roleDO.setId(roleId);
        roleDO.setCreateBy(userId);
        baseMapper.updateById(roleDO);

        //先删除角色与菜单关系
        roleMenuService.remove(Wrappers.<RoleMenuDO>lambdaQuery()
                .eq(RoleMenuDO::getRoleId, roleId));

        Set<Long> menuIds = role.getMenuIds();
        if (menuIds != null && menuIds.size() > 0) {
            roleMenuService.correlationPermissions(roleDO.getId(), menuIds);
        }
        return ResultVO.successJson();
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(List<Long> roleIds) {
        //删除角色
        baseMapper.update(null, Wrappers.<RoleDO>lambdaUpdate()
                .in(RoleDO::getId, roleIds)
                .set(RoleDO::getDeleted, true)
        );

        //删除角色与菜单关联
        roleMenuService.deleteBatchByRoleId(roleIds);

        //删除角色与用户关联
        userRoleService.deleteBatchByRoleId(roleIds);
    }

    @Override
    public Set<Long> queryRoleIdList(Long userId) {
        if (ConfigConstaint.SUPER_ADMIN.equals(userId)) {
            return baseMapper.selectList(null).stream()
                    .map(r -> r.getId())
                    .collect(Collectors.toSet());
        }
        return userRoleService.list(Wrappers.<UserRoleDO>lambdaQuery()
                .select(UserRoleDO::getRoleId)
                .eq(UserRoleDO::getUserId, userId)
        ).stream().map(ur -> ur.getRoleId()).collect(Collectors.toSet());
    }

    @Override
    public ResultVO roleInfo(Long roleId) {
        RoleDO roleDO = baseMapper.selectById(roleId);
        RoleForm form = new RoleForm();
        BeanUtil.copyProperties(roleDO, form);
        List<RoleMenuDO> list = roleMenuService.list(Wrappers.<RoleMenuDO>lambdaQuery()
                .select(RoleMenuDO::getMenuId)
                .eq(RoleMenuDO::getRoleId, form.getId())
        );
        if (list != null && list.size() > 0) {
            Set<Long> set = list.stream()
                    .map(v -> v.getMenuId())
                    .collect(Collectors.toSet());
            form.setMenuIds(set);
        }
        return ResultVO.successJson(form);
    }
}
