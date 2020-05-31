package cn.hdj.hdjblog.controller.admin;


import cn.hdj.hdjblog.aspect.annotation.SysLog;
import cn.hdj.hdjblog.constaint.ConfigConstaint;
import cn.hdj.hdjblog.entity.RoleDO;
import cn.hdj.hdjblog.model.params.RoleForm;
import cn.hdj.hdjblog.model.params.RoleSearchForm;
import cn.hdj.hdjblog.model.vo.PageVO;
import cn.hdj.hdjblog.model.vo.ResultVO;
import cn.hdj.hdjblog.service.RoleService;
import cn.hdj.hdjblog.util.MyWebUtils;
import cn.hdj.hdjblog.validator.ValidatorUtils;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 系统角色 前端控制器
 * </p>
 *
 * @author hdj
 * @since 2019-06-07
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/api/admin/role")
public class RoleController  {

    @Autowired
    private RoleService service;

    @SysLog("下拉角色列表")
    @GetMapping(value = "/selectList")
    @ApiOperation(value = "下拉角色列表", httpMethod = "GET", response = ResultVO.class)
    public ResultVO selectList() {
        LambdaQueryWrapper<RoleDO> query = Wrappers.<RoleDO>lambdaQuery();
        Long currentUserId = MyWebUtils.getCurrentUserId();
        //如果不是超级管理员，则只查询自己所拥有的角色列表
        if (!ConfigConstaint.SUPER_ADMIN.equals(currentUserId)) {
            query.eq(RoleDO::getCreateBy, currentUserId);
        }
        return ResultVO.successJson(
                service.list(query.eq(RoleDO::getDeleted, false))
        );
    }

    @RequiresPermissions("sys:role:search")
    @SysLog("角色列表(分页)")
    @GetMapping(value = "/list")
    @ApiOperation(value = "角色列表", httpMethod = "GET", response = ResultVO.class)
    public ResultVO list(@ApiParam RoleSearchForm roleForm) {

        LambdaQueryWrapper<RoleDO> query = Wrappers.<RoleDO>lambdaQuery();
        Long currentUserId = MyWebUtils.getCurrentUserId();
        //如果不是超级管理员，则只查询自己所拥有的角色列表
        if (!ConfigConstaint.SUPER_ADMIN.equals(currentUserId)) {
            query.eq(RoleDO::getCreateBy, currentUserId);
        }

        IPage<RoleDO> page1 = service.page(
                roleForm.getIPage(),
                query.eq(RoleDO::getDeleted, false)
                        .like(
                                StrUtil.isNotBlank(roleForm.getRoleName()),
                                RoleDO::getRoleName,
                                roleForm.getRoleName()
                        )
        );
        return ResultVO.successJson(PageVO.build(page1));
    }


    @RequiresPermissions("sys:role:info")
    @SysLog("角色详情")
    @GetMapping(value = "/info/{roleId}")
    @ApiOperation(value = "角色详情", httpMethod = "GET", response = ResultVO.class)
    public ResultVO roleInfo(@PathVariable("roleId") Long roleId) {
        return service.roleInfo(roleId);
    }


    @RequiresPermissions("sys:role:add")
    @SysLog("添加角色")
    @PostMapping(value = "/add")
    @ApiOperation(value = "添加角色", httpMethod = "POST", response = ResultVO.class)
    public ResultVO addRole(@RequestBody RoleForm role) {
        ValidatorUtils.validateEntity(role);
        return service.addRole(role);
    }


    @RequiresPermissions("sys:role:edit")
    @SysLog("编辑角色")
    @PutMapping(value = "/edit/{roleId}")
    @ApiOperation(value = "编辑角色", httpMethod = "PUT", response = ResultVO.class)
    public ResultVO editRole(@PathVariable("roleId") Long roleId, @RequestBody RoleForm role) {
        ValidatorUtils.validateEntity(role);
        return service.editRole(roleId, role);
    }


    @RequiresPermissions("sys:role:delete")
    @SysLog("删除角色")
    @DeleteMapping(value = "/delete")
    @ApiOperation(value = "批量删除角色", httpMethod = "DELETE", response = ResultVO.class)
    public ResultVO deleteBatch(@RequestBody List<Long> roleIds) {
        service.deleteBatch(roleIds);
        return ResultVO.successJson();
    }

}
