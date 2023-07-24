package com.cos.photogramstart.web;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor    // final에 걸려있는 모든 것들의 생성자를 만들어줌. (final 필드를 DI할때 사용)
@Controller // 1. IoC에 등록이 됨. 2. 파일을 리턴하는 컨트롤러
public class AuthController {   // 인증을 위한 컨트롤러

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    //public AuthController(AuthService authService) {
    //    this.authService = authService;
    //}

    @GetMapping("/auth/signin")
    public String signinForm() {
        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm() {
        return "auth/signup";
    }

    // 회원가입버튼 클릭 -> /auth/signup -> /auth/signin
    @PostMapping ("/auth/signup")
    public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) {    // 회원가입 // key=value (x-www-form-urlencoded)
        // Valid: signupDto 값이 들어오면 유효성 검사한다는 뜻     // 에러가 발생하면 bindingResult에 다 모아줌
        log.info(signupDto.toString());

        if (bindingResult.hasErrors()) {    // 세 값(@Not Blank한것) 중 blank가 있다거나 name의 길이가 20을 넘었을 때(에러가 하나라도 있을때)
            Map<String, String> errorMap = new HashMap<>(); // 내가 만든 해시맵에 에러들을 담음

            for (FieldError error : bindingResult.getFieldErrors()) { // 그리고 bindingResult의 getFieldErrors에 다 모아줌
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            throw new CustomValidationException("유효성검사 실패함", errorMap); // 강제로 익셉션을 던져서 데이터를 던짐 -> ControllerExceptionHandler가 낚아챔
        } else {
            // User <- SignupDto
            User user = signupDto.toEntity();
            User userEntity = authService.회원가입(user);
            System.out.println(userEntity);
            log.info(user.toString());
            return "auth/signin";   // 회원가입 성공하면 로그인 페이지
            // @Controller, public String signup() --> file 리턴
            // @Controller, public @ResponseBody String signup() --> 데이터 응답
        }


    }
}
