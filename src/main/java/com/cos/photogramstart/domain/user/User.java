package com.cos.photogramstart.domain.user;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100,unique = true) // Oauth2로그인을 위해 컬럼 늘리기 // 스키마를 바꾸면 create로 바꿔줘야함
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    private String website;
    private String bio;
    @Column(nullable = false)
    private String email;
    private String phone;
    private String gender;

    private String profileImageUrl;
    private String role;

    // 나는 연관관계의 주인이 아니다. 그러므로 테이블에 칼럼을 만들지마.
    // User를 Select할 때 해당 User id로 등록된 image들을 다 가져와
    //ㅣazy = User를 select할때 해당 user id로 등록된 image들을 가져오지마 - 대신 getImages함수가 호출되면 가져와
    // eager = user를 select할때 해당 user id로 등록된 image들을 전부 join해서 가 져와
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"user"})
    private List<Image> images;


    private LocalDateTime createDate;

    @PrePersist //insert될때 자동으로 들어감
    public void createDate(){
        this.createDate=LocalDateTime.now();
    }

}
