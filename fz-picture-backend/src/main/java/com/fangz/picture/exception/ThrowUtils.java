package com.fangz.picture.exception;

/**
 * @Author: fangzong
 * @CreateTime: 2025-01-19
 * @Description: 抛出异常工具类
 * @Version: 1.0
 */
public class ThrowUtils {
    private static void throwIf(boolean condition,RuntimeException runtimeException){
        if (condition){
            throw runtimeException;
        }
    }

    private static void throwIf(boolean condition,ErrorCode errorCode){
        throwIf(condition,new BusinessException(errorCode));
    }

    private static void throwIf(boolean condition,ErrorCode errorCode,String message){
        throwIf(condition,new BusinessException(errorCode,message));
    }
}
