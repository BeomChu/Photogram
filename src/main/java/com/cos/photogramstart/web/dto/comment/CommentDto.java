package com.cos.photogramstart.web.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

//NotNull = null값 체크
//NotEmpty = 빈값이거나 null 체크
//NotBlank = 빈값이거나 null 체크 그리고 빈 공백 ( 스페이스까지 ) 체크

@Data
public class CommentDto {
    @NotBlank //빈값이거나 null 그리고 공백까지
    private String content;

    @NotNull // //null 체크
    private Integer imageId;

    //toEntity 필요없음
}
