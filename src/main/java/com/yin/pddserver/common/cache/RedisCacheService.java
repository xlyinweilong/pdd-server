package com.yin.pddserver.common.cache;

import com.alibaba.fastjson.JSONObject;
import com.yin.pddserver.common.store.ThreadStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisCacheService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Object get(String key, Class clazz) {
        String k = ThreadStore.getTnId() + ":" + key;
        if (stringRedisTemplate.hasKey(k)) {
            return JSONObject.parseObject(stringRedisTemplate.opsForValue().get(k), clazz);
        }
        return null;
    }

    public void set(String key, Object o) {
        String k = ThreadStore.getTnId() + ":" + key;
        stringRedisTemplate.opsForValue().set(k, JSONObject.toJSONString(o), 2, TimeUnit.HOURS);
    }

}
