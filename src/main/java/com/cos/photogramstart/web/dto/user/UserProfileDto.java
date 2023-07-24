package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor // 전체 생성자
@NoArgsConstructor  // 빈 생성자
@Data
public class UserProfileDto {
    private boolean pageOwnerState;    // 이 페이지의 주인인지 아닌지에 대한 여부 데이터
    private int imageCount; // 이렇게 최종 데이터를 만들어가는게 좋음.
                            // ${dto.user.images.size()} 처럼 View 페이지에서 연산하는 것은 좋지 않음.
                            // 최대한 View 페이지에서 연산을 줄이고 모든 데이터를 만들어가자!!
    private boolean subscribeState; // 구독 상태 여부
    private int subscribeCount; // 구독 개수
    private User user;
}
