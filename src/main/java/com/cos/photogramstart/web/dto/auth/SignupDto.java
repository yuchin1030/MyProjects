package com.cos.photogramstart.web.dto.auth;

import com.cos.photogramstart.domain.user.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignupDto {
    // https://bamdule.tistory.com/35 (@Valid 어노테이션 종류)
    @Size(min=2, max=20)
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
    @NotBlank
    private String name;

    public User toEntity() {    // User객체를 만들어서 build해주면 가입 후 로그인 했을 때 회원 정보에 가입한 정보들(4개) 자동 등록됨
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .name(name)
                .build();
    }
}
