package cn.hdj.hdjblog.service.impl;

import cn.hdj.hdjblog.HdjBlogApplicationTests;
import cn.hdj.hdjblog.entity.MenuDO;
import cn.hdj.hdjblog.service.MenuService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author hdj
 * @version 1.0
 * @date 11/1/19 2:08 PM
 * @description:
 */
public class MenuServiceImplTest extends HdjBlogApplicationTests {


    @Autowired
    private MenuService menuService;

    @Test
    public void addMenu() {

        MenuDO menuDO=new MenuDO();
        menuDO.setMenuName("测试");
        menuDO.setParentId(0L);
        menuDO.setType(0);
//        menuDO.setId(-1L);
        menuDO.setComponent("123");
        menuDO.setUrl("123");
        menuService.save(menuDO);
    }
}