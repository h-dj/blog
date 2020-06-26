package cn.hdj.hdjblog.shiro.service.impl;

import cn.hdj.hdjblog.constaint.ConfigConstaint;
import cn.hdj.hdjblog.dao.MenuDao;
import cn.hdj.hdjblog.dao.RoleDao;
import cn.hdj.hdjblog.dao.UserDao;
import cn.hdj.hdjblog.dao.UserRoleDao;
import cn.hdj.hdjblog.entity.MenuDO;
import cn.hdj.hdjblog.entity.RoleDO;
import cn.hdj.hdjblog.entity.UserDO;
import cn.hdj.hdjblog.entity.UserRoleDO;
import cn.hdj.hdjblog.model.dto.UserDetailDTO;
import cn.hdj.hdjblog.shiro.service.ShiroService;
import cn.hdj.hdjblog.util.JwtUtils;
import cn.hdj.hdjblog.util.MyWebUtils;
import cn.hdj.hdjblog.util.RedisUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author hdj
 * @date 2019/7/24 23:04
 * @Version 1.0
 * @Description:
 */
@Service
public class ShiroServiceImpl implements ShiroService {


    @Autowired
    private UserDao userDao;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public String generateJwtToken(String account) {
        String tokenSalt = JwtUtils.generateSalt();
        redisUtils.set(JwtUtils.genKey(account), tokenSalt, ConfigConstaint.SYSTEM_TOKEN_TIMEOUT);
        return JwtUtils.sign(account, tokenSalt, ConfigConstaint.SYSTEM_TOKEN_TIMEOUT);
    }

    @Override
    public UserDetailDTO getJwtTokenInfo(String account) {
        String tokenSalt = redisUtils.get(JwtUtils.genKey(account));
        UserDO userDO = findByUserName(account);
        UserDetailDTO user = new UserDetailDTO();
        BeanUtil.copyProperties(userDO, user);
        if (userDO != null && StrUtil.isNotEmpty(tokenSalt)) {
            user.setTokenSalt(tokenSalt);
            return user;
        }
        return null;
    }

    @Override
    public UserDO findByUserName(String account) {
        UserDO userDO = userDao.selectOne(Wrappers.<UserDO>lambdaQuery()
                .eq(UserDO::getEmail, account)
                .eq(UserDO::getDeleted, false)
        );
        return userDO;
    }

    @Override
    public Set<String> queryAllPerms(Long userId) {
        List<String> permsList;
        //超级用户查询全部权限
        if (ConfigConstaint.SUPER_ADMIN.equals(userId)) {
            permsList = menuDao.selectList(Wrappers.<MenuDO>lambdaQuery()
                    .eq(MenuDO::getDeleted, false)
            ).stream()
                    .map(menuDO -> menuDO.getPermission())
                    .collect(Collectors.toList());
        } else {
            permsList = userDao.queryAllPerms(userId);
        }
        return permsList.stream()
                // 过滤空置的字符串
                .filter(perms -> StrUtil.isNotEmpty(perms))
                // 转换成set集合
                .collect(Collectors.toSet());
    }

    @Override
    public void logout() {
        redisUtils.expire(JwtUtils.genKey(MyWebUtils.getCurrentUserName()), 0);
    }

    @Override
    public Set<String> queryAllRoles(Long userId) {
        if (ConfigConstaint.SUPER_ADMIN.equals(userId)) {
            return roleDao.selectList(null).stream()
                    .map(RoleDO::getRoleCode)
                    .collect(Collectors.toSet());
        }

        Set<Long> roleIds = userRoleDao.selectList(Wrappers.<UserRoleDO>lambdaQuery()
                .select(UserRoleDO::getRoleId)
                .eq(UserRoleDO::getUserId, userId)
        ).stream().map(ur -> ur.getRoleId()).collect(Collectors.toSet());

        return roleDao.selectBatchIds(roleIds)
                .stream()
                .map(RoleDO::getRoleCode)
                .collect(Collectors.toSet());


    }
}
