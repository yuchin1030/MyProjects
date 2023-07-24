package com.cos.photogramstart.service; // 실제로 이 정보를 받아 DB에 insert나 delete하려면 서비스가 필요함

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service    // DB에 넣어주기 위해 Service를 등록해주어야함. 등록해주면 ->   1.IoC 등록됨 2. 트랜잭션 관리해줌
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional  // 함수가 실행되고 종료될때까지 트랜잭션 관리를 해줌. Write(Insert, Update, Delete) 할때 걸어줄거임
    public User 회원가입(User user) {
        // 회원가입 진행
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword); // 암호화된 패스워드
        user.setPassword(encPassword);
        user.setRole("ROLE_USER");  // 관리자 : ROLE_ADMIN
        User userEntity = userRepository.save(user);
        return userEntity;
    }
}
