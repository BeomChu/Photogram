package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Supplier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SubscribeRepository subscribeRepository;



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
