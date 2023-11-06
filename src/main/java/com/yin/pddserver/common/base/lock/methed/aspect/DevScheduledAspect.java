package com.yin.pddserver.common.base.lock.methed.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class DevScheduledAspect {

    @Value("${spring.profiles.active}")
    private String profilesActive;

    @Pointcut("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void scheduledAspect() {
    }

    @Around("scheduledAspect()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        if ("dev".equals(profilesActive)) {
            return null;
        }
        return point.proceed();
    }

}
