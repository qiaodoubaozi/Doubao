package com.doubao.backend.common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class BaseResponse<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -7241571217068920217L;

    private int code; // 状态码

    private T data; // 数据

    private String message; // 消息

    private String description; // 详细描述

    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
    }
}
