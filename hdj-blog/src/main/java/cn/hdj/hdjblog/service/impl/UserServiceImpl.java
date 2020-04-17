package cn.hdj.hdjblog.service.impl;

import cn.hdj.hdjblog.constaint.ConfigConstaint;
import cn.hdj.hdjblog.dao.UserDao;
import cn.hdj.hdjblog.entity.UserDO;
import cn.hdj.hdjblog.entity.UserRoleDO;
import cn.hdj.hdjblog.exception.MyException;
import cn.hdj.hdjblog.model.dto.UserDetailDTO;
import cn.hdj.hdjblog.model.params.LoginForm;
import cn.hdj.hdjblog.model.params.UserForm;
import cn.hdj.hdjblog.model.params.UserSearchForm;
import cn.hdj.hdjblog.model.vo.PageVO;
import cn.hdj.hdjblog.model.vo.ResultVO;
import cn.hdj.hdjblog.service.RoleService;
import cn.hdj.hdjblog.service.UserRoleService;
import cn.hdj.hdjblog.service.UserService;
import cn.hdj.hdjblog.shiro.DbToken;
import cn.hdj.hdjblog.shiro.PasswordHelper;
import cn.hdj.hdjblog.shiro.service.ShiroService;
import cn.hdj.hdjblog.util.MyWebUtils;
import cn.hdj.hdjblog.util.RedisUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author hdj
 * @since 2019-06-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserDO> implements UserService {

    @Autowired
    private PasswordHelper passwordHelper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ShiroService shiroService;

    @Override
    public ResultVO changePassword(String oldPassword, String newPassword) {
        UserDetailDTO currentUser = MyWebUtils.getCurrentUser();
        oldPassword = passwordHelper.encryptPassword(oldPassword, currentUser.getSalt());
        if (!StrUtil.equalsIgnoreCase(oldPassword, currentUser.getPassword())) {
            return ResultVO.errorJson("原密码不正确");
        }
        UserDO userDO = new UserDO();
        userDO.setId(currentUser.getId());
        userDO.setPassword(newPassword);
        passwordHelper.encryptPassword(userDO);
        boolean flag = this.updateById(userDO);
        return flag ? ResultVO.successJson() : ResultVO.errorJson();
    }


    @Override
    public String login(LoginForm user) {
        //shiro 执行登陆验证
        Subject subject = SecurityUtils.getSubject();
        DbToken token = new DbToken(user);
        subject.login(token);
        UserDetailDTO account = (UserDetailDTO) subject.getPrincipal();
        this.update(Wrappers.<UserDO>lambdaUpdate()
                .eq(UserDO::getId, account.getId())
                .set(UserDO::getLoginTime, new Date()));
        return shiroService.generateJwtToken(account.getEmail());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addUser(UserForm user) {
        UserDO userDO = new UserDO();
        BeanUtil.copyProperties(user, userDO);
        int count = count(Wrappers.<UserDO>lambdaQuery()
                .eq(UserDO::getEmail, userDO.getEmail())
                .eq(UserDO::getDeleted, false)
        );
        if (count > 0) {
            throw new DuplicateKeyException("邮箱已注册！");
        }
        passwordHelper.encryptPassword(userDO);
        baseMapper.insert(userDO);
        Set<Long> roleIds = user.getRoleIds();
        List<UserRoleDO> userRoleList = new ArrayList<>(roleIds.size());
        for (Long roleId : roleIds) {
            UserRoleDO userRoleDO = new UserRoleDO();
            userRoleDO.setRoleId(roleId);
            userRoleDO.setUserId(userDO.getId());
            userRoleList.add(userRoleDO);
        }
        if (!userRoleList.isEmpty()) {
            userRoleService.saveBatch(userRoleList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void editUser(Long userId, UserForm user) {
        UserDO userDO = new UserDO();
        BeanUtil.copyProperties(user, userDO);
        userDO.setId(userId);


        int count = count(Wrappers.<UserDO>lambdaQuery()
                .eq(UserDO::getEmail, userDO.getEmail())
                .ne(UserDO::getId, userId)
                .eq(UserDO::getDeleted, false)
        );
        if (count > 0) {
            throw new DuplicateKeyException("邮箱已注册！");
        }
        baseMapper.updateById(userDO);
        userRoleService.remove(Wrappers.<UserRoleDO>lambdaQuery().eq(UserRoleDO::getUserId, userId));
        Set<Long> roleIds = user.getRoleIds();
        if (roleIds != null && roleIds.size() > 0) {
            List<UserRoleDO> userRoleList = new ArrayList<>(roleIds.size());
            for (Long roleId : roleIds) {
                UserRoleDO userRoleDO = new UserRoleDO();
                userRoleDO.setRoleId(roleId);
                userRoleDO.setUserId(userDO.getId());
                userRoleList.add(userRoleDO);
            }
            userRoleService.saveBatch(userRoleList);
        }
    }


    @Override
    public UserDetailDTO getUserInfo(Long userId) {
        UserDO userDO = getById(userId);
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        BeanUtil.copyProperties(userDO, userDetailDTO);
        Set<Long> roleIds = roleService.queryRoleIdList(userId);
        userDetailDTO.setRoleIds(roleIds);
        return userDetailDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(List<Long> userIds) {

        if (userIds != null && userIds.contains(ConfigConstaint.SUPER_ADMIN)) {
            throw new MyException("系统管理员不能删除");
        }

        if (userIds != null && userIds.contains(MyWebUtils.getCurrentUserId())) {
            throw new MyException("当前用户不能删除");
        }

        userRoleService.remove(Wrappers.<UserRoleDO>lambdaQuery().in(UserRoleDO::getUserId, userIds));
        baseMapper.update(null, Wrappers.<UserDO>lambdaUpdate()
                .set(UserDO::getDeleted, true)
                .in(UserDO::getId, userIds)
        );
    }

    @Override
    public List<Long> findAllMenuForUser(Long userId) {
        return baseMapper.findAllMenuForUser(userId);
    }


    @Override
    public PageVO listUser(UserSearchForm params) {

        IPage<UserDO> page = page(
                params.getIPage(),
                Wrappers.<UserDO>lambdaQuery()
                        .select(UserDO::getId,
                                UserDO::getEmail,
                                UserDO::getEnable,
                                UserDO::getLocked,
                                UserDO::getUserName,
                                UserDO::getAvatar,
                                UserDO::getRemark,
                                UserDO::getLoginTime)
                        .and(StrUtil.isNotEmpty(params.getKey()),
                                sql -> sql.like(UserDO::getEmail, params.getKey())
                                        .or()
                                        .like(UserDO::getUserName, params.getKey())
                        )
                        .eq(UserDO::getDeleted, false)
        );
        return PageVO.build(page);
    }

    @Override
    public void profile(UserForm userForm) {
        UserDO userDO = new UserDO();
        BeanUtil.copyProperties(userForm, userDO);
        Long currentUserId = MyWebUtils.getCurrentUserId();
        userDO.setId(currentUserId);
        int count = count(Wrappers.<UserDO>lambdaQuery()
                .eq(UserDO::getEmail, userDO.getEmail())
                .ne(UserDO::getId, currentUserId)
                .eq(UserDO::getDeleted, false)
        );
        if (count > 0) {
            throw new DuplicateKeyException("邮箱已注册！");
        }
        if (StrUtil.isNotEmpty(userForm.getPassword())) {
            passwordHelper.encryptPassword(userDO);
        }
        baseMapper.updateById(userDO);
    }
}
