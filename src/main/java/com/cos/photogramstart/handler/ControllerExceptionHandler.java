package com.cos.photogramstart.handler;

import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.utill.Script;
import com.cos.photogramstart.web.dto.CMRespDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController // 리턴타입 데이터임
@ControllerAdvice // 모든 에러 낚아챔
public class ControllerExceptionHandler {


    @ExceptionHandler(CustomValidationException.class) //런타임은 다 찾아먹음
    public String validationException(CustomValidationException e){                 //에러맵을 담을꺼니까 제네릭타입을 에러맵으로해줌
        //CMRespDto, script 비교
        //1.클라이언트에게 응답할때는 script 좋음(사용자 가독성이 좋음) 개발과정에서 코드로 받아서 확인하는게 좋음
        //2. Ajax통신 - CMRespDto, Android 통신 - CMRespDto        개발과정에서 코드로 받아서 확인하는게 좋음
        if(e.getErrorMap() == null){
            return Script.back(e.getMessage());
        }else {
            return Script.back(e.getErrorMap().toString());
        }
//        return new CMRespDto<Map<String,String>>(-1,e.getMessage(),e.getErrorMap());     //실패한 상황에 출력되니까 -1로 출력하게함  //나중에 쓸거임
    }

    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<?> validationApiException(CustomValidationApiException e){
        return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(),e.getErrorMap()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> apiException(CustomApiException e){
        return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(),null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class) //런타임은 다 찾아먹음
    public String validationException(CustomException e){                 //에러맵을 담을꺼니까 제네릭타입을 에러맵으로해줌

            return Script.back(e.getMessage());
    }

}
