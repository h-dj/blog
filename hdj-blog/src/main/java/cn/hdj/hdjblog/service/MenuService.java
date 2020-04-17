package cn.hdj.hdjblog.service;

import cn.hdj.hdjblog.entity.MenuDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统菜单 服务类
 * </p>
 *
 * @author hdj
 * @since 2019-06-07
 */
public interface MenuService extends IService<MenuDO> {


    /**
     * 获取用户菜单
     *
     * @param userId
     * @return
     */
    List<MenuDO> listForUser(Long userId);
}
