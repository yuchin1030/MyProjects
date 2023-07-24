package com.cos.photogramstart.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscribeDto { // 구독정보 창
    private int id; // 누구를 구독할지에 관한 Id
    private String username;    // 유저 이름
    private String profileImageUrl; // 프로필 이미지
    private Integer subscribeState; // 구독상태 여부
    private Integer equalUserState; // 동일인인지 여부
}
