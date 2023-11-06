package com.yin.pddserver.common.base.lock.methed.aspect;

import com.yin.pddserver.common.base.lock.RedisTaskLock;
import com.yin.pddserver.common.base.lock.methed.anno.LockMethedAnno;
import com.yin.pddserver.common.exceptions.MessageException;
import com.yin.pddserver.common.store.ThreadStore;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class LockMethedAspect {

    @Autowired
    private RedisTaskLock redisTaskLock;

    @Pointcut("@annotation(com.yin.pddserver.common.base.lock.methed.anno.LockMethedAnno)")
    public void logAspect() {
    }

    /**
     * 方法锁
     *
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("logAspect()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        String className = point.getTarget().getClass().getSimpleName();
        String methodName = point.getSignature().getName();
        LockMethedAnno anno = this.getAnno(point, methodName);
        String key = (ThreadStore.getTnId() == null ? "null" : ThreadStore.getTnId()) + ":" + className + "." + methodName;
        if (StringUtils.isNotBlank(anno.key())) {
            Object[] args = point.getArgs();
            Object arg = args[Integer.parseInt(anno.key().split("\\.")[0].replace("#p", ""))];
            if (anno.key().contains("\\.")) {
                String name = anno.key().split("\\.")[1];
                key = key + ":" + getGetMethod(arg, name);
            } else {
                key = key + ":" + arg;
            }
        }
        //执行方法
        Object result;
        try {
            this.getKey(key, anno);
            result = point.proceed();
            return result;
        } catch (Throwable e) {
            throw e;
        } finally {
            redisTaskLock.releaseLock(key);
        }
    }

    private void getKey(String key, LockMethedAnno anno) throws Exception {
        boolean isGotKey = redisTaskLock.getLockSeconds(key, anno.seconds());
        if (!isGotKey) {
            Thread.sleep(100L);
            this.getKey(key, anno, 0);
        }
    }

    private void getKey(String key, LockMethedAnno anno, int times) throws Exception {
        boolean isGotKey = redisTaskLock.getLockSeconds(key, anno.seconds());
        if (!isGotKey) {
            Thread.sleep(100L);
            if (times == 10 * 60 * 2L) {
                throw new MessageException("发生死锁，请联系管理员");
            }
            this.getKey(key, anno, times + 1);
        }
    }

    private LockMethedAnno getAnno(ProceedingJoinPoint point, String methodName) throws Exception {
        Class<?> classTarget = point.getTarget().getClass();
        Class<?>[] par = ((MethodSignature) point.getSignature()).getParameterTypes();
        Method objMethod = classTarget.getMethod(methodName, par);
        LockMethedAnno anno = objMethod.getAnnotation(LockMethedAnno.class);
        return anno;
    }


    private Object getGetMethod(Object ob, String name) throws Exception {
        Method[] m = ob.getClass().getMethods();
        for (int i = 0; i < m.length; i++) {
            if (("get" + name).toLowerCase().equals(m[i].getName().toLowerCase())) {
                return m[i].invoke(ob);
            }
        }
        return "";
    }


}
