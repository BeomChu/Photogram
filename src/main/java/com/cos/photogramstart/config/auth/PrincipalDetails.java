package com.cos.photogramstart.config.auth;

import com.cos.photogramstart.domain.user.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {
    private static final Long serialVersionUID= 1L;

    private User user;
    private Map<String,Object> attributes;

    public PrincipalDetails(User user){
        this.user=user;
    }

    public PrincipalDetails(User user,Map<String,Object> attributes) { //오버로딩
        this.user=user;
    }



    //권한은 한개가 아닐 수 있음. 그래서 컬렉션타입으로 리턴하게됨
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {  //권한을 가져오는 함수
//
//        Collection<GrantedAuthority> collector = new ArrayList<>();
//        collector.add(new GrantedAuthority() {        람다식 이전ver
//            @Override
//            public String getAuthority() {
//                return user.getRole();
//            }
//        });
//        return collector;
//    }
    public Collection<? extends GrantedAuthority> getAuthorities() {  //권한을 가져오는

        Collection<GrantedAuthority> collector = new ArrayList<>();
        collector.add(()->{return user.getRole();});              //람다식 ver
        return collector;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes; ///{id:12479823414} , name : 고범수 , email : izekfzha@gmail.com}
    }

    @Override
    public String getName() {
        return (String)attributes.get("name");
    }
}
