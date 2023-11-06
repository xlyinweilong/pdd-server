package com.yin.pddserver.apiusercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yin.pddserver.apiusercenter.service.PowerService;
import com.yin.pddserver.apiusercenter.service.RolePowerService;
import com.yin.pddserver.apiusercenter.service.RoleService;
import com.yin.pddserver.apiusercenter.service.UserRoleService;
import com.yin.pddserver.apiusercenter.vo.in.RolePageInVo;
import com.yin.pddserver.common.api.user.po.RolePo;
import com.yin.pddserver.common.api.user.po.RolePowerPo;
import com.yin.pddserver.common.api.user.po.UserRolePo;
import com.yin.pddserver.common.auth.anno.AuthAnno;
import com.yin.pddserver.common.base.controller.BaseController;
import com.yin.pddserver.common.base.vo.in.BaseDeleteIdsVo;
import com.yin.pddserver.common.base.vo.out.BaseJson;
import com.yin.pddserver.common.constant.LoginPlatformConstant;
import com.yin.pddserver.common.exceptions.MessageException;
import com.yin.pddserver.common.log.anno.LogAnno;
import com.yin.pddserver.common.utils.SqlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/user/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private RolePowerService rolePowerService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private PowerService powerService;

    @LogAnno("角色列表")
    @GetMapping("list")
    @AuthAnno(value = LoginPlatformConstant.PC)
    public BaseJson list(RolePageInVo vo) throws Exception {
        QueryWrapper<RolePo> qw = new QueryWrapper<>();
        SqlUtils.notBlankEq(qw, "name", vo.getName());
        qw.lambda().orderByDesc(RolePo::getCreateDate);
        return BaseJson.getSuccess(roleService.page(vo.getPage(), qw));
    }

    @LogAnno("全部角色")
    @GetMapping("all")
    @AuthAnno(value = LoginPlatformConstant.PC)
    public BaseJson all(RolePageInVo vo) throws Exception {
        QueryWrapper<RolePo> qw = new QueryWrapper<>();
        SqlUtils.notBlankEq(qw, "name", vo.getName());
        qw.lambda().orderByDesc(RolePo::getCreateDate);
        return BaseJson.getSuccess(roleService.list(qw));
    }

    @LogAnno("保存角色")
    @PostMapping("save")
    @AuthAnno(value = LoginPlatformConstant.PC)
    @Transactional(rollbackFor = Throwable.class)
    public BaseJson save(@RequestBody @Validated RolePo po) throws Exception {
        //判断角色是否存在
        QueryWrapper<RolePo> qw = new QueryWrapper<>();
        SqlUtils.notBlankNe(qw, "id", po.getId());
        if (roleService.getOne(qw.lambda().eq(RolePo::getName, po.getName())) != null) {
            throw new MessageException("角色名称已经存在");
        }
        roleService.saveOrUpdate(po);
        return BaseJson.getSuccess();
    }

    @LogAnno("删除角色")
    @PostMapping("delete")
    @AuthAnno(value = LoginPlatformConstant.PC)
    @Transactional(rollbackFor = Throwable.class)
    public BaseJson delete(@RequestBody @Validated BaseDeleteIdsVo vo) throws Exception {
        roleService.removeByIds(vo.getIds());
        if (!vo.getIds().isEmpty()) {
            //角色下的用户关系
            userRoleService.remove(new QueryWrapper<UserRolePo>().lambda().in(UserRolePo::getRoleId, vo.getIds()));
            //角色下的权限关系
            rolePowerService.remove(new QueryWrapper<RolePowerPo>().lambda().in(RolePowerPo::getRoleId, vo.getIds()));
        }
        powerService.resetAllCache();
        //TODO 定时任务，清理钉钉的级联数据
        return BaseJson.getSuccess();
    }

}
