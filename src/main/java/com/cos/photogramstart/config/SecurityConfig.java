package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity // 해당 파일로 시큐리티를 활성화
@Configuration//Ioc 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encode(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        //super 삭제 - 기존 시큐리티가 가지고 있는 기능이 전부 비활성화됨.
        http.csrf().disable();

        http.authorizeRequests()     //antMatchers 이후에 오는 주소는 모든 주소는 인증이 필요함// http code 403으로 나옴옴
               .antMatchers("/","/user/**","/image/**","/subscribe/**","/comment/**").authenticated()
                .anyRequest().permitAll() //나머지는 알아서해
                .and()
                .formLogin()
                .loginPage("/auth/signin") //로그인 페이지 설정  //Get       //시큐리티 설정파일이 auth/signin요청받는걸 기다리고있음.
                .loginProcessingUrl("/auth/signin")           //Post 스프링 시큐리티가 로그인 프로세스 진행
                .defaultSuccessUrl("/");   //로그인 성공하면 가는 페이지
        //정상적인 로그인이 되지 않으면 300번대 http 코드가 나오고 다시 로그인페이지 나옴
    }
}
