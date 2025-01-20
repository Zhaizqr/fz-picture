package com.fangz.picture.mybatis.model.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fangz.picture.mybatis.model.BaseModel;
import com.fangz.picture.mybatis.model.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName UserVO
 */
@Data
public class UserVO implements Serializable {
    private String username;

    private String email;

    private String phone;

    private Integer status;

    private Date lastLoginTime;

    public static UserVO converToUserVO(User user) {
        UserVO userVO = new UserVO();
        userVO.setUsername(user.getUsername());       // 获取用户名
        userVO.setEmail(user.getEmail());             // 获取电子邮件
        userVO.setPhone(user.getPhone());             // 获取手机号
        userVO.setStatus(user.getStatus());           // 获取状态
        userVO.setLastLoginTime(user.getLastLoginTime());  // 获取上次登录时间
        return userVO;
    }

    private static final long serialVersionUID = 1L;
}