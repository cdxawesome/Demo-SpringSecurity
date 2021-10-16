package com.simple.demospringsecurity.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private String url;

    public MyAuthenticationSuccessHandler(String url) {
        this.url = url;
    }

    // 自定义登陆成功跳转逻辑
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        //// 获取user对象，就是我们在自定义登陆逻辑那里返回的user
        //User user = (User) authentication.getPrincipal();
        //System.out.println(user.getUsername());
        //System.out.println(user.getPassword());
        //System.out.println(user.getAuthorities());

        // 使用重定向跳转页面
        httpServletResponse.sendRedirect(url);
    }
}
