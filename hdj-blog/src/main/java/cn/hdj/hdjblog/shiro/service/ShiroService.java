package cn.hdj.hdjblog.shiro.service;

import cn.hdj.hdjblog.entity.UserDO;
import cn.hdj.hdjblog.model.dto.UserDetailDTO;

import java.util.Set;

/**
 * @author hdj
 * @date 2019/7/24 23:03
 * @Version 1.0
 * @Description: shiro服务接口
 */
public interface ShiroService {

    /**
     * 生成Token
     *
     * @param account
     * @return
     */
    String generateJwtToken(String account);

    /**
     * 获取Token信息
     *
     * @param account
     * @return
     */
    UserDetailDTO getJwtTokenInfo(String account);

    /**
     * 根据用户名，查询用户信息
     *
     * @param account
     * @return
     */
    UserDO findByUserName(String account);

    /**
     * 获取用户权限
     *
     * @param userId
     * @return
     */
    Set<String> queryAllPerms(Long userId);

    /**
     * 退出登录
     */
    void logout();

    /**
     * 查询全部的角色标识
     *
     * @param userId
     * @return
     */
    Set<String> queryAllRoles(Long userId);
}
