package com.yin.pddserver.common.utils.wechatexpress;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * 微信直播组件接口
 *
 * @author yin.weilong
 * @date 2019.08.31
 */
@Slf4j
public class WeChatLiveUtils {



    /**
     * 【获取直播房间列表】接口，仅供后台调用
     *
     * @param token
     * @param start 起始拉取房间，start=0表示从第1个房间开始拉取
     * @param limit 每次拉取的个数上限，不要设置过大，建议100以内
     * @return
     * @throws Exception
     */
    public static JSONObject getliveinfo(String token, Integer start, Integer limit) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("start", start);
        jsonObject.put("limit", limit);
        String url = "https://api.weixin.qq.com/wxa/business/getliveinfo?access_token=";
        return WeChatUtils.callApi(url, token, jsonObject);
    }

}
