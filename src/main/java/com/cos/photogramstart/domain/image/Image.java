package com.cos.photogramstart.domain.image;

import com.cos.photogramstart.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity // DB에 테이블을 생성
public class Image {    // N, 1
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다.
    private int id; // Long - 엄청 큰 서비스라면
    private String caption; // 사진 설명
    private String postImageUrl;    // 사진을 전송받아 그 사진을 서버의 특정 폴더에 저장 - DB에 그 저장된 경로를 insert

    @JoinColumn(name = "userId")
    @ManyToOne
    private User user;  // 1, 1

    // 이미지 좋아요
    // 댓글

    private LocalDateTime createDate;

    @PrePersist // DB에 INSERT 되기 직전에 실행됨. (항상 DB에는 데이터가 언제 들어왔는지 시간이 들어와야한다)
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    // 오브젝트를 콘솔에 출력할 때 문제가 될 수 있어서 User 부분을 출력되지 않게 함.
    /*@Override
    public String toString() {  // @Data라는 어노테이션을 사용하면 toString()을 자동으로 만들어줌. 따라서 이미 존재한다 뜸.
        return "Image{" +
                "id=" + id +
                ", caption='" + caption + '\'' +
                ", postImageUrl='" + postImageUrl + '\'' +
                ", createDate=" + createDate +
                '}';
    }*/
}
