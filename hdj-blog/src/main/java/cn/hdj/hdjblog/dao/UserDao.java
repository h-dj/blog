package cn.hdj.hdjblog.dao;

import cn.hdj.hdjblog.entity.UserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author hdj
 * @since 2019-06-07
 */
public interface UserDao extends BaseMapper<UserDO> {

    /**
     * 查找用户全部的菜单id
     *
     * @param userId
     * @return
     */
    List<Long> findAllMenuForUser(@Param("userId")Long userId);

    /**
     * 查询权限
     *
     * @param userId
     * @return
     */
    List<String> queryAllPerms(@Param("userId")Long userId);
}
