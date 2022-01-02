package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomApiException extends RuntimeException{

    private static final long serialVersionUID=1L;



    public CustomApiException(String message){
        super(message);
    }
    public CustomApiException(String message,Map<String,String> errorMap){
        super(message); //부모한테 던짐
//        this.message=message;

    }


}

