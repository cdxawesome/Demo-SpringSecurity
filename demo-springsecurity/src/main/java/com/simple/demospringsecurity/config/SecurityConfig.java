package com.simple.demospringsecurity.config;

import com.simple.demospringsecurity.handler.MyAccessDeniedHandler;
import com.simple.demospringsecurity.handler.MyAuthenticationFailureHandler;
import com.simple.demospringsecurity.handler.MyAuthenticationSuccessHandler;
import com.simple.demospringsecurity.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PersistentTokenRepository tokenRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表单提交
        http.formLogin()
                // 自定义入参
                .usernameParameter("username123").passwordParameter("password123")
                // 自定义登陆页面
                .loginPage("/login.html")
                // 必须和表单提交的接口一样，去执行自定义的登陆逻辑
                .loginProcessingUrl("/login")
                // 登陆成功后跳转的页面 POST请求
                 .successForwardUrl("/toMain")
                // 自定义登陆成功跳转逻辑
                //.successHandler(new MyAuthenticationSuccessHandler("/main.html"))
                // 登陆失败后跳转的页面 POST请求
                // .failureForwardUrl("/toError");
                // 自定义登陆失败跳转逻辑
                .failureHandler(new MyAuthenticationFailureHandler("/error.html"));

        // 自定义异常处理
        http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);
        // 关闭csrf防护
        http.csrf().disable();

        // 授权
        http.authorizeRequests()
                // 放行login.html ，不需要认证
                .antMatchers("/login.html").permitAll()
                // 放行error.html ，不需要认证
                .antMatchers("/error.html").permitAll()
                // images目录下的所有资源放行
                // .antMatchers("/images/**").permitAll()
                // 所有目录下的jpg后缀的文件都放行
                .antMatchers("/**/*.jpg").permitAll()
                // 权限控制，拥有admin权限才可访问(严格区分大小写)
                //.antMatchers("/main1.html").hasAuthority("admin")
                // 权限控制，拥有[admin,admiN]其中一个权限即可访问
                //.antMatchers("/main1.html").hasAnyAuthority("admin","admiN")
                // 基于角色的权限控制(严格区分大小写)
                //.antMatchers("/main1.html").hasAnyRole("abc")
                // 基于access的访问控制
                //.antMatchers("/main1.html").access("hasRole('abc')")
                // 基于角色的权限控制，有任意一个角色即可访问
                //.antMatchers("/main1.html").hasAnyRole("abc", "abC")
                // 所有请求必须认证才能访问，也就是需要登陆
                .anyRequest().authenticated();
                // 自定义access方法
                //.anyRequest().access("@myServiceImpl.hasPermission(request,authentication)");

        // 设置 记住我
        http.rememberMe()
                // 设置数据源
                .tokenRepository(tokenRepository)
                // 设置过期时间(秒)
                .tokenValiditySeconds(30)
                // 设置自定义登陆逻辑
                .userDetailsService(userDetailsService);

        // 退出
        http.logout()
                // 退出url，默认就是springsecurity提供的/logout
                .logoutUrl("/logout")
                // 退出成功后跳转的url
                .logoutSuccessUrl("/login.html");
    }

    // 自定义token存储方法
    @Bean
    public PersistentTokenRepository tokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository=new JdbcTokenRepositoryImpl();
        // 设置数据源
        jdbcTokenRepository.setDataSource(dataSource);
        // 设置自定建表(数据库自定建表存储登陆信息，第一次启动时开启，第二次要注释掉)
        //jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    // 将PasswordEncoder交给spring容器进行管理
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
