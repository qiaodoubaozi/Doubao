package com.doubao.backend.utils;

import com.doubao.backend.common.BaseResponse;
import com.doubao.backend.common.ErrorCode;
import com.doubao.backend.exception.BusinessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e) {
        return new BaseResponse(e.getCode(), null, e.getMessage(), e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException e) {
        return new BaseResponse(ErrorCode.SYSTEM_ERROR);
    }
}
