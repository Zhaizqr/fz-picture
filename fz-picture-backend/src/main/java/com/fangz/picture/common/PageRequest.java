package com.fangz.picture.common;

import lombok.Data;

/**
 * @Author: fangzong
 * @CreateTime: 2025-01-19
 * @Description: 通用分页请求类
 * @Version: 1.0
 */
@Data
public class PageRequest {

    /**
     * 当前页
     */
    private int current = 1;

    /**
     * 页面大小
     */
    private int size = 1;

    /**
     * 排序字段
     */
    private String sort;

    /**
     * 排序方式（默认升序）
     */
    private String order = "descend";
}
