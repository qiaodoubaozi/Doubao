package com.doubao.backend.exception;

import com.doubao.backend.common.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    private final int code;

    private final String description;

    public BusinessException(String message, int code, String description){
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode){
        this(errorCode.getMessage(), errorCode.getCode(), errorCode.getDescription());
    }

    public BusinessException(ErrorCode errorCode, String description){
        this(errorCode.getMessage(), errorCode.getCode(), description);
    }

}
