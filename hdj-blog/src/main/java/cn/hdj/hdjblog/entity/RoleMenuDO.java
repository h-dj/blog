package cn.hdj.hdjblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>
 * 角色资源表
 * </p>
 *
 * @author hdj
 * @since 2019-08-13
 */
@Data
@TableName("t_role_menu")
public class RoleMenuDO {

    private static final long serialVersionUID = 1L;
    /**
     * 实体ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 资源ID
     */
    private Long menuId;


}
