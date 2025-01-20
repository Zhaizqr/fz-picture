package com.fangz.picture.mybatis.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fangz.picture.mybatis.model.BaseModel;
import com.fangz.picture.mybatis.model.vo.UserVO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 *
 * @TableName user
 */
@TableName(value = "user")
@Data
public class User extends BaseModel implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 主键 ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;
    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;
    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;
    /**
     * 手机号
     */
    @TableField(value = "phone")
    private String phone;
    /**
     * 用户状态（0：禁用，1：启用）
     */
    @TableField(value = "status")
    private Integer status;
    /**
     * 最后登录时间
     */
    @TableField(value = "last_login_time")
    private Date lastLoginTime;

    public static User converToUser(UserVO userVO) {
        User user = new User();
        user.setUsername(userVO.getUsername());       // 获取用户名
        user.setEmail(userVO.getEmail());             // 获取电子邮件
        user.setPhone(userVO.getPhone());             // 获取手机号
        user.setStatus(userVO.getStatus());           // 获取状态
        user.setLastLoginTime(user.getLastLoginTime());  // 获取上次登录时间
        return user;
    }
}