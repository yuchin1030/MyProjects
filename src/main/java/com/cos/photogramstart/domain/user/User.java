package com.cos.photogramstart.domain.user;

// JPA - Java Persistence API (자바로 데이터를 영구적으로 저장(DB)할 수 있는 API를 제공)

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity // DB에 테이블을 생성
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다.
    private int id; // Long - 엄청 큰 서비스라면

    @Column(length = 20, unique = true)
    private String username;
    @Column(nullable = false)   // : 널 불가능
    private String password;

    @Column(nullable = false)
    private String name;
    private String website; // 웹 사이트
    private String bio; // 자기소개
    @Column(nullable = false)
    private String email;
    private String phone;
    private String gender;

    private String profileImageUrl; // 사진
    private String role;    // 권한

    // mappedBy = "user" : 나는 연관관계의 주인이 아님. 그러므로 테이블에 칼럼을 만들지마
    // User를 Select할 때 해당 User id로 등록된 image들을 다 가져와
    // Lazy : User를 Select할 때 해당 User id로 등록된 image들을 가져오지마 - 대신 getImages() 함수의 image들이 호출될 때 가져와!!
    // Eager : User를 Select할 때 해당 User id로 등록된 image들을 전부 Join해서 가져와!!
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"user"}) // Image 클래스 내부에 있는 user를 무시하고 파싱해줌
    private List<Image> images; // 양방향 매핑    // @JoinColumn(name = "userId")
                                                //@ManyToOne
                                                //private User user; 여기서의 User의 getter 호출을 막음(무한참조 방지)

    private LocalDateTime createDate;   // DB에 무조건 필요함.

    @PrePersist // DB에 INSERT 되기 직전에 실행됨.
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

}
