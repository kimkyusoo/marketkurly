package com.example.marketkurly.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter // Get메소드 일괄 생성
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 만듦
public class ResponseDto<T> {
    private boolean success;
    private T data;
    private Error error;

    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(true, data, null);
    }

    public static <T> ResponseDto<T> fail(String code, String message) {
        return new ResponseDto<>(false, null, new Error(code, message));
    }

    @Getter // Get메소드 일괄 생성
    @AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 만듦
    static class Error {
        private String code;
        private String message;
    }

}
