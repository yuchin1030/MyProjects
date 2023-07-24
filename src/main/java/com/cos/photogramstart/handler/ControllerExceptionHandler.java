package com.cos.photogramstart.handler;

import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@ControllerAdvice   // 모든 Exception을 다 낚아챔
public class ControllerExceptionHandler {

    /*@ExceptionHandler(CustomValidationException.class)   // CustomValidationException이 발동하는 모든 익셉션을 이 함수가 가로챔
    public CMRespDto<?> validationException(CustomValidationException e) {  // ?로 해두면 알아서 리턴 타입을 찾음
        return new CMRespDto<Map<String, String>>(-1, e.getMessage(), e.getErrorMap()); // 에러 내용 리턴하고 종료
    }*/             // ---> 이 방법은 나중에 사용할거임. 지금은 아래 방법이 더 좋음. (자바스크립트로 제거해주는 것이 더 좋기 때문)
                    // 위 방법은 회원가입->다른 페이지로 넘어가서 에러를 알려주고, 아래 방법은 팝업창 같은 것이 뜸(사용자가 보기 더 편리함)

    @ExceptionHandler(CustomValidationException.class)   // CustomValidationException이 발동하는 모든 익셉션을 이 함수가 가로챔
    public String validationException(CustomValidationException e) {
        // CMRespDto, Script 비교
        // 1. 클라이언트에게 응답받을 때는 Script가 좋음.
        // 2. Ajax통신 - CMRespDto        // 개발자가 응답받을 때는 2,3번처럼 코드로 응답받는 것이 좋음
        // 3. Android 통신 - CMRespDtp
        if (e.getErrorMap() == null) {
            return Script.back(e.getMessage());
        } else {
            return Script.back(e.getErrorMap().toString()); // 에러 내용 리턴하고 종료
        }
    }

    @ExceptionHandler(CustomException.class)   // CustomValidationException이 발동하는 모든 익셉션을 이 함수가 가로챔
    public String Exception(CustomException e) {
        // CMRespDto, Script 비교
        // 1. 클라이언트에게 응답받을 때는 Script가 좋음.
        // 2. Ajax통신 - CMRespDto        // 개발자가 응답받을 때는 2,3번처럼 코드로 응답받는 것이 좋음
        // 3. Android 통신 - CMRespDtp
        return Script.back(e.getMessage()); // 에러 내용 리턴하고 종료
    }

    // 데이터 리턴 - Ajax통신
    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<?> validationApiException(CustomValidationApiException e) {
        return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST); // 에러 내용 리턴하고 종료
    }

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> apiException(CustomApiException e) {
        return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), null), HttpStatus.BAD_REQUEST); // 에러 내용 리턴하고 종료
    }
}
