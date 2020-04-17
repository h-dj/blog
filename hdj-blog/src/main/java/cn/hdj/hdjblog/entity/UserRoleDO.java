package cn.hdj.hdjblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>
 * 用户角色
 * </p>
 *
 * @author hdj
 * @since 2019-08-13
 */
@Data
@TableName("t_user_role")
public class UserRoleDO {

    private static final long serialVersionUID = 1L;
    /**
     * 实体ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;


}
