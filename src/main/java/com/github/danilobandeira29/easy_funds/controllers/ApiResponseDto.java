package com.github.danilobandeira29.easy_funds.controllers;

import lombok.Getter;

@Getter
public class ApiResponseDto<T> {
    public enum STATUS {
        success,
        fail
    }
    private STATUS status;
    private T data;
    private String error, code;

    private ApiResponseDto(STATUS s, T d) {
        status = s;
        data = d;
    }

    private ApiResponseDto(STATUS s, String e, String c) {
        status = s;
        data = null;
        error = e;
        code = c;
    }

    public static <T> ApiResponseDto<T> success(T data) {
        return new ApiResponseDto<T>(STATUS.success, data);
    }

    public static <T> ApiResponseDto<T> fail(String e, String c) {
        return new ApiResponseDto<T>(STATUS.fail, e, c);
    }
}
