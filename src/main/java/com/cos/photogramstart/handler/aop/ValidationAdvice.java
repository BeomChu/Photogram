package com.cos.photogramstart.handler.aop;

import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.naming.Binding;
import java.util.HashMap;
import java.util.Map;

@Component // Restcontroller, Service 모든것들이 component를 상속해서 만들어져 있음.
@Aspect
public class ValidationAdvice {

    @Around("execution(* com.cos.photogramstart.web.*Controller.api.*(..))") //경로의 모든 controller로 끝나느 모든 java파일 . 모든 메서드 (..)== 모든 파라미터 허용
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{

        Object[] args = proceedingJoinPoint.getArgs(); //매게변수 다 뽑아쥼~
        for(Object arg : args){
//            System.out.println(arg); // 유저 tostring에서 이미지 지워줘야함
            if(arg instanceof BindingResult){
                System.out.println("유효성 검사를 하는 함수 입니다.");
                BindingResult bindingResult=(BindingResult) arg;

                if(bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();

                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage());
//                System.out.println(error.getDefaultMessage());
                    }
                    throw new CustomValidationApiException("유효성 검사 실패함", errorMap);
                }
            }

        }

        //proceedingJoinPoint 모든 함수에 접근할 수 있는 변수
        // 어떤 함수보다 먼저 실행됨
        //return할때 어떤 함수가 실행됨


        return proceedingJoinPoint.proceed();
    }
    @Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{


        Object[] args = proceedingJoinPoint.getArgs();
        for(Object arg : args){
            if(arg instanceof BindingResult){
                BindingResult bindingResult= (BindingResult) arg;
                if(bindingResult.hasErrors()){
                    Map<String,String> errorMap = new HashMap<>();

                    for (FieldError error:bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(),error.getDefaultMessage());
//                System.out.println(error.getDefaultMessage());
                    }
                    throw new CustomValidationException("유효성 검사 실패함",errorMap);
                }
            }

        }

        return proceedingJoinPoint.proceed();
    }

}
