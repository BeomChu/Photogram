package com.cos.photogramstart.web;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {



    @GetMapping("/user/{id}")
    public String profile(@PathVariable int id){
        System.out.println("실행됨");
        return "user/profile";
    }

    @GetMapping("/user/{id}/update")
    public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println("세션 정보:"+ principalDetails.getUser());

        //극혐인데 뜯어봐야함 중요한듯.          ★ㅁ8★ㅁ8
//        Authentication auth= SecurityContextHolder.getContext().getAuthentication(); //중요함 검색해보기
//        PrincipalDetails mPrincipalDetails = (PrincipalDetails) auth.getPrincipal();
//        System.out.println("직접 찾은 세션 정보: "+ mPrincipalDetails.getUser());

//        model.addAttribute("principal" , principalDetails.getUser());


        return "/user/update";
    }





}
