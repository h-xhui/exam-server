package com.exam.Interceptor;

import cn.hutool.core.util.StrUtil;
import com.exam.utils.JWTUtils;
import com.exam.utils.RedisUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.exam.utils.RedisConstants.USER_TOKEN;

public class IdentityHandler implements HandlerInterceptor {

    private JWTUtils jwtUtils;

    private RedisUtils redisUtils;

    public IdentityHandler(JWTUtils jwtUtils, RedisUtils redisUtils) {
        this.jwtUtils = jwtUtils;
        this.redisUtils = redisUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String jwt = request.getHeader(jwtUtils.getHeader());
        if (StrUtil.isBlank(jwt)) {
            throw new JwtException("未登录");
        }

        String flag = redisUtils.get(jwt);
        if (flag != null) {
            throw new JwtException("请重新登录");
        }

        Claims token = jwtUtils.getClaimsByToken(jwt);
        if (token == null) {
            throw new JwtException("身份异常");
        } else if (jwtUtils.isTokenExpired(token)) {
            throw new JwtException("身份过期");
        }

        return true;
    }

}
