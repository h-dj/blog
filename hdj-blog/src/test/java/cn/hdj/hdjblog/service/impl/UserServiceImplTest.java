package cn.hdj.hdjblog.service.impl;

import cn.hdj.hdjblog.HdjBlogApplicationTests;
import cn.hdj.hdjblog.entity.UserDO;
import cn.hdj.hdjblog.model.params.UserSearchForm;
import cn.hdj.hdjblog.model.vo.PageVO;
import cn.hdj.hdjblog.service.UserService;
import cn.hdj.hdjblog.shiro.PasswordHelper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author hdj
 * @Description:
 * @date 10/5/19
 */
public class UserServiceImplTest extends HdjBlogApplicationTests {

    @Autowired
    private UserService userService;


    @Autowired
    PasswordHelper passwordHelper;
    @Test
    public void listUser() {

        PageVO pageVO = userService.listUser(new UserSearchForm());
        System.out.println(pageVO.toString());

        pageVO = userService.listUser(new UserSearchForm());
        System.out.println(pageVO.toString());
    }

    @Test
    public void changePwd(){
        UserDO userDO = new UserDO();
        userDO.setPassword("1");
        passwordHelper.encryptPassword(userDO);
        userService.update(userDO, Wrappers.<UserDO>lambdaUpdate().eq(UserDO::getEmail,"hdj2@qq.com"));
    }
}