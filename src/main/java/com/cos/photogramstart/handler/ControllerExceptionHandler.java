package com.cos.photogramstart.handler;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController // 리턴타입 데이터임
@ControllerAdvice // 모든 에러 낚아챔
public class ControllerExceptionHandler {


    @ExceptionHandler(CustomValidationException.class) //런타임은 다 찾아먹음
    public Map<String, String> validationException(CustomValidationException e){
        return e.getErrorMap();
    }
}
