package com.yin.pddserver.common.cache;

import com.alibaba.fastjson.JSONObject;
import com.yin.pddserver.common.utils.ClazzUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermanentCacheService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Object getObject(String key, Class clazz) {
        if (stringRedisTemplate.hasKey(key)) {
            return JSONObject.parseObject(stringRedisTemplate.opsForValue().get(key), clazz);
        }
        return null;
    }

    public List getList(Class clazz) {
        return this.getList(ClazzUtils.getClassName(clazz), clazz);
    }

    public List getList(String key, Class clazz) {
        if (stringRedisTemplate.hasKey(key)) {
            return JSONObject.parseArray(stringRedisTemplate.opsForValue().get(key), clazz);
        }
        return null;
    }

    public void setList(String key, List o) {
        stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(o));
    }

    public void setObject(String key, Object o) {
        stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(o));
    }
}
