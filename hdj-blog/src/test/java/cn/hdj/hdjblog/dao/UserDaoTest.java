package cn.hdj.hdjblog.dao;

import cn.hdj.hdjblog.entity.UserDO;
import cn.hdj.hdjblog.shiro.PasswordHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author hdj
 * @ClassName: cn.hdj.hdjblog.dao
 * @Description: TODO
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordHelper passwordHelper;
    @Test
    public void getUserByName() {
//        UserDO userByName = userDao.getUserByName("hdj");
//        System.out.println(userByName);
    }

    @Test
    public void pwdTest(){
        UserDO userDO=new UserDO();
        userDO.setPassword("123");
        passwordHelper.encryptPassword(userDO);
        System.out.println(userDO);
    }
}