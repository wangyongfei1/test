package com.wisdomconstruction.wisdomConstruction.tool;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author kongke
 * @version 1.0
 * @intention
 * @date 2020/5/7 15:29
 */
@Component
public class RedisTool {

    private static StringRedisTemplate redisTemplate;

    @Autowired
    private SpringTool springTool;

    @PostConstruct
    public void init() {
        redisTemplate = SpringTool.getBean(StringRedisTemplate.class);
    }

    public static Map<Object, Object> getHash(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public static void setHash(String key, Map<?, ?> map, Long time, TimeUnit unit) {
        redisTemplate.opsForHash().putAll(key, map);
        redisTemplate.expire(key, time, unit);
    }

    public static void setVal(String key, Object object, Long time, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(object));
        redisTemplate.expire(key, time, unit);
    }

    public static <T> T getVal(String key, Class<T> clazz) {
        String data = redisTemplate.opsForValue().get(key);
        if (StrUtil.isEmpty(data)) {
            return null;
        }
        return JSONUtil.toBean(data, clazz);
    }

}
