package com.exam.config;

import com.exam.Interceptor.IdentityHandler;
import com.exam.utils.JWTUtils;
import com.exam.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

//
//@Configuration
//public class InterceptorConfig implements WebMvcConfigurer {
//
//    @Autowired
//    private JWTUtils jwtUtils;
//
//    @Resource
//    private RedisUtils RedisUtils;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new IdentityHandler(jwtUtils, RedisUtils)).excludePathPatterns(
//                "/user/code",
//                "/user/login",
//                "/user/register"
//        );
//
//
//    }
//}
