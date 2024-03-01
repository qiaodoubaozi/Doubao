package com.doubao.backend.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse<T> implements Serializable {

    private int code; // 状态码

    private T data; // 数据

}
