package com.exam.utils;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static com.exam.utils.RedisConstants.CACHE_NULL_TTL;

@Component
public class RedisUtils {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void set(String key, Object value, Long time, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), time, unit);
    }

    public void setRandom(String key, Object value, Long time, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), time + RandomUtil.randomInt(2, 5), unit);
    }

    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }


    /**
     * 取缓存，防止缓存穿透，数据来自数据库
     *
     * @param keyPrefix  key前段
     * @param keyMain    key后段，数据的主键等
     * @param type       取出数据的类型
     * @param daFallback 取出数据对应的查询方法
     * @param time       当不存时，缓存的时间
     * @param unit       时间单位
     * @param <R>        取出数据的类型
     * @param <KEY>      数据主键的类型
     * @return
     */
    public <R, KEY> R classPassThrough(String keyPrefix, KEY keyMain, Class<R> type, Function<KEY, R> daFallback, Long time, TimeUnit unit) {
        String key = keyPrefix + keyMain;
        String json = stringRedisTemplate.opsForValue().get(key);
        if (StrUtil.isNotBlank(json)) {
            return JSONUtil.toBean(json, type);
        }
        if (json != null) {
            return null;
        }
        R r = daFallback.apply(keyMain);
        if (r == null) {
            set(key, "", CACHE_NULL_TTL, TimeUnit.MINUTES);
            return null;
        }
        set(key, r, time + RandomUtil.randomInt(2, 5), unit);
        return r;
    }

//    public <KEY> String passThrough(String keyPrefix,KEY keyMain){
//        String key =keyPrefix+keyMain;
//        String json = stringRedisTemplate.opsForValue().get(key);
//        if(json!=null){
//            return json;
//        }
//        set(key,"",CACHE_NULL_TTL,TimeUnit.MINUTES);
//        return "";
//    }


    //缓存穿透

}
