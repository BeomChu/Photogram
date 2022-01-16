package com.cos.photogramstart.domain.likes;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "likes_uk",
                        columnNames = {"imageId", "userId"}
                )
        }
)
public class Likes {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //무한참조됨
    @ManyToOne
    @JoinColumn(name = "imageId")
    private Image image;

    @JsonIgnoreProperties({"images"})
    @ManyToOne
    @JoinColumn (name = "userId")
    private User user;

    private LocalDateTime createDate;  //넽이티브 쿼리 쓰면 안들어감

    @PrePersist //insert될때 자동으로 들어감
    public void createDate(){
        this.createDate=LocalDateTime.now();
    }
}
