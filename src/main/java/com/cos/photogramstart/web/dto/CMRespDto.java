package com.cos.photogramstart.web.dto;


import com.cos.photogramstart.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CMRespDto<T> {          //전역적인 에러를 담을거임

    private int code; // 1 성공, -1 실패
    private String message;
    private T data;

//    private User user;
}
