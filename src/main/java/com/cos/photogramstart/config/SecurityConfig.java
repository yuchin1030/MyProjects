package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity  // 해당 파일로 시큐리티를 활성화
@Configuration  //IoC
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();  // csrf 비활성화 - postman으로 회원가입하든 정상적으로 홈페이지에서 회원가입하든 구분 안 함.
        // super 삭제 - 기존 시큐리티가 가직 있는 기능이 다 비활성화됨.
        http.authorizeRequests()
                .antMatchers("/","/user/**","image/**","/subscribe/**","/comment/**", "/api/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/auth/signin")  // GET (인증이 안돼있으면 여기로)
                .loginProcessingUrl("/auth/signin") // POST (로그인 요청을 하면 스프링 시큐리티가 여기서 로그인 프로세스 진행)
                .defaultSuccessUrl("/");    // 인증이 필요한 페이지 요청이 오면 /auth/signin으로 자동으로 가게끔 함
                                            // /auth/signin : formLogin페이지
                                            // login 정상적으로 처리되면 "/"로 가게 할게
    }
}
