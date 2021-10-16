package com.simple.demospringsecurity.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

//    @RequestMapping("/login")
//    public String login() {
//        System.out.println("执行了login接口");
//        return "redirect:main.html";
//    }

    // Spring Security的注解:拥有abc角色的用户可以访问此接口
    //@Secured("ROLE_abc")
    // PreAuthorize在这个注解中hasRole的值可以用ROLE前缀，也可以不用。但是配置类中不用加前缀
    @PreAuthorize("hasRole('abc')")
    @RequestMapping("/toMain")
    public String main() {
        return "redirect:main.html";
    }

    @RequestMapping("/toError")
    public String error() {
        return "redirect:error.html";
    }
}
