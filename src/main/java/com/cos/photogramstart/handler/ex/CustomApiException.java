package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomApiException extends RuntimeException {   // Exception이 되려면 RuntimeException 상속받아야함
    // 시리얼번호: 객체를 구분할때
    private static final long serialVersionUID = 1L;

    public CustomApiException(String message) {
        super(message);
    }

}
