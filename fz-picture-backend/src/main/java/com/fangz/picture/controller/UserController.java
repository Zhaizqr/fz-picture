/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fangz.picture.controller;

import com.fangz.picture.common.BaseResponse;
import com.fangz.picture.common.ResultUtils;
import com.fangz.picture.exception.ErrorCode;
import com.fangz.picture.mybatis.model.dto.UserDTO;
import com.fangz.picture.mybatis.model.vo.UserVO;
import com.fangz.picture.service.IBusUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IBusUserService userService;
    @PostMapping("/login")
    public BaseResponse<?> login(@RequestBody UserDTO userDTO, HttpServletResponse response) {
        return userService.login(userDTO,response);
    }

    @PostMapping("/register")
    public BaseResponse<?> register(@RequestBody UserDTO userDTO) {
        return userService.register(userDTO);
    }

    @PostMapping("/getLogin")
    public BaseResponse<?> getLogin(UserDTO userDTO,HttpServletRequest request) {
        return userService.getLogin(userDTO,request);
    }
    @PostMapping("/logout")
    public BaseResponse<?> logout(@RequestBody UserDTO userDTO) {
        return userService.logout(userDTO);
    }

    @PostMapping("/refreshToken")
    public BaseResponse<?> refreshToken(UserDTO userDTO,HttpServletRequest request,HttpServletResponse response) {
        return userService.refreshToken(userDTO,request,response);
    }

}
