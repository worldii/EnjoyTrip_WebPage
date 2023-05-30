package com.ssafy.enjoytrip.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

public class ApiUtil {
    public static <T> ApiResult<T> success(T response) {
        return new ApiResult<>(true, response, null);
    }

    public static ApiResult<?> error (BindingResult result){
        return new ApiResult<>(false, null, new ApiError(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST));
    }
    public static ApiResult<?> error(Throwable throwable, HttpStatus status) {
        return new ApiResult<>(false, null, new ApiError(throwable, status));
    }

    public static ApiResult<?> error(String message, HttpStatus status) {
        return new ApiResult<>(false, null, new ApiError(message, status));
    }


    @Getter
    @ToString
    public static class ApiError {

        private final String message;
        private final int status;

        public ApiError(String message, HttpStatus status) {
            this.message = message;
            this.status = status.value();
        }
        public ApiError(Throwable throwable, HttpStatus status) {
            this(throwable.getMessage(), status);
        }
    }

    @AllArgsConstructor
    @Getter
    @ToString
    public static class  ApiResult <T> {
        private final boolean success;
        private final T response;
        private final ApiError error;
    }
}
