package com.simple.demospringsecurity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class DemoSpringsecurityApplicationTests {

    @Test
    public void contextLoads() {
        PasswordEncoder pw = new BCryptPasswordEncoder();
        // 加密
        String encodePwd = pw.encode("123");
        System.out.println(encodePwd);
        // 校验密码
        boolean matches = pw.matches("123", encodePwd);
        System.out.println(matches);
        System.out.println("============================================");
    }

}
