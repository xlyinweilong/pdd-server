package com.yin.pddserver.apiusercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yin.pddserver.apiusercenter.service.PowerService;
import com.yin.pddserver.apiusercenter.service.RolePowerService;
import com.yin.pddserver.apiusercenter.vo.in.RolePowerInVo;
import com.yin.pddserver.common.api.user.po.RolePowerPo;
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
@RequestMapping("api/user/role_power")
public class RolePowerController extends BaseController {

    @Autowired
    private RolePowerService rolePowerService;
    @Autowired
    private PowerService powerService;

    @LogAnno("某角色权限")
    @GetMapping("role_powers")
    @AuthAnno(value = LoginPlatformConstant.PC)
    public BaseJson rolePowers(String roleId) throws Exception {
        return BaseJson.getSuccess(rolePowerService.list(new QueryWrapper<RolePowerPo>()
                .lambda().eq(RolePowerPo::getRoleId, roleId)));
    }

    @LogAnno("保存角色权限")
    @PostMapping("save")
    @AuthAnno(value = LoginPlatformConstant.PC)
    @Transactional(rollbackFor = Throwable.class)
    public BaseJson save(@RequestBody @Validated RolePowerInVo vo) throws Exception {
        rolePowerService.remove(new QueryWrapper<RolePowerPo>()
                .lambda().eq(RolePowerPo::getRoleId, vo.getRoleId()));
        if (!vo.getPowers().isEmpty()) {
            rolePowerService.saveBatch(vo.getPowers().stream()
                    .map(pk -> new RolePowerPo(vo.getRoleId(), pk)).collect(Collectors.toList()));
        }
        powerService.resetAllCache();
        return BaseJson.getSuccess();
    }

}
