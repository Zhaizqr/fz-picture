package com.fangz.picture.system;

import cn.hutool.core.util.StrUtil;
import com.fangz.picture.config.JwtProperties;
import com.fangz.picture.mybatis.model.entity.User;
import com.fangz.picture.mybatis.model.vo.UserVO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtUtil {

    // 如果 SECRET_KEY 是从配置文件注入的，应该避免将其定义为 static
    private static final Key KEY;

    static {
        // 使用静态初始化块来保证配置的 SECRET_KEY 已经注入
        KEY = Keys.hmacShaKeyFor(JwtProperties.SECRET_KEY.getBytes());
    }

    /**
     * 生成请求token，短时间
     * @param user
     * @return
     */
    public static String generateRequestToken(User user) {
        return generateToken(user,JwtProperties.REQUEST_EXPIRATION_TIME);
    }

    /**
     * 生成刷新token，长时间
     * @param user
     * @return
     */
    public static String generateRefreshToken(User user) {
        return generateToken(user,JwtProperties.REFRESH_EXPIRATION_TIME);
    }

    /**
     * 生成请求token
     * @param user
     * @return
     */
    private static String generateToken(User user,long time) {
        String username = user.getUsername();
        String email = user.getEmail();
        String phone = user.getPhone();

        // 将用户信息存储为 claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("email", email);
        claims.put("phone", phone);

        return Jwts.builder()
                .setClaims(claims)  // 设置自定义 claims
                .setIssuedAt(new Date()) // 签发时间
                .setExpiration(new Date(System.currentTimeMillis() + time)) // 过期时间
                .signWith(KEY, SignatureAlgorithm.HS256) // 签名算法
                .compact();
    }

    // 验证并解析 JWT
    public static Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            log.error("JWT验证失败", e);
            return null;
        }
    }

    /**
     * 判断 token 是否过期，且 返回用户信息
     * @param token
     * @return
     */
    public static UserVO isTokenExpired(String token) {
        if (StrUtil.isBlank(token)){
            return null;
        }
        Claims claims = validateToken(token);
        boolean expire = claims == null || claims.getExpiration().before(new Date());
        UserVO user = new UserVO();
        if (!expire) {
            user.setUsername(claims.get("username", String.class));
            user.setEmail(claims.get("email", String.class));
            user.setPhone(claims.get("phone", String.class));
            return user;
        }
        return null;
    }
}
