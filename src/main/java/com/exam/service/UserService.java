package com.exam.service;

import com.exam.pojo.VO.UserVO;
import com.exam.pojo.dto.LoginDTO;
import com.exam.pojo.entity.User;
import com.exam.pojo.result.ApiResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author hongjinhui
 * 2022/5/20
 */

public interface UserService {
    List<User> getAllUser();

    ApiResult<?> sendCode(String email);

//    ApiResult<?> login(LoginDTO loginDto);
    User login(String phone);

    ApiResult<?> register(User user);

    ApiResult<?> logout(HttpServletRequest request);

    ApiResult<?> update(UserVO user);

    ApiResult<?> upPassword(HttpServletRequest request, String oldStr, String newStr);


}
