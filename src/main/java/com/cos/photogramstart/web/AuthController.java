package com.cos.photogramstart.web;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor //final에 대한 생성자 만들어줌 (DI)
public class AuthController {

    private static final Logger log= LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

//    //생성자주입
//    public AuthController(AuthService authService){
//        this.authService=authService;
//    }

    @GetMapping("/auth/signin")
    public String signinForm() {return "auth/signin";}

    @GetMapping("/auth/signup")
    public String signupForm() {return "auth/signup";}

    //회원가입버튼 누르면 post로 오게 jsp에 설정되어있음.
    @PostMapping("/auth/signup")
    public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) {  //key=value 형태 (x-www-form-urlencoded)가 default값


            User user= signupDto.toEntity();
            log.info(user.toString());
            authService.회원가입(user);

//        log.info(signupDto.toString());
        //        System.out.println("signup 실행됨?"); //첫 시도 실행 안됨 //csrf토큰 해제하고 fhrmdl
        return "auth/signin";
    }
}
