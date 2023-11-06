package com.yin.pddserver.apiusercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yin.pddserver.apiusercenter.service.PowerService;
import com.yin.pddserver.apiusercenter.service.UserService;
import com.yin.pddserver.apiusercenter.vo.in.LoginInVo;
import com.yin.pddserver.apiusercenter.vo.in.ResetPasswordInVo;
import com.yin.pddserver.apiusercenter.vo.out.LoginOutVo;
import com.yin.pddserver.common.api.user.po.UserPo;
import com.yin.pddserver.common.auth.LoginUser;
import com.yin.pddserver.common.auth.anno.AuthAnno;
import com.yin.pddserver.common.auth.anno.IpLimitAnno;
import com.yin.pddserver.common.auth.anno.UserAnno;
import com.yin.pddserver.common.auth.service.IpLimitService;
import com.yin.pddserver.common.base.controller.BaseController;
import com.yin.pddserver.common.base.vo.out.BaseJson;
import com.yin.pddserver.common.constant.LoginPlatformConstant;
import com.yin.pddserver.common.exceptions.LoginException;
import com.yin.pddserver.common.exceptions.MessageException;
import com.yin.pddserver.common.log.anno.LogAnno;
import com.yin.pddserver.common.store.ThreadStore;
import com.yin.pddserver.common.utils.JwtUtil;
import com.yin.pddserver.common.utils.MD5Util;
import com.yin.pddserver.common.vaptcha.CaptchaVerifyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("api/user")
@Slf4j
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private PowerService powerService;
    @Autowired
    private IpLimitService ipLimitService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private Integer verifyCount = 5;


    @PostMapping("login")
    @LogAnno("PC登录")
    @IpLimitAnno
    public BaseJson login(@Validated @RequestBody LoginInVo loginInVo) throws Exception {
        this.checkCaptcha(loginInVo.getCaptcha());
        UserPo userPo = userService.getOne(new QueryWrapper<UserPo>().lambda().eq(UserPo::getUsername, loginInVo.getUsername()));
        if (userPo == null) {
            throw new LoginException("账号或密码错误");
        }
        if (!userPo.getPassword().equals(loginInVo.getPassword())) {
            throw new LoginException("账号或密码错误");
        }
        if (userPo.getIsLeave()) {
            throw new LoginException("账号已经离职");
        }
        if (userPo.getDisabled()) {
            throw new LoginException("账号已被禁用");
        }
        //查询权限
        List<String> powers = powerService.loadUserPowerKey(userPo.getId(), userPo.getIsSuper());
        String token = JwtUtil.createJWT(new LoginUser(LoginPlatformConstant.PC, userPo.getId(), userPo.getUsername(), ThreadStore.getTnId(), MD5Util.md5(userPo.getPassword())));
        return BaseJson.getSuccess("登录成功", LoginOutVo.builder().name(userPo.getName()).token(token).powers(powers).build());
    }

    /**
     * 验证验证码
     *
     * @param captcha
     * @throws Exception
     */
    private void checkCaptcha(String captcha) throws Exception {
        String ip = ThreadStore.getIp();
        if (ipLimitService.get(ip) > verifyCount) {
            if (StringUtils.isBlank(captcha) || !CaptchaVerifyUtils.verify(captcha, ip)) {
//                throw new MessageException("验证码效验失败");
            }
        }
    }

    /**
     * 是否需要校验
     *
     * @return
     * @throws Exception
     */
    @GetMapping("is_verify")
    @IpLimitAnno
    public BaseJson isVerify() throws Exception {
        String ip = ThreadStore.getIp();
        return BaseJson.getSuccess(ipLimitService.get(ip) > verifyCount);
    }


    /**
     * 获取用户信息
     *
     * @return
     * @throws Exception
     */
    @GetMapping("my_info")
    @LogAnno("获取用户信息")
    @AuthAnno(value = LoginPlatformConstant.PC)
    public BaseJson myInfo(@UserAnno LoginUser loginUser) throws Exception {
        UserPo userPo = userService.getById(ThreadStore.getTnId(), loginUser.getId());
        List<String> powers = powerService.loadUserPowerKey(userPo.getId(), userPo.getIsSuper());
        return BaseJson.getSuccess("操作成功", LoginOutVo.builder().name(userPo.getName()).token(loginUser.getSecret()).powers(powers).build());
    }

    /**
     * 修改密码
     *
     * @param loginUser
     * @param vo
     * @return
     * @throws Exception
     */
    @PostMapping("reset_password")
    @LogAnno("修改密码")
    @AuthAnno(value = LoginPlatformConstant.PC)
    @Transactional(rollbackFor = Throwable.class)
    public BaseJson resetPassword(@UserAnno LoginUser loginUser, @RequestBody @Validated ResetPasswordInVo vo) throws Exception {
        UserPo userPo = userService.getById(ThreadStore.getTnId(), loginUser.getId());
        if (!vo.getOldPasswd().equals(userPo.getPassword())) {
            throw new MessageException("原密码错误");
        }
        userPo.setPassword(vo.getNewPasswd());
        userService.saveOrUpdate(ThreadStore.getTnId(), userPo);
        return BaseJson.getSuccess();
    }


    /**
     * 登出
     *
     * @param loginUser
     * @return
     * @throws Exception
     */
    @PostMapping("logout")
    @LogAnno("登出")
    @AuthAnno(value = LoginPlatformConstant.PC)
    public BaseJson logout(@UserAnno LoginUser loginUser) throws Exception {
        return BaseJson.getSuccess();
    }

    @PostMapping("report_authc")
    @LogAnno("报表授权")
    @AuthAnno(value = LoginPlatformConstant.PC)
    public BaseJson reportAuthc(@UserAnno LoginUser loginUser) throws Exception {
        String code = UUID.randomUUID().toString().replaceAll("-", "");
        stringRedisTemplate.opsForValue().set("report_auth:" + loginUser.getId(), code, 5, TimeUnit.MINUTES);
        Map map = new HashMap();
        map.put("code", code);
        map.put("appId", loginUser.getId());
        return BaseJson.getSuccess("报表授权成功", map);
    }

    @GetMapping("report_authz")
    @LogAnno("报表鉴权")
    @IpLimitAnno
    public BaseJson reportAuthz(String code, String appId) throws Exception {
        String redisCode = stringRedisTemplate.opsForValue().get("report_auth:" + appId);
        if (redisCode != null && redisCode.equals(code)) {
            stringRedisTemplate.delete("report_auth:" + appId);
            UserPo userPo = userService.getById(appId);
            //查询权限
            List<String> powers = powerService.loadUserPowerKey(userPo.getId(), userPo.getIsSuper());
            String token = JwtUtil.createJWT(new LoginUser(LoginPlatformConstant.PC, userPo.getId(), userPo.getUsername(), ThreadStore.getTnId(), MD5Util.md5(userPo.getPassword())));
            return BaseJson.getSuccess("报表鉴权成功", LoginOutVo.builder().name(userPo.getName()).token(token).powers(powers).build());
        }
        throw new MessageException("授权失败");
    }

}
