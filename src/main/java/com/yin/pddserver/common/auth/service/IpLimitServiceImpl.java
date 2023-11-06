package com.yin.pddserver.common.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


/**
 * ip限制服务
 */
@Service
@Slf4j
public class IpLimitServiceImpl implements IpLimitService {

    private final String key = "IpLimitService:";
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取次数
     *
     * @param ip
     * @return
     */
    @Override
    public Integer get(String ip) {
        synchronized (log) {
            Integer times = (Integer) redisTemplate.opsForValue().get(key + ip);
            if (times == null) {
                times = 0;
            }
            return times;
        }
    }

    /**
     * 增加次数
     *
     * @param ip
     * @return
     */
    @Override
    public Integer addTimes(String ip) {
        synchronized (log) {
            Integer times = (Integer) redisTemplate.opsForValue().get(key + ip);
            if (times == null) {
                times = 1;
                redisTemplate.opsForValue().set(key + ip, times, 1, TimeUnit.MINUTES);
            } else {
                times = times + 1;
                redisTemplate.opsForValue().set(key + ip, times, 1, TimeUnit.MINUTES);
            }
            return times;
        }
    }

}
