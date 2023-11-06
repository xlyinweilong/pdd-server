package com.yin.pddserver.apiusercenter.controller;

import com.yin.pddserver.apiusercenter.service.PowerService;
import com.yin.pddserver.common.auth.anno.AuthAnno;
import com.yin.pddserver.common.base.controller.BaseController;
import com.yin.pddserver.common.base.vo.out.BaseJson;
import com.yin.pddserver.common.constant.LoginPlatformConstant;
import com.yin.pddserver.common.log.anno.LogAnno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/user/power")
public class PowerController extends BaseController {

    @Autowired
    private PowerService powerService;


    @LogAnno("全部权限")
    @GetMapping("all")
    @AuthAnno(LoginPlatformConstant.PC)
    public BaseJson all() throws Exception {
        return BaseJson.getSuccess(powerService.getAll());
    }

}
