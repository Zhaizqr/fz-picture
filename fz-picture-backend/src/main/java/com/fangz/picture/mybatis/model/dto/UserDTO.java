package com.fangz.picture.mybatis.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName UserDTO
 */
@Data
public class UserDTO implements Serializable {
    private String username;

    private String password;

    private String checkPassword;

    private String email;

    private String phone;

    private static final long serialVersionUID = 1L;
}