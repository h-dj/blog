package cn.hdj.hdjblog.dao;

import cn.hdj.hdjblog.entity.MenuDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Set;

/**
 * <p>
 * 系统资源 Mapper 接口
 * </p>
 *
 * @author hdj
 * @since 2019-06-07
 */
public interface MenuDao extends BaseMapper<MenuDO> {
    /**
     * 查询权限标识
     *
     * @param roleIds
     * @return
     */
    Set<String> queryPermissionCodeList(Set<Long> roleIds);
}
