package com.simple.demospringsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
// 开启SpringSecurity的注解
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class DemoSpringsecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSpringsecurityApplication.class, args);
    }

}
