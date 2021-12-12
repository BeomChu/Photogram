package com.cos.photogramstart.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @GetMapping("/auth/signin")
    public String signinForm() {
        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm() {
        return "auth/signup";
    }

    //회원가입버튼 누르면 post로 오게 jsp에 설정되어있음.
    @PostMapping("/auth/signup")
    public String signup() {
//        System.out.println("signup 실행됨?"); //첫 시도 실행 안됨 //csrf토큰 해제하고 로그인창(signin페이지)로 넘어감
        return "auth/signin";
    }
}
