package cn.hdj.hdjblog.controller.admin;


import cn.hdj.hdjblog.aspect.annotation.SysLog;
import cn.hdj.hdjblog.constaint.enums.MenuTypeEnum;
import cn.hdj.hdjblog.entity.MenuDO;
import cn.hdj.hdjblog.exception.MyException;
import cn.hdj.hdjblog.model.vo.ResultVO;
import cn.hdj.hdjblog.service.MenuService;
import cn.hdj.hdjblog.util.MyWebUtils;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 系统资源 前端控制器
 * </p>
 *
 * @author hdj
 * @since 2019-06-07
 */
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/api/admin/menus")
public class MenuController {

    @Autowired
    private MenuService service;

    @SysLog("获取侧边栏导航")
    @GetMapping("/nav")
    @ApiOperation(value = "获取侧边栏导航", httpMethod = "GET", response = ResultVO.class)
    public ResultVO nav() {
        return ResultVO.successJson(service.listForUser(MyWebUtils.getCurrentUserId()));
    }

    @RequiresPermissions("sys:menu:search")
    @SysLog("菜单列表")
    @GetMapping(value = "/all")
    @ApiOperation(value = "菜单列表", httpMethod = "GET", response = ResultVO.class)
    public ResultVO all(String key) {
        List<MenuDO> list = service.list(Wrappers.<MenuDO>lambdaQuery()
                .eq(MenuDO::getDeleted, false)
                .like(StrUtil.isNotEmpty(key), MenuDO::getMenuName, key)
        );
        return ResultVO.successJson(list);
    }

    /**
     * 下拉菜单列表(去除按钮菜单)
     *
     * @return
     */
    @GetMapping(value = "/select")
    @ApiOperation(value = "下拉菜单列表", httpMethod = "GET", response = ResultVO.class)
    public ResultVO select() {
        List<MenuDO> list = service.list(Wrappers.<MenuDO>lambdaQuery()
                .select(MenuDO::getId,MenuDO::getMenuName,MenuDO::getParentId)
                .eq(MenuDO::getDeleted, false)
        );
        return ResultVO.successJson(list);
    }

    @RequiresPermissions("sys:menu:info")
    @SysLog("菜单详情")
    @GetMapping(value = "/info/{menuId}")
    @ApiOperation(value = "菜单详情", httpMethod = "GET", response = ResultVO.class)
    public ResultVO menuIdInfo(@PathVariable("menuId") Long menuId) {
        return ResultVO.successJson(service.getById(menuId));
    }


    @RequiresPermissions("sys:menu:add")
    @SysLog("添加菜单")
    @PostMapping(value = "/add")
    @ApiOperation(value = "添加菜单", httpMethod = "POST", response = ResultVO.class)
    public ResultVO addMenu(@RequestBody MenuDO menu) {
        verifyForm(menu);
        service.save(menu);
        return ResultVO.successJson();
    }

    @RequiresPermissions("sys:menu:edit")
    @SysLog("修改菜单")
    @PutMapping(value = "/edit/{menuId}")
    @ApiOperation(value = "修改菜单", httpMethod = "PUT", response = ResultVO.class)
    public ResultVO editMenu(@PathVariable("menuId") Long menuId, @RequestBody MenuDO menu) {
        verifyForm(menu);
        menu.setId(menuId);
        service.updateById(menu);
        return ResultVO.successJson();
    }

    @RequiresPermissions("sys:menu:delete")
    @SysLog("删除菜单")
    @DeleteMapping(value = "/delete/{menuId}")
    @ApiOperation(value = "删除菜单", httpMethod = "DELETE", response = ResultVO.class)
    public ResultVO deleteRole(@PathVariable("menuId") Long menuId) {

        int count = service.count(Wrappers.<MenuDO>lambdaUpdate()
                .eq(MenuDO::getParentId, menuId)
                .eq(MenuDO::getDeleted, false)
        );
        if (count > 0) {
            return ResultVO.errorJson("请先删除子菜单或按钮");
        }
        service.update(Wrappers.<MenuDO>lambdaUpdate()
                .eq(MenuDO::getId, menuId)
                .or(s -> s.eq(MenuDO::getParentId, menuId))
                .set(MenuDO::getDeleted, true)
        );
        return ResultVO.successJson();
    }


    /**
     * 验证菜单
     *
     * @param menu
     */
    private void verifyForm(MenuDO menu) {
        if (menu == null) {
            throw new MyException("参数错误！");
        }
        if (StrUtil.isEmpty(menu.getMenuName())) {
            throw new MyException("菜单名称不能为空!");
        }
        if (menu.getType() == null) {
            throw new MyException("菜单类型不能为空");
        }
        if (menu.getParentId() == null) {
            throw new MyException("菜单父目录不能为空");
        }
        //菜单
        if (menu.getType() == MenuTypeEnum.MENU.getType()) {
            if (StrUtil.isBlank(menu.getUrl())) {
                throw new MyException("菜单URL不能为空");
            }
        }
        //上级菜单类型
        int parentType = MenuTypeEnum.CATEGORY.getType();
        if (menu.getParentId() != 0) {
            MenuDO parentMenu = service.getById(menu.getParentId());
            parentType = parentMenu.getType();
        }
        //目录、菜单
        if (menu.getType() == MenuTypeEnum.CATEGORY.getType() || menu.getType() == MenuTypeEnum.MENU.getType()) {
            if (parentType != MenuTypeEnum.CATEGORY.getType()) {
                throw new MyException("菜单类型的父菜单只能为目录类型");
            }
        }
        //按钮
        if (menu.getType() == MenuTypeEnum.BUTTON.getType()) {
            if (parentType != MenuTypeEnum.MENU.getType()) {
                throw new MyException("按钮类型的父菜单只能为菜单类型");
            }
            //按钮隐藏
            menu.setHidden(true);
        }
    }

}
