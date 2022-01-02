package com.cos.photogramstart.domain.subscribe;


import com.cos.photogramstart.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
       uniqueConstraints = {
               @UniqueConstraint(
                       name = "subscribe_uk",
                       columnNames = {"fromUserId", "toUserId"}
               )
       }
)
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "fromUserId")
    @ManyToOne
    private User fromUser;

    @JoinColumn(name = "toUserId")
    @ManyToOne
    private User toUser;

    private LocalDateTime createDate;             //넽이티브 쿼리 쓰면 안들어감

    @PrePersist //insert될때 자동으로 들어감
    public void createDate(){
        this.createDate=LocalDateTime.now();
    }

}




