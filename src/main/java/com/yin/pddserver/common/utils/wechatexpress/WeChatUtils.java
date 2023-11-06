package com.yin.pddserver.common.utils.wechatexpress;

import com.alibaba.fastjson.JSONObject;
import com.yin.pddserver.common.exceptions.MessageException;
import com.yin.pddserver.common.http.HttpSendUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yin.weilong
 * @date 2020.02.26
 */
public class WeChatUtils {

    public static JSONObject callApi(String url, String token, JSONObject jsonObject) throws Exception {
        String str = HttpSendUtils.sendPostJson(url + token, jsonObject);
        JSONObject rs = JSONObject.parseObject(str);
        String errcode = rs.getString("errcode");
        if (!"0".equals(errcode)) {
            if(StringUtils.isNotBlank(rs.getString("delivery_resultmsg"))){
                throw new MessageException(rs.getString("delivery_resultmsg"));
            }
            throw new MessageException(rs.getString("errmsg"));
        }
        return rs;
    }

}
