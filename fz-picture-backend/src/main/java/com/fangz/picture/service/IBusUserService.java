package com.fangz.picture.service;

import com.fangz.picture.common.BaseResponse;
import com.fangz.picture.mybatis.model.dto.UserDTO;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IBusUserService {
    BaseResponse<?> login(UserDTO userDTO, HttpServletResponse response);

    BaseResponse<?> register(UserDTO userDTO);

    BaseResponse<?> getLogin(UserDTO userDTO,HttpServletRequest request);

    BaseResponse<?> refreshToken(UserDTO userDTO,HttpServletRequest request,HttpServletResponse response);

    BaseResponse<?> logout(UserDTO userDTO);

}
