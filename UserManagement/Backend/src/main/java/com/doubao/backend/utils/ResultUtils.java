package com.doubao.backend.utils;

import com.doubao.backend.common.BaseResponse;

public class ResultUtils {

    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<>(200, data, "success");
    }

    public static <T>BaseResponse<T> success(T data, String message){
        return new BaseResponse<>(200, data, message);
    }
}
