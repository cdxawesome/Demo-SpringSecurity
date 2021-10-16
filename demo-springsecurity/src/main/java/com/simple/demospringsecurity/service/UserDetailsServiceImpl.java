package com.simple.demospringsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1.从数据库查询username，查不到则报异常(这里就不连数据库了，为了测试，直接写死)
        if (!"admin".equals(username)) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 2.比较密码
        // 一般情况下是需要查询数据库，将数据库的密码(加密保存的)和传进来的密码进行比较
        String encodePwd = passwordEncoder.encode("123");
        // AuthorityUtils是一个工具类，这里直接指定该user的权限列表，并且拥有的角色是abs(ROLE_ 是固定写法)
        // 入参的GrantedAuthority接口我们也可以自己实现。然后这里直接new我们的实现类即可
        return new User(username, encodePwd, AuthorityUtils.commaSeparatedStringToAuthorityList("admin,normal,ROLE_abc,/main.html"));
    }
}
