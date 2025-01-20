package com.fangz.picture.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class JwtProperties {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.request-expiration-time}")
    private long requestExpirationTime;

    @Value("${jwt.refresh-expiration-time}")
    private long refreshExpirationTime;

    @Value("${jwt.token-prefix}")
    private String tokenPrefix;

    @Value("${jwt.header-string}")
    private String headerString;

    public static String SECRET_KEY;
    public static long REQUEST_EXPIRATION_TIME;
    public static long REFRESH_EXPIRATION_TIME;
    public static String TOKEN_PREFIX;
    public static String HEADER_STRING;

    @PostConstruct
    public void init() {
        SECRET_KEY = secretKey;
        REQUEST_EXPIRATION_TIME = requestExpirationTime;
        REFRESH_EXPIRATION_TIME = refreshExpirationTime;
        TOKEN_PREFIX = tokenPrefix;
        HEADER_STRING = headerString;
    }
}
