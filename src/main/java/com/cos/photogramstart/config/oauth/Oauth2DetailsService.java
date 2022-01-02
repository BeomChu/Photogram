package com.cos.photogramstart.config.oauth;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class Oauth2DetailsService extends DefaultOAuth2UserService{ // 시큐리티 userservice 서비스에 리턴타입 맞추기 위한 상속

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("서비스됨");
        OAuth2User oAuth2User=super.loadUser(userRequest);
        System.out.println(oAuth2User.getAttributes());

        Map<String,Object> userinfo = oAuth2User.getAttributes();

        String username= "facebook_"+(String) userinfo.get("id");
//        String password= bCryptPasswordEncoder.encode(UUID.randomUUID().toString()); // 이거 에러남 DI의 문제임
        String password= new BCryptPasswordEncoder().encode(UUID.randomUUID().toString()); // 이거 에러남 DI의 문제임 //ioc컨테이너에 들ㅇ록되는 순서 사이클 문제임
        String email=(String) userinfo.get("email");
        String name=(String) userinfo.get("name");

        User userEntity= userRepository.findByUsername(username);

        if (userEntity==null){
            User user= User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .name(name)
                    .role("ROLE_USER")
                    .build();
            return new PrincipalDetails(userRepository.save(userEntity),oAuth2User.getAttributes());
        }else{ //페이스북으로 로그인 되어있음.
            return new PrincipalDetails(userEntity,oAuth2User.getAttributes());
        }
    }
}
