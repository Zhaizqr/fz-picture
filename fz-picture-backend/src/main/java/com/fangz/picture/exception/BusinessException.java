package com.fangz.picture.exception;

import lombok.Getter;

/**
 * @Author: fangzong
 * @CreateTime: 2025-01-19
 * @Description:
 * @Version: 1.0
 */
@Getter
public class BusinessException extends RuntimeException {

    private final int code;

    public BusinessException(int code,String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {
        this(errorCode.getCode(),errorCode.getMessage());
    }

    public BusinessException(ErrorCode errorCode,String message) {
        this(errorCode.getCode(),message);
    }
}
