package cn.hdj.hdjblog.controller.admin;


import cn.hdj.hdjblog.aspect.annotation.SysLog;
import cn.hdj.hdjblog.constaint.ConfigConstaint;
import cn.hdj.hdjblog.constaint.enums.LevelEnum;
import cn.hdj.hdjblog.entity.UserDO;
import cn.hdj.hdjblog.model.params.LoginForm;
import cn.hdj.hdjblog.model.params.UserForm;
import cn.hdj.hdjblog.model.params.UserSearchForm;
import cn.hdj.hdjblog.model.vo.ResultVO;
import cn.hdj.hdjblog.service.UserService;
import cn.hdj.hdjblog.shiro.service.ShiroService;
import cn.hdj.hdjblog.util.MyWebUtils;
import cn.hdj.hdjblog.validator.ValidatorUtils;
import cn.hdj.hdjblog.validator.group.AddGroup;
import cn.hdj.hdjblog.validator.group.UpdateGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统用户 前端控制器
 * </p>
 *
 * @author hdj
 * @since 2019-06-07
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping(value = "/api/admin/user")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private ShiroService shiroService;

    /**
     * 获取当前登陆用户信息
     *
     * @return
     */
    @SysLog("获取当前用户信息")
    @GetMapping(value = "/info")
    @ApiOperation(value = "获取当前用户信息", httpMethod = "GET", response = ResultVO.class)
    public ResultVO getUserInfo() {
        return ResultVO.successJson(MyWebUtils.getCurrentUser());
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @SysLog("获取用户信息")
    @RequiresPermissions("admin:sys:user:info")
    @GetMapping(value = "/info/{userId}")
    @ApiOperation(value = "获取用户信息", httpMethod = "GET", response = ResultVO.class)
    public ResultVO getUserInfo(@PathVariable("userId") Long userId) {
        return ResultVO.successJson(service.getUserInfo(userId));
    }


    /**
     * 获取用户列表
     *
     * @param params
     * @return
     */
    @SysLog("获取用户列表")
    @RequiresPermissions("admin:sys:user:list")
    @GetMapping(value = "/list")
    @ApiOperation(value = "获取用户列表", httpMethod = "GET", response = ResultVO.class)
    public ResultVO listUser(@ApiParam UserSearchForm params) {
        return ResultVO.successJson(service.listUser(params));
    }


    /**
     * 登陆
     *
     * @param user
     * @param response
     * @return
     */
    @SysLog(value = "登陆", ignoreParams = true, logLevel = LevelEnum.LOGIN)
    @PostMapping(value = "/signIn")
    @ApiOperation(value = "登陆", httpMethod = "POST", response = ResultVO.class)
    public ResultVO login(@RequestBody LoginForm user, HttpServletResponse response) {
        response.setHeader(ConfigConstaint.SYSTEM_TOKEN_HEADER, service.login(user));
        return ResultVO.successJson();
    }

    @SysLog("添加用户")
    @RequiresPermissions("admin:sys:user:add")
    @PostMapping(value = "/add")
    @ApiOperation(value = "添加用户", httpMethod = "POST", response = ResultVO.class)
    public ResultVO addUser(@RequestBody UserForm user) {
        ValidatorUtils.validateEntity(user, AddGroup.class);
        service.addUser(user);
        return ResultVO.successJson();
    }

    /**
     * 修改用户
     *
     * @param userId
     * @param user
     * @return
     */
    @SysLog("修改用户")
    @RequiresPermissions("admin:sys:user:edit")
    @PutMapping(value = "/edit/{userId}")
    @ApiOperation(value = "修改用户", httpMethod = "PUT", response = ResultVO.class)
    public ResultVO editUser(@PathVariable("userId") Long userId, @ApiParam @RequestBody UserForm user) {
        ValidatorUtils.validateEntity(user, UpdateGroup.class);
        service.editUser(userId, user);
        return ResultVO.successJson();
    }


    /**
     * 删除多个用户
     *
     * @param userIds
     * @return
     */
    @SysLog("删除用户")
    @RequiresPermissions("admin:sys:user:delete")
    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "删除多个用户", httpMethod = "DELETE", response = ResultVO.class)
    public ResultVO deleteUser(@ApiParam("userIds") @RequestBody List<Long> userIds) {
        if (userIds != null && userIds.size() > 0) {
            service.deleteBatch(userIds);
        }
        return ResultVO.successJson();
    }


    @SysLog("更改profile")
    @PutMapping("/profile")
    @RequiresPermissions("admin:sys:user:profile")
    @ApiOperation(value = "更改profile", httpMethod = "PUT", response = ResultVO.class)
    public ResultVO profile(@RequestBody UserForm userForm) {
        ValidatorUtils.validateEntity(userForm);
        service.profile(userForm);
        return ResultVO.successJson();
    }

    @SysLog("修改头像")
    @PutMapping("/avatar")
    @ApiOperation(value = "修改头像", httpMethod = "PUT", response = ResultVO.class)
    public ResultVO avatar(@ApiParam(value = "头像链接") @RequestBody Map<String, String> params) throws IOException {

        Long currentUserId = MyWebUtils.getCurrentUserId();
        UserDO userDO = new UserDO();
        userDO.setId(currentUserId);
        userDO.setAvatar(params.get("avatar"));
        service.updateById(userDO);
        return ResultVO.successJson();
    }

    @SysLog("退出登录")
    @PutMapping("/logout")
    @ApiOperation(value = "退出登录", httpMethod = "PUT", response = ResultVO.class)
    public ResultVO logout() {
        shiroService.logout();
        return ResultVO.successJson();
    }
}
