package com.zlwon.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.zlwon.server.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl<T> implements RedisService<T> {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, String value, long expiredTime) {
        set(key, value, expiredTime, TimeUnit.HOURS);
    }

    public void set(String key, String value, long expiredTime, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, value, expiredTime, timeUnit);
    }

    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public List<T> getList(String key, Class clazz) {
        String result = get(key);
        if(StringUtils.isNotEmpty(result)){
            return JSON.parseArray(result, clazz);
        }
        return null;
    }

    public T getObject(String key) {
        String result = get(key);
        if(StringUtils.isNotEmpty(result)){
            return (T) JSON.parseObject(result);
        }
        return null;
    }

    public void delete(String key){
        stringRedisTemplate.delete(key);
    }

    public void delete(Collection<String> keys){
        stringRedisTemplate.delete(keys);
    }

}
