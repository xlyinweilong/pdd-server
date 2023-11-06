package com.yin.pddserver.common.base.lock;

import com.yin.pddserver.common.utils.ClazzUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisTaskLock {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 通过redis获取一个锁
     *
     * @param key
     * @return
     */
    public Boolean getLock(String key) {
        return this.getLock(key, 20L);
    }

    public Boolean getLock(String key, long minutes) {
        return stringRedisTemplate.opsForValue().setIfAbsent(key, key, minutes, TimeUnit.MINUTES);
    }

    public Boolean releaseLock(String key) {
        return stringRedisTemplate.delete(key);
    }

    public Boolean getLock4Shot(Class clazz) {
        //获取直接调用该方法的方法名称
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        String key = ClazzUtils.getRedisLockKey(clazz, methodName);
        return this.getLock(key, 3L);
    }

    public Boolean getLock4Seconds(Class clazz, long seconds) {
        //获取直接调用该方法的方法名称
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        String key = ClazzUtils.getRedisLockKey(clazz, methodName);
        log.info("输出key=" + key);
        return this.getLockSeconds(key, seconds);
    }

    public Boolean getLock(Class clazz) {
        //获取直接调用该方法的方法名称
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        String key = ClazzUtils.getRedisLockKey(clazz, methodName);
        return this.getLock(key);
    }

    public Boolean getLockSeconds(String key, long seconds) {
        boolean b = stringRedisTemplate.opsForValue().setIfAbsent(key, key, seconds, TimeUnit.SECONDS);
        return b;
    }

    public Boolean expireLock(String key, long timeout, TimeUnit unit) {
        return stringRedisTemplate.expire(key, timeout, unit);
    }

}
