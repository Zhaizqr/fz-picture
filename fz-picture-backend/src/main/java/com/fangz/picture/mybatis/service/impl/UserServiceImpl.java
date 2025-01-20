package com.fangz.picture.mybatis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fangz.picture.mybatis.model.entity.User;
import com.fangz.picture.mybatis.service.IUserService;
import com.fangz.picture.mybatis.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author fangz
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2025-01-20 16:48:07
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements IUserService {

}




