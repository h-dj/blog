package cn.hdj.hdjblog.service.impl;

import cn.hdj.hdjblog.dao.RoleMenuDao;
import cn.hdj.hdjblog.entity.RoleMenuDO;
import cn.hdj.hdjblog.service.RoleMenuService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色资源表 服务实现类
 * </p>
 *
 * @author hdj
 * @since 2019-06-07
 */
@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleMenuDao, RoleMenuDO> implements RoleMenuService {

    @Override
    public void deleteBatchByRoleId(List<Long> roleIds) {
        baseMapper.delete(Wrappers.<RoleMenuDO>lambdaQuery()
                .in(RoleMenuDO::getRoleId, roleIds)
        );
    }

    @Override
    public void correlationPermissions(Long roleId, Set<Long> menuIds) {
        List<RoleMenuDO> list = new ArrayList<>(menuIds.size());
        for (Long menuId : menuIds) {
            RoleMenuDO roleMenuDO = new RoleMenuDO();
            roleMenuDO.setRoleId(roleId);
            roleMenuDO.setMenuId(menuId);
            list.add(roleMenuDO);
        }
        this.saveBatch(list);
    }
}
