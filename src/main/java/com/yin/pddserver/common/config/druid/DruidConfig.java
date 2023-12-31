package com.yin.pddserver.common.config.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//@Configuration
public class DruidConfig {
    //该注解向bean自动注入对应的属性，属性在配置文件配置
//    @ConfigurationProperties(prefix = "spring.datasource")
//    @Bean
    public DataSource druid() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setStatLogger(new StatLogger());
        return dataSource;
    }

    //配置druid的监控
    //1.配置管理后台的servlet
//    @Bean
    public ServletRegistrationBean statViewServlet() {
        //druid监控页面的url
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String, String> initParams = new HashMap<>();
        initParams.put("loginUsername", "admin");   //登陆用户名
        initParams.put("loginPassword", "123456");  //密码
        initParams.put("allow", "127.0.0.1"); //允许哪些ip
        bean.setInitParameters(initParams);
        return bean;
    }

    //2.配置一个web监控的filter,监控sql
//    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String, String> initParams = new HashMap<>();
        initParams.put("exclusions", "*.js,*.css,*.html,/druid/*");
        bean.setInitParameters(initParams);
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }

}