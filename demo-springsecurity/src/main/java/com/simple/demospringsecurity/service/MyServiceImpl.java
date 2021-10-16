package com.simple.demospringsecurity.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * 这个是自定义的access逻辑
 */
@Service
public class MyServiceImpl implements MyService {

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        // 得到主体(这里的主体就是我们在自定义登陆逻辑时返回的user)
        Object obj = authentication.getPrincipal();
        // 如果主体的类型是UserDetails，那就转成UserDetails
        if (obj instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) obj;
            //获取权限列表
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            // 如果权限列表中包含了请求uri，则返回true
            return authorities.contains(new SimpleGrantedAuthority(request.getRequestURI()));
        }
        return false;
    }
}
