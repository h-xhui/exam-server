package com.exam.exception;

import com.exam.pojo.result.ApiResult;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.exam.common.ResultCode.NO_PERMISSION;

//@RestControllerAdvice
//@Slf4j
//public class GlobalExceptionHandler {
//    @ExceptionHandler(value = JwtException.class)
//    public ApiResult<String> JwtExceptionHandler(JwtException e) {
//        return ApiResult.fail(NO_PERMISSION, "用户登录异常----------" + e.getMessage(), e.getMessage());
//    }
//
//}
