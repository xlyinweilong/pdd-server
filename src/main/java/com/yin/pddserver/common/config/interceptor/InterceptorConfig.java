package com.yin.pddserver.common.config.interceptor;

import com.yin.pddserver.apiusercenter.service.UserService;
import com.yin.pddserver.common.auth.interceptor.AuthInterceptor;
import com.yin.pddserver.common.auth.resolver.LoginUserResolver;
import com.yin.pddserver.common.auth.service.IpLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * 拦截器配置
 *
 * @author yin.weilong
 * @date 2018.12.21
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired
    private UserService userService;
    @Autowired
    private IpLimitService ipLimitService;

    @Bean
    public AuthInterceptor getAuthInterceptor() {
        AuthInterceptor auth = new AuthInterceptor();
        auth.setUserService(userService);
        auth.setIpLimitService(ipLimitService);
        return auth;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getAuthInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(new LoginUserResolver());
    }
}
