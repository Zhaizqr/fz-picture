package com.fangz.picture.Model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础模型类，包含通用字段
 */
@Data
public class BaseModel implements Serializable {

    /**
     * 主键 ID
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除字段（0：未删除，1：已删除）
     */
    private Integer isDelete = 0;

    private static final long serialVersionUID = 1L;
}