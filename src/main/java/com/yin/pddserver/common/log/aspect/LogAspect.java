package com.yin.pddserver.common.log.aspect;

import com.alibaba.fastjson.JSONArray;
import com.yin.pddserver.common.log.anno.LogAnno;
import com.yin.pddserver.common.log.constant.LogTypeConstant;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.yin.pddserver.common.log.anno.LogAnno)")
    public void logAspect() {
    }

    /**
     * 请求日志
     *
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("logAspect()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
//        String ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        String className = point.getTarget().getClass().getSimpleName();
        String methodName = point.getSignature().getName();
        LogAnno logAnno = this.getLogAnno(point, methodName);
//        String classMethod = className + "." + methodName;
//        String args = this.getArgs(point, logAnno);
        String argsJson = this.getArgsJson(point, logAnno);
        String apiMean = logAnno.value();
        //执行方法
        Object result;
        try {
            result = point.proceed();
            return result;
        } catch (Throwable e) {
            throw e;
        } finally {
            //执行时长(毫秒)
            long time = System.currentTimeMillis() - beginTime;
//        保存日志
//        sysLogService.save(point, time, result, userName, ip, beginTime);
//            log.info("ip:" + ip);
//            log.info("classMethod:" + classMethod);
//            log.info("args:" + args);
//            log.info("argsJson:" + argsJson);
//            log.info("apiMean:" + apiMean);
//            log.info("用时：" + time);
        }
    }


    private LogAnno getLogAnno(ProceedingJoinPoint point, String methodName) throws Exception {
        Class<?> classTarget = point.getTarget().getClass();
        Class<?>[] par = ((MethodSignature) point.getSignature()).getParameterTypes();
        Method objMethod = classTarget.getMethod(methodName, par);
        LogAnno logAnno = objMethod.getAnnotation(LogAnno.class);
        return logAnno;
    }

    private String getArgs(ProceedingJoinPoint point, LogAnno logAnno) {
        if (logAnno.type().equals(LogTypeConstant.FILE)) {
            return "";
        }
        return Arrays.toString(point.getArgs());
    }

    private String getArgsJson(ProceedingJoinPoint point, LogAnno logAnno) {
        if (logAnno.type().equals(LogTypeConstant.FILE)) {
            return "";
        }
        return JSONArray.toJSONString(point.getArgs());
    }

}
