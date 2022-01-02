package com.cos.photogramstart.abebebe;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LupeeController {

    @GetMapping("/lupee/test/getid")
    public String pracetice(@AuthenticationPrincipal PrincipalDetails principalDetails){
        String result = principalDetails.getUser().toString();

        return result;
    }
}
