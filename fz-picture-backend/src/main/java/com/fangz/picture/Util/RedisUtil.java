package com.fangz.picture.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    private static RedisTemplate<String, Object> staticRedisTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 在类初始化时，将注入的redisTemplate赋值给静态变量
     */
    @PostConstruct
    public void init() {
        staticRedisTemplate = this.redisTemplate;
    }

    /**
     * 设置缓存（静态方法）
     *
     * @param key   键
     * @param value 值
     */
    public static void set(String key, Object value) {
        staticRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置缓存并设置过期时间（静态方法）
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期时间
     * @param unit    时间单位
     */
    public static void set(String key, Object value, long timeout, TimeUnit unit) {
        staticRedisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 获取缓存（静态方法）
     *
     * @param key 键
     * @return 值
     */
    public static Object get(String key) {
        return staticRedisTemplate.opsForValue().get(key);
    }

    /**
     * 删除缓存（静态方法）
     *
     * @param key 键
     */
    public static void delete(String key) {
        staticRedisTemplate.delete(key);
    }

    /**
     * 通配删除
     *
     * @param pattern 通配符模式（例如：user:token:refresh:admin:*）
     */
    public static void deleteByPattern(String pattern) {
        Set<String> keys = staticRedisTemplate.keys(pattern);
        if (keys != null && !keys.isEmpty()) {
            staticRedisTemplate.delete(keys);
        }
    }

    /**
     * 判断缓存是否存在（静态方法）
     *
     * @param key 键
     * @return 是否存在
     */
    public static boolean hasKey(String key) {
        return Boolean.TRUE.equals(staticRedisTemplate.hasKey(key));
    }

    /**
     * 设置过期时间（静态方法）
     *
     * @param key     键
     * @param timeout 过期时间
     * @param unit    时间单位
     */
    public static void expire(String key, long timeout, TimeUnit unit) {
        staticRedisTemplate.expire(key, timeout, unit);
    }

    /**
     * 获取剩余过期时间（静态方法）
     *
     * @param key 键
     * @return 剩余时间（单位：秒）
     */
    public static Long getExpire(String key) {
        return staticRedisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 递增（静态方法）
     *
     * @param key   键
     * @param delta 增量
     * @return 递增后的值
     */
    public static Long increment(String key, long delta) {
        return staticRedisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减（静态方法）
     *
     * @param key   键
     * @param delta 减量
     * @return 递减后的值
     */
    public static Long decrement(String key, long delta) {
        return staticRedisTemplate.opsForValue().decrement(key, delta);
    }
}