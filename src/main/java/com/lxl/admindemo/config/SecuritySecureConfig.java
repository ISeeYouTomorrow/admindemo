package com.lxl.admindemo.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * @author xile.lv
 * @since 2020/6/24 15:55
 **/
@Configuration
public class SecuritySecureConfig extends WebSecurityConfigurerAdapter {
    private final String contextPath;
    public SecuritySecureConfig(AdminServerProperties adminServerProperties) {
        this.contextPath = adminServerProperties.getContextPath();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 跨域设置，SpringBootAdmin客户端通过instances注册，见InstancesController
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .ignoringAntMatchers(contextPath+"/instances");
        //静态资源
        http.authorizeRequests().antMatchers(contextPath+"/assets/**").permitAll();
        //所有请求必须通过认证
        http.authorizeRequests().anyRequest().authenticated();
        http.formLogin().loginPage("/login").permitAll();
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login");
        http.httpBasic();
//        super.configure(http);
    }
}
