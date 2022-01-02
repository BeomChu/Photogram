package com.cos.photogramstart.service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    @Transactional (readOnly = true) // 영속성 컨텍스트에서 변경 감지를 해서 ,더티체킹 , flush(반영) X (리드온리안하면 무조건함)
    public Page<Image> 이미지스토리(int principalId, Pageable pageable){
        Page<Image> images = imageRepository.mStory(principalId,pageable);

        //images에 좋아요 상태담기
        images.forEach((image -> {
            image.getLikes().forEach((like)->{
              if(like.getUser().getId() == principalId){  // 해당 이미지에 좋아요한 사람들을 찾아서 현재 로그인한 사람이 좋아요 한 것인지 비교
                image.setLikeState(true);
              }
            });
        }));
        return  images;
    }

    @Value("${file.path}")
    private String uploadFolder;

    @Transactional
    public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails){
        UUID uuid= UUID.randomUUID();
        String imageFileName= uuid + imageUploadDto.getFile().getOriginalFilename();
        System.out.println("이미지 파일이름: "+ imageFileName);

        Path imageFilePath = Paths.get(uploadFolder+imageFileName);

        //통신 I/O -> 예외 발생할 수 있음, 컴파일 에러는 안되고 런타임때만 예외를 잡을수있음
        try{
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
        // image 테이블에 저장
        Image image = imageUploadDto.toEntity(imageFileName,principalDetails.getUser());
        Image imageEntity = imageRepository.save(image);
    }
}
