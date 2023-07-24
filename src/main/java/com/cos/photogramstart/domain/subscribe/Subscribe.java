package com.cos.photogramstart.domain.subscribe;

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
@Table( // 2개를 복합적으로 unique를 걸 때(unique: 1번이 2번을 구독한다는 데이터가 중복되지 않게 하기 위함)
        uniqueConstraints = {
                @UniqueConstraint(
                        name="subscribe_uk",
                        columnNames={"fromUserId", "toUserId"}  // 실제 DB의 열이름을 적어야함
                )
        }
)
public class Subscribe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다.
    private int id; // Long - 엄청 큰 서비스라면

    @JoinColumn(name="fromUserId")  // 이렇게 열이름 만들어! 니 맘대로 만들지 말고!!
    @ManyToOne
    private User fromUser;  // 구독하는 애

    @JoinColumn(name="toUserId")
    @ManyToOne
    private User toUser;    // 구독받는 애

    private LocalDateTime createDate;   // DB에 무조건 필요함.

    @PrePersist // DB에 INSERT 되기 직전에 실행됨.
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
