package com.yin.pddserver.common.vaptcha;


import com.alibaba.fastjson.JSONObject;
import com.yin.pddserver.common.http.HttpSendUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 验证码辅助工具
 */
@Slf4j
public class CaptchaVerifyUtils {

    private static String id = "5e80427859ec213db68b115f";

    private static String key = "d5ab0df9d2154281b5d364881e63f791";

    public static boolean verify(String captcha, Integer scene, String ip) {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("secretkey", key);
        json.put("scene", scene);
        json.put("ip", ip);
        json.put("token", captcha);
        try {
            JSONObject rs = JSONObject.parseObject(HttpSendUtils.sendPostJson("http://0.vaptcha.com/verify", json));
            return rs.getInteger("success") == 1;
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return true;
    }

    public static boolean verify(String captcha, String ip) {
        return verify(captcha, 0, ip);
    }
}
