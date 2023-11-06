package com.yin.pddserver.common.auth;


import com.yin.pddserver.common.constant.LoginPlatformConstant;
import com.yin.pddserver.common.utils.GenerateUtil;
import com.yin.pddserver.common.utils.JwtUtil;
import com.yin.pddserver.common.utils.MD5Util;

/**
 * 生成Feign专用的token
 */
public class FeignTokenProducer {

    private FeignTokenProducer() {
    }

    private static String token;

    public static String getToken() {
        if (token != null) {
            return token;
        }
        String id = GenerateUtil.createUUID();
        String username = MD5Util.md5(GenerateUtil.createUUID());
        token = JwtUtil.createJWT(new LoginUser(LoginPlatformConstant.FEIGN, id, username, null, MD5Util.md5(id + username)));
        return token;
    }
}
