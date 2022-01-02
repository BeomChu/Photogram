package com.cos.photogramstart.web;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping({"/image/story" ,"/"})
    public String story(){
        return "/image/story";

    }


    @GetMapping("image/popular")
    public String popular(){
        return "/image/popular";
    }

    @GetMapping("image/upload")
    public String upload(){

        return "/image/upload";
    }

    @PostMapping("/image")
    public String ImageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails){
        if(imageUploadDto.getFile().isEmpty()){
            throw new CustomValidationException("사진을 첨부해야합니다.",null);
        }

        imageService.사진업로드(imageUploadDto,principalDetails);
        return "redirect:/user/"+principalDetails.getUser().getId(); // 유저의 프로필페이지
    }


}
