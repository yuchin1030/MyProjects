package com.cos.photogramstart.web;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/user/{pageUserId}")   // user 페이지는 각각 다름 -> {id}
    public String profile(@PathVariable int pageUserId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        UserProfileDto dto = userService.회원프로필(pageUserId, principalDetails.getUser().getId());
        model.addAttribute("dto", dto);
        return "user/profile";
    }

    @GetMapping("/user/{id}/update")    // user 누구를 업데이트? -> {id}
    public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        // 1. 추천
        //System.out.println("세션 정보 : " + principalDetails.getUser());

        // 2. 극혐
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //PrincipalDetails mPrincipalDetails = (PrincipalDetails) auth.getPrincipal();
        //System.out.println("직접 찾은 세션 정보 : " + mPrincipalDetails.getUser());

        // principal : 접근(인증) 주체; 인증되는 사용자 오브젝트명으로 쓸 때 좋음
        return "user/update";
    }
}
