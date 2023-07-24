package com.cos.photogramstart.service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Value("${file.path}")          // yml파일에서 내가 만든 키 값(file.path)을 가져올 수 있음
    private String uploadFolder;    // yml의 path에 맨 뒤에 / 필수

    @Transactional
    public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
        UUID uuid = UUID.randomUUID();  // 사진 구분(네트워크 상에서 고유성이 보장되는 id를 만들기 위한 표준 규약) - 중복 불가능
        String imageFileName = uuid + "_" +imageUploadDto.getFile().getOriginalFilename();  // 실제 파일 이름 ex- 1.jpg
        System.out.println("이미지 파일 이름 : " + imageFileName);

        Path imageFilePath = Paths.get(uploadFolder + imageFileName);   // 경로 + 파일 이름

        // 통신, I/O가 일어날때 예외가 발생할 수 있음.
        try {
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // image 테이블에 저장
        Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());   // save하려면 imageUploadDto는 넣지 못하고 Image 객체를 넣어야함(파일 타입이 다르기 때문)
                // imageFileName: 8e0f0d8e-ed57-4158-b63b-3605e0c39276_starry_night.jpg
        imageRepository.save(image);    // 따라서 imageUploadDto를 image 객체에 넣는 로직이 필요 -> ImageUploadDto

        //System.out.println(imageEntity.toString());   // 사진 등록하면 실행됨(테스트만 하고 주석처리 필수)
    }
}
