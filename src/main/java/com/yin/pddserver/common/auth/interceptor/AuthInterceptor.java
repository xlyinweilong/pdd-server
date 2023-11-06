package com.yin.pddserver.common.auth.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.yin.pddserver.apiusercenter.service.UserService;
import com.yin.pddserver.common.api.user.po.UserPo;
import com.yin.pddserver.common.auth.anno.AuthAnno;
import com.yin.pddserver.common.auth.anno.IpLimitAnno;
import com.yin.pddserver.common.auth.service.IpLimitService;
import com.yin.pddserver.common.base.vo.out.BaseJson;
import com.yin.pddserver.common.constant.LoginPlatformConstant;
import com.yin.pddserver.common.exceptions.MessageException;
import com.yin.pddserver.common.store.ThreadStore;
import com.yin.pddserver.common.utils.JwtUtil;
import com.yin.pddserver.common.utils.MD5Util;
import io.jsonwebtoken.Claims;
import lombok.Setter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 权限拦截器
 *
 * @author yin.weilong
 * @date 2018.12.21
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    public static final String TOKEN_KEY = "X-Token";

    public static final String TN_ID_KEY = "tn_id";

    @Setter
    private UserService userService;
    @Setter
    private IpLimitService ipLimitService;

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        AuthAnno authAnno = method.getAnnotation(AuthAnno.class);
        IpLimitAnno ipLimitAnno = method.getAnnotation(IpLimitAnno.class);
        String tnId = request.getHeader(TN_ID_KEY);
        String ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        ThreadStore.setIp(ip);
        handlerMethod.getMethod().getParameterAnnotations();
        if (authAnno != null) {
            try {
                this.checkAuth(authAnno, request, response);
                return true;
            } catch (MessageException e) {
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(JSONObject.toJSONString(BaseJson.builder().code(50008).message(e.getMessage()).build()));
                return false;
            }
        } else if (StringUtils.isNotBlank(tnId)) {
            ThreadStore.setTnId(tnId);
        }
        if (StringUtils.isNotBlank(ip) && ipLimitAnno != null && ipLimitService.get(ip) > 100L) {
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(400);
            response.getWriter().write(JSONObject.toJSONString(BaseJson.builder().code(400).message("请求频率受限").build()));
            return false;
        }
        return true;
    }

    private void checkAuth(AuthAnno authAnno, HttpServletRequest request, HttpServletResponse response) throws MessageException {
        String token = request.getHeader(TOKEN_KEY);
        if (StringUtils.isBlank(token)) {
            response.setStatus(403);
            throw new MessageException("请先登录");
        } else {
            Claims claims = JwtUtil.parseJWT(token);
            if (claims == null) {
                response.setStatus(403);
                throw new MessageException("登录状态过期");
            }
            String id = claims.getId();
            String userId = JwtUtil.getUserId(claims);
            String tnId = JwtUtil.getTnId(claims);
            String username = JwtUtil.getUsername(claims);
            String secret = JwtUtil.getUserSecret(claims);
            String platform = JwtUtil.getUserPlatform(claims);
            Date createDate = JwtUtil.getCreateDate(claims);
            if (StringUtils.isAnyBlank(userId, username, secret, platform) || createDate == null) {
                response.setStatus(403);
                throw new MessageException("登录状态过期");
            }
            if (StringUtils.isNotBlank(tnId)) {
                //请求解析出tnId
                ThreadStore.setTnId(tnId);
            } else if (LoginPlatformConstant.PC.equals(platform) ||
                    LoginPlatformConstant.SMALL_EMPLOY.equals(platform) ||
                    LoginPlatformConstant.SMALL_VIP.equals(platform)) {
                throw new MessageException("非法的平台请求");
            }
            if (!ArrayUtils.contains(authAnno.value(), platform)) {
                response.setStatus(401);
                throw new MessageException("平台权限不足");
            }
            switch (platform) {
                case LoginPlatformConstant.PC:
                    this.checkPc(authAnno, userId, secret);
                    break;
                default:
                    response.setStatus(403);
                    throw new MessageException("登录状态过期");
            }
        }
    }


    /**
     * 检查调用PC平台
     *
     * @param authAnno
     * @param userId
     * @param secret
     * @throws MessageException
     */
    private void checkPc(AuthAnno authAnno, String userId, String secret) throws MessageException {
        UserPo user = userService.getById(ThreadStore.getTnId(), userId);
        if (user == null) {
            throw new MessageException("用户状态没有找到");
        }
        if (!MD5Util.md5(user.getPassword()).equals(secret)) {
            throw new MessageException("密码被修改");
        }
//        if (authAnno.powers() != null && authAnno.powers().length > 0) {
//            List<String> powers = powerFeignClient.userPowers(ThreadStore.getTnId(), userId);
//            if (Arrays.asList(authAnno.powers()).stream().filter(p -> powers.contains(p)).count() == 0) {
//                throw new MessageException("没有接口访问权限");
//            }
//        }
    }

}
