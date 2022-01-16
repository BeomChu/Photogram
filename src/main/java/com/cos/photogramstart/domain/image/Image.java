package com.cos.photogramstart.domain.image;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.likes.Likes;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String caption; // 글
    private String postImageUrl; //사진 전송받아서 그 사진을 서버에 특정 폴더에 저장 -db 그 경로를 insert

    @JsonIgnoreProperties("{images}")
    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    //이미지 좋아요
    @JsonIgnoreProperties("image") //무한참조 잡기
    @OneToMany(mappedBy = "image")
    private List<Likes> likes;

    //댓글
    @OrderBy("id DESC")
    @JsonIgnoreProperties({"image"})
    @OneToMany(mappedBy = "image")
    private List<Comment> comments;

    private LocalDateTime createDate;

    @Transient // DB에 칼럼이 만들어지지 않음
    private boolean likeState;

    @Transient
    private int likeCount;

    @PrePersist //insert될때 자동으로 들어감
    public void createDate(){
        this.createDate=LocalDateTime.now();
    }


}
