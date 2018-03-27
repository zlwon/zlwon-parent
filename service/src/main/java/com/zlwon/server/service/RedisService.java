package com.zlwon.server.service;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by fred on 2017/12/5.
 */
public interface RedisService<T> {

    void set(String key, String value);

    void set(String key, String value, long expiredTime);

    void set(String key, String value, long expiredTime, TimeUnit timeUnit);

    String get(String key);

    List<T> getList(String key, Class clazz);

    T getObject(String key);

    void delete(String key);

    void delete(Collection<String> keys);

}
