package com.yin.pddserver.common.auth.resolver;

import com.yin.pddserver.common.auth.LoginUser;
import com.yin.pddserver.common.auth.anno.UserAnno;
import com.yin.pddserver.common.auth.interceptor.AuthInterceptor;
import com.yin.pddserver.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class LoginUserResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserAnno.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String token = request.getHeader(AuthInterceptor.TOKEN_KEY);
        Claims claims = JwtUtil.parseJWT(token);
        if (claims == null) {
            return null;
        }
        Class<?> klass = parameter.getParameterType();
        if (klass.isAssignableFrom(LoginUser.class)) {
            return JwtUtil.getLoginUser(claims);
        }
        return null;
    }
}
