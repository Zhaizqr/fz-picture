package com.fangz.picture.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fangz.picture.Util.RedisUtil;
import com.fangz.picture.Util.Util;
import com.fangz.picture.common.BaseResponse;
import com.fangz.picture.common.ResultUtils;
import com.fangz.picture.exception.ErrorCode;
import com.fangz.picture.mybatis.model.dto.UserDTO;
import com.fangz.picture.mybatis.model.entity.User;
import com.fangz.picture.mybatis.model.vo.UserVO;
import com.fangz.picture.mybatis.service.IUserService;
import com.fangz.picture.service.IBusUserService;
import com.fangz.picture.system.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class BusUserServiceImpl implements IBusUserService {

    public static final String USER_REFRESH_TOKEN_KEY = "user:token:refresh:";
    public static final String USER_REQUEST_TOKEN_KEY = "user:token:request:";
    public static final String REDIS_SPLIT = ":";

    @Autowired
    private IUserService userService;

    @Override
    public BaseResponse<?> login(UserDTO userDTO, HttpServletResponse response) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();

        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }

        String entryPassword = SecureUtil.md5(password);
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        wrapper.select("id", "username", "password", "email", "phone", "status", "last_login_time")
                .eq("username", username)
                .eq("password", entryPassword);
        List<User> list = userService.list(wrapper);

        if (list != null && !list.isEmpty()) {
            User user = list.get(0);
            String refreshToken = JwtUtil.generateRefreshToken(user);
            String requestToken = JwtUtil.generateRequestToken(user);

            String refreshTokenId = UUID.randomUUID().toString();
            String requestTokenId = UUID.randomUUID().toString();

            // 删除用户所有的token缓存
            RedisUtil.deleteByPattern(USER_REFRESH_TOKEN_KEY + username + REDIS_SPLIT + "*");
            RedisUtil.deleteByPattern(USER_REQUEST_TOKEN_KEY + username + REDIS_SPLIT + "*");

            RedisUtil.set(USER_REFRESH_TOKEN_KEY + username + REDIS_SPLIT + refreshTokenId, refreshToken, 15, TimeUnit.DAYS); // 15天过期
            RedisUtil.set(USER_REQUEST_TOKEN_KEY + username + REDIS_SPLIT + requestTokenId, requestToken, 15, TimeUnit.MINUTES); //15分钟过期

            // 登录成功，生成 Token 并且设置 cookies
            response.addCookie(generateCookie(refreshTokenId, "auth_token_refresh"));
            response.addCookie(generateCookie(requestTokenId, "auth_token_request"));

            // 返回用户信息
            return ResultUtils.success(UserVO.converToUserVO(user), "登入成功");
        }

        return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR, "用户名或密码错误");
    }

    public Cookie generateCookie(String token, String key) {
        Cookie cookie = new Cookie(key, token);  // cookie 名字可以自定义
        cookie.setHttpOnly(true);  // 设置 HttpOnly 属性，防止客户端 JS 访问，提升安全性
        cookie.setSecure(true);    // 设置 Secure 属性，确保 cookie 只通过 HTTPS 协议传输
        cookie.setPath("/api");       // 设置 cookie 的路径，使得所有页面都能访问到这个 cookie
        cookie.setMaxAge(60 * 60 * 24);  // 设置 cookie 过期时间为 1 天 (24 小时)
        return cookie;
    }

    @Override
    public BaseResponse<?> register(UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        String checkPassword = userDTO.getCheckPassword();
        String email = userDTO.getEmail();
        String phone = userDTO.getPhone();

        if (StrUtil.isBlank(username) || StrUtil.isBlank(password) || StrUtil.isBlank(checkPassword)) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "用户名，密码，确认密码不能为空");
        }

        if (password.length() < 6) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "密码不能少于6位");
        }

        if (!password.equals(checkPassword)) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "两次密码不一致");
        }

        if (!Validator.isMobile(phone) && StrUtil.isNotBlank(phone)) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "手机号格式不正确");
        }

        if (!Validator.isEmail(email) && StrUtil.isNotBlank(email)) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "邮箱格式不正确");
        }

        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setPassword(SecureUtil.md5(password));
        List<User> existUser = userService.list(new QueryWrapper<User>().eq("username", username));
        if (existUser != null && !existUser.isEmpty()) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "该用户名已被占用，请更换其余名称");
        }

        boolean result = userService.save(user);

        if (result) {
            return ResultUtils.success(user, "注册成功，去登录吧");
        } else {
            return ResultUtils.error(ErrorCode.NOT_FOUND_ERROR, "注册失败");
        }
    }

    @Override
    public BaseResponse<?> getLogin(UserDTO userDTO, HttpServletRequest request) {
        // 获取所有的 Cookie
        Cookie[] cookies = request.getCookies();
        String username = userDTO.getUsername();

        if (cookies == null) {
            return ResultUtils.error(ErrorCode.REFRESH_TOKEN);
        }

        String token = getRequestTokenFromCookies(cookies, "auth_token_request", username);
        if (token == null) {
            return ResultUtils.error(ErrorCode.REFRESH_TOKEN);
        }

        UserVO user = JwtUtil.isTokenExpired(token);
        if (user != null) {
            return ResultUtils.success(user);
        } else {
            return ResultUtils.error(ErrorCode.REFRESH_TOKEN);
        }
    }

    @Override
    public BaseResponse<?> refreshToken(UserDTO userDTO, HttpServletRequest request, HttpServletResponse response) {
        // 获取所有的 Cookie
        Cookie[] cookies = request.getCookies();
        String username = userDTO.getUsername();

        if (cookies == null) {
            return ResultUtils.error(ErrorCode.NOT_LOGIN_ERROR);
        }

        String token = getRefreshTokenFromCookies(cookies, "auth_token_refresh", username);
        if (token == null) {
            return ResultUtils.error(ErrorCode.NOT_LOGIN_ERROR);
        }

        UserVO userVO = JwtUtil.isTokenExpired(token);

        if (userVO != null) {
            String newRequestToken = JwtUtil.generateRequestToken(User.converToUser(userVO));
            String requestTokenId = UUID.randomUUID().toString();
            RedisUtil.deleteByPattern(USER_REQUEST_TOKEN_KEY + userVO.getUsername() + REDIS_SPLIT+"*");
            RedisUtil.set(USER_REQUEST_TOKEN_KEY + userVO.getUsername() + REDIS_SPLIT + requestTokenId, newRequestToken, 15, TimeUnit.MINUTES);
            response.addCookie(generateCookie(requestTokenId, "auth_token_request"));
            return ResultUtils.success();
        } else {
            return ResultUtils.error(ErrorCode.NOT_LOGIN_ERROR);
        }
    }

    @Override
    public BaseResponse<?> logout(UserDTO userDTO) {

        String username = userDTO.getUsername();

        RedisUtil.delete(USER_REQUEST_TOKEN_KEY + username);
        RedisUtil.delete(USER_REFRESH_TOKEN_KEY + username);

        return ResultUtils.success();
    }

    // 从 cookies 中获取 token
    private String getRequestTokenFromCookies(Cookie[] cookies, String key, String username) {
        for (Cookie cookie : cookies) {
            if (key.equals(cookie.getName())) {
                return Util.getStrOfObj(RedisUtil.get(USER_REQUEST_TOKEN_KEY + username + REDIS_SPLIT + cookie.getValue()));
            }
        }
        return null;
    }

    // 从 cookies 中获取 token
    private String getRefreshTokenFromCookies(Cookie[] cookies, String key, String username) {
        for (Cookie cookie : cookies) {
            if (key.equals(cookie.getName())) {
                RedisUtil.delete(USER_REFRESH_TOKEN_KEY + username);
                return Util.getStrOfObj(RedisUtil.get(USER_REFRESH_TOKEN_KEY + username + REDIS_SPLIT + cookie.getValue()));
            }
        }
        return null;
    }

}
