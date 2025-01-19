package com.fangz.picture.common;

import com.fangz.picture.exception.ErrorCode;
import lombok.Getter;

import java.io.Serializable;

/**
 * @Author: fangzong
 * @CreateTime: 2025-01-19
 * @Description: 通用响应类
 * @Version: 1.0
 */
@Getter
public class BaseResponse<T> implements Serializable {
    private final int code;
    private final T data;
    private final String message;

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(int code, String message) {
        this(code,null,message);
    }
}
