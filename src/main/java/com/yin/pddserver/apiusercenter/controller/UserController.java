package com.yin.pddserver.apiusercenter.controller;

import com.yin.pddserver.apiusercenter.service.UserService;
import com.yin.pddserver.common.api.user.po.UserPo;
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
@RequestMapping("api/user/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @LogAnno("用户明细")
    @GetMapping("get_by_id")
    @AuthAnno(value = LoginPlatformConstant.PC)
    public BaseJson getById(String id) throws Exception {
        UserPo user = userService.getById(id);
        return BaseJson.getSuccess(user);
    }

}
