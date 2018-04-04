package com.zlwon.server.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zlwon.server.service.RedisService;

@Service
public class RedisServiceImpl<T> implements RedisService<T> {

    @Autowired
    private RedisTemplate stringRedisTemplate;

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
        return (String) stringRedisTemplate.opsForValue().get(key);
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

    
    /**
     * hash添加
     * @param key
     * @param hashKey
     * @param value
     */
    public  void  hSet(Object key, Object hashKey, Object value){
    	stringRedisTemplate.opsForHash().put(key, hashKey, value);
    }
    /**
     * hash获取
     * @param key
     * @param hashKey
     */
    public  Object  hGet(Object key, Object hashKey){
    	return  stringRedisTemplate.opsForHash().get(key, hashKey);
    }
    
    public  void  expire(Object  key, long expiredTime, TimeUnit timeUnit){
    	stringRedisTemplate.expire(key, expiredTime, timeUnit);
    }
}
