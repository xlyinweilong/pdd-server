package com.yin.pddserver.common.utils;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * json解析工具
 *
 * @author yin.weilong
 * @date 2018.12.27
 */
public class JsonObjectUtils {

    public static String getString(JSONObject jSONObject, String key) throws JSONException {
        return jSONObject.isNull(key) ? null : jSONObject.getString(key);
    }

    public static Long getLong(JSONObject jSONObject, String key) throws JSONException {
        return jSONObject.isNull(key) ? null : jSONObject.getLong(key);
    }
    public static Integer getInt(JSONObject jSONObject, String key) throws JSONException {
       boolean b=  jSONObject.isNull(key);
        return jSONObject.isNull(key) ? null : jSONObject.getInt(key);
    }
}
