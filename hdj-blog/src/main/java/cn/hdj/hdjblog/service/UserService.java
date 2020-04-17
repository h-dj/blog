package cn.hdj.hdjblog.service;

import cn.hdj.hdjblog.entity.UserDO;
import cn.hdj.hdjblog.model.dto.UserDetailDTO;
import cn.hdj.hdjblog.model.params.LoginForm;
import cn.hdj.hdjblog.model.params.UserForm;
import cn.hdj.hdjblog.model.params.UserSearchForm;
import cn.hdj.hdjblog.model.vo.PageVO;
import cn.hdj.hdjblog.model.vo.ResultVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author hdj
 * @since 2019-06-07
 */
public interface UserService extends IService<UserDO> {


    /**
     * 修改密码
     *
     * @param oldPassword
     * @param newPassword
     * @return ResultVO
     */
    ResultVO changePassword(String oldPassword, String newPassword);

    /**
     * 登陆
     *
     * @param user
     * @return
     */
    String login(LoginForm user);

    /**
     * 创建用户
     *
     * @param user
     * @return
     */
    void addUser(UserForm user);

    /**
     * 修改用户
     *
     * @param userId
     * @param user
     * @return
     */
    void editUser(Long userId, UserForm user);


    /**
     * 获取用户详情
     *
     * @param userId
     * @return
     */
    UserDetailDTO getUserInfo(Long userId);

    /**
     * 删除多个用户
     *
     * @param userIds
     * @return
     */
    void deleteBatch(List<Long> userIds);

    /**
     * 查找用户所拥有的菜单id
     *
     * @param userId
     * @return
     */
    List<Long> findAllMenuForUser(Long userId);

    /**
     * 用户列表
     *
     * @param params
     * @return
     */
    PageVO listUser(UserSearchForm params);

    /**
     * 更新profile
     *
     * @param userForm
     */
    void profile(UserForm userForm);
}
