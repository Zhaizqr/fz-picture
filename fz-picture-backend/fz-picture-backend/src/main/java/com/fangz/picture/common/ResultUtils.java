package com.fangz.picture.common;

import com.fangz.picture.exception.ErrorCode;

/**
 * @Author: fangzong
 * @CreateTime: 2025-01-19
 * @Description: 响应结果类
 * @Version: 1.0
 */
public class ResultUtils {

    /**
     * 普通返回
     *
     * @param <T>@return
     */
    public static<T> BaseResponse<T> success(T data){
        return new BaseResponse<>(0,data,"ok");
    }

    /**
     * 使用ErrorCode，带数据
     * @param errorCode
     * @return
     */
    public static BaseResponse<String> error(ErrorCode errorCode){
        return new BaseResponse<>(errorCode.getCode(),errorCode.getMessage());
    }

    /**
     * 使用ErrorCode，且自定义错误信息
     * @param errorCode
     * @param message
     * @return
     */
    public static BaseResponse<String> error(ErrorCode errorCode,String message){
        return new BaseResponse<>(errorCode.getCode(),message);
    }

    /**
     * @param code
     * @param message
     * @return
     */
    public static BaseResponse<String> error(int code,String message){
        return new BaseResponse<>(code,message);
    }

}
