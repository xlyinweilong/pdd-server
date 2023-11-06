package com.yin.pddserver.apiusercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yin.pddserver.apiusercenter.service.PowerService;
import com.yin.pddserver.apiusercenter.service.UserRoleService;
import com.yin.pddserver.apiusercenter.vo.in.RoleUserInVo;
import com.yin.pddserver.apiusercenter.vo.in.UserRoleInVo;
import com.yin.pddserver.common.api.user.po.UserRolePo;
import com.yin.pddserver.common.auth.anno.AuthAnno;
import com.yin.pddserver.common.base.controller.BaseController;
import com.yin.pddserver.common.base.vo.out.BaseJson;
import com.yin.pddserver.common.constant.LoginPlatformConstant;
import com.yin.pddserver.common.log.anno.LogAnno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;


@RestController
@RequestMapping("api/user/role_user")
public class RoleUserController extends BaseController {

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private PowerService powerService;

    @LogAnno("角色的用户列表")
    @GetMapping("list")
    @AuthAnno(value = LoginPlatformConstant.PC)
    public BaseJson list(String roleId) throws Exception {
        return BaseJson.getSuccess(userRoleService.list(new QueryWrapper<UserRolePo>().lambda()
                .eq(UserRolePo::getRoleId, roleId)));
    }

    @LogAnno("保存角色的用户")
    @PostMapping("save")
    @AuthAnno(value = LoginPlatformConstant.PC)
    @Transactional(rollbackFor = Throwable.class)
    public BaseJson save(@RequestBody @Validated RoleUserInVo vo) throws Exception {
        userRoleService.remove(new QueryWrapper<UserRolePo>().lambda()
                .eq(UserRolePo::getRoleId, vo.getRoleId()));
        if (!vo.getUserIds().isEmpty()) {
            userRoleService.saveBatch(vo.getUserIds().stream().distinct()
                    .map(uid -> new UserRolePo(vo.getRoleId(), uid)).collect(Collectors.toList()));
        }
        powerService.resetAllCache();
        return BaseJson.getSuccess();
    }

    @LogAnno("用户的角色")
    @GetMapping("user_role")
    @AuthAnno(value = LoginPlatformConstant.PC)
    public BaseJson userRoleIds(String userId) throws Exception {
        return BaseJson.getSuccess(userRoleService.list(new QueryWrapper<UserRolePo>().lambda()
                .eq(UserRolePo::getUserId, userId)));
    }

    @LogAnno("保存用户的角色")
    @PostMapping("save_user_role")
    @Transactional(rollbackFor = Throwable.class)
    @AuthAnno(value = LoginPlatformConstant.PC)
    public BaseJson saveUserRole(@RequestBody @Validated UserRoleInVo vo) throws Exception {
        userRoleService.remove(new QueryWrapper<UserRolePo>().lambda()
                .eq(UserRolePo::getUserId, vo.getUserId()));
        if (!vo.getRoleIds().isEmpty()) {
            userRoleService.saveBatch(vo.getRoleIds().stream().distinct()
                    .map(roleId -> new UserRolePo(roleId, vo.getUserId())).collect(Collectors.toList()));
        }
        powerService.resetCache(vo.getUserId());
        return BaseJson.getSuccess();
    }

}
