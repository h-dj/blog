package cn.hdj.hdjblog.service.impl;

import cn.hdj.hdjblog.constaint.ConfigConstaint;
import cn.hdj.hdjblog.dao.MenuDao;
import cn.hdj.hdjblog.entity.MenuDO;
import cn.hdj.hdjblog.service.MenuService;
import cn.hdj.hdjblog.service.RoleMenuService;
import cn.hdj.hdjblog.service.UserService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统资源 服务实现类
 * </p>
 *
 * @author hdj
 * @since 2019-06-07
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuDao, MenuDO> implements MenuService {

    @Autowired
    private RoleMenuService roleMenuService;


    @Autowired
    private UserService userService;


    @Override
    public List<MenuDO> listForUser(Long userId) {
        //系统管理员，拥有最高权限
        if (ConfigConstaint.SUPER_ADMIN.equals(userId)) {
            return getAllMenuList(null);
        }
        List<Long> menuIdList = userService.findAllMenuForUser(userId);
        return getAllMenuList(menuIdList);
    }

    /**
     * 根据菜单id 查询菜单
     *
     * @param menuIdList
     * @return
     */
    private List<MenuDO> getAllMenuList(List<Long> menuIdList) {
        return list(Wrappers.<MenuDO>lambdaQuery()
                .eq(MenuDO::getDeleted, false)
                .in(CollectionUtil.isNotEmpty(menuIdList), MenuDO::getId, menuIdList)
                .orderByAsc(MenuDO::getSort, MenuDO::getCreateTime)
        );
    }
}
