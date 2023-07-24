package com.cos.photogramstart.web;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class ImageController {

    private final ImageService imageService;

    @GetMapping({"/", "/image/story"})  // 메인페이지 뜨게 하기
    public String story() {
        return "image/story";
    }

    @GetMapping("/image/popular")   // 인기페이지 뜨게 하기
    public String popular() {
        return "image/popular";
    }

    @GetMapping("/image/upload")   // 사진등록 뜨게 하기
    public String upload() {
        return "image/upload";
    }

    @PostMapping("/image")
    public String imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if (imageUploadDto.getFile().isEmpty())  { // 사진 없이 업로드하면 익셉션 처리
            throw new CustomValidationException("이미지가 첨부되지 않았습니다.", null);
        }
        // 서비스 호출
        imageService.사진업로드(imageUploadDto, principalDetails);

        return "redirect:/user/" + principalDetails.getUser().getId();  // 업로드하면 메인 페이지로 옴
    }
}
