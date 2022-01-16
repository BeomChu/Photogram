package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Supplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SubscribeRepository subscribeRepository;

    @Value("${file.path}")
    private String uploadFolder;

    @Transactional
    public User 회원프로필사진변경(int principlaId, MultipartFile profileImageFile){
        UUID uuid= UUID.randomUUID(); // uuid
        String imageFileName= uuid + profileImageFile.getOriginalFilename();
        System.out.println("이미지 파일이름: "+ imageFileName);

        Path imageFilePath = Paths.get(uploadFolder+imageFileName);

        //통신 I/O -> 예외 발생할 수 있음, 컴파일 에러는 안되고 런타임때만 예외를 잡을수있음
        try{
            Files.write(imageFilePath, profileImageFile.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
        User userEntity = userRepository.findById(principlaId).orElseThrow(()->{
            throw new CustomApiException("유저를 찾을 수 없습니다.");
        });
        userEntity.setProfileImageUrl(imageFileName);

        return userEntity;

    }



    @Transactional(readOnly = true)
    public UserProfileDto 회원프로필(int pageUserId,int principalId){
        UserProfileDto dto=new UserProfileDto();


        // SELECT * FROM image WHERE userId = :userId; 쿼리는 이렇게하고 우리는 jpa사용
        User userEntity= userRepository.findById(pageUserId).orElseThrow(()-> {
            throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
        });

        dto.setUser(userEntity);
        dto.setImageCount(userEntity.getImages().size());
        dto.setPageOwnerState(pageUserId == principalId); // 1은 페이지 주인 ,-1은 아님
        //3항 연산자 뿌뿌뿌뿌

        int subscribeState = subscribeRepository.mSubScribeState(principalId, pageUserId);
        int subscribeCount = subscribeRepository.mSubScribeCount(pageUserId);

        dto.setSubscribeCount(subscribeCount);
        dto.setSubscribeState(subscribeState == 1);

        //좋아요 카운트 추가하기
        userEntity.getImages().forEach(image -> {
            image.setLikeCount(image.getLikes().size());
        });

        return dto;
    }

    @Transactional
    public User 회원수정(int id, User user){
        //1. 영속화
        User userEntity = userRepository.findById(id).orElseThrow(()->{return new CustomValidationApiException("찾을 수 없는 ID입니다.");});

        // get()::1.무조건 찾았따. 걱정마 get(), 2.못찾았어 익셉션 발동시킬게 orElseThrow
        //2. 영속화된 오브젝트르 수정- 더티체킹 (업데이트완료)

        String rawPassword=user.getPassword();
        String encPassword=bCryptPasswordEncoder.encode(rawPassword);

        userEntity.setName(user.getName());
        userEntity.setPassword(encPassword);
        userEntity.setBio(user.getBio());
        userEntity.setWebsite(user.getWebsite());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());
        return userEntity;

    } //더티체킹이 일어나서 업데이트가 완료됨




}
