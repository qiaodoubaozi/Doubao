package com.doubao.backend.common;

import lombok.Getter;

@Getter
public enum ErrorCode {
    SUCCESS(20000, "成功", ""),
    PARAMS_ERROR(40000, "请求参数错误", ""),
    NULL_ERROR(40001, "空指针异常", ""),
    NO_AUTH(40100, "无权限", ""),
    NOT_LOGIN(40101, "未登录", ""),
    SYSTEM_ERROR(50000, "系统异常", "");

    private final int code;
    private final String message;
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

}
