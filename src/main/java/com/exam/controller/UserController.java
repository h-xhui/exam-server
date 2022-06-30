package com.exam.controller;

import com.exam.common.ResultCode;
import com.exam.pojo.VO.UserVO;
import com.exam.pojo.dto.LoginDTO;
import com.exam.pojo.entity.User;
import com.exam.pojo.result.ApiResult;
import com.exam.service.ExamService;
import com.exam.service.UserService;
import com.exam.utils.JWTUtils;
import com.exam.utils.RegexUtils;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hongjinhui
 * 2022/5/20
 */
@Validated
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ExamService examService;

    @Autowired
    private JWTUtils jwtUtils;


    @ResponseBody
    @RequestMapping("/test")
    public ApiResult<?> getAllUser() {
        return ApiResult.success(userService.getAllUser());
    }

    /**
     * 验证码
     *
     * @param email 邮箱
     * @return
     */
    @ResponseBody
    @PostMapping("/code")
    public ApiResult<?> sendCode(@RequestParam("email") String email) {
        if (RegexUtils.isEmailInvalid(email)) {
            return ApiResult.fail("A0401", "邮箱格式错误", "邮箱格式错误");
        }
        return userService.sendCode(email);
    }

//    /**
//     * 登录
//     *
//     * @param loginDto 学号、密码、类型、验证码
//     * @return
//     */
//    @ResponseBody
//    @PostMapping("/login")
//    public ApiResult<?> login(@RequestBody @Validated LoginDTO loginDto) {
//        return userService.login(loginDto);
//    }

    @ResponseBody
    @RequestMapping("/login")
    public ApiResult<?> login(String phone, String password, Integer type) {
        if (StringUtil.isNullOrEmpty(password) || StringUtil.isNullOrEmpty(phone)) {
            return ApiResult.fail(ResultCode.MISSING_PARAM, "请输入学号或密码", "请输入学号或密码");
        }
        User user = userService.login(phone);
        if (user == null) {
            return ApiResult.fail(ResultCode.USERNAME_INVALID, "学号错误", "用户不存在");
        }
//        if (type == 0 && user.getType() == 1) {
//            return ApiResult.fail(ResultCode.CODE_ERROR, "type error", "这里是学生端，请前往教师端");
//        }
//        if (type == 1 && user.getType() == 0) {
//            return ApiResult.fail(ResultCode.CODE_ERROR, "type error", "这里是教师端，请前往学生端");
//        }
        if (password.equals(user.getPassword())) {
            user.setPassword("");
            return ApiResult.success(user);
        } else {
            return ApiResult.fail(ResultCode.PASSWORD_WRONG, "密码错误", "密码错误");
        }
    }

    /**
     * 注册
     *
     * @param user 学号、密码、姓名、类型、性别、邮箱
     * @return
     */
    @ResponseBody
    @PostMapping("/register")
    public ApiResult<?> register(@RequestBody @Validated User user) {
        return userService.register(user);
    }

    /**
     * 登出
     *
     * @param request
     * @return
     */
    @ResponseBody
    @GetMapping("/logout")
    public ApiResult<?> logout(HttpServletRequest request) {
        return userService.logout(request);
    }

    /**
     * 修改信息
     *
     * @param user
     * @return
     */
    @ResponseBody
    @PutMapping("/update")
    public ApiResult<?> update(@RequestBody @Validated UserVO user) {
        return userService.update(user);
    }

    /**
     * 更新密码
     *
     * @param request
     * @param oldStr
     * @param newStr
     * @return
     */
    @ResponseBody
    @PutMapping("/updatePassword")
    public ApiResult<?> updatePassword(HttpServletRequest request, @RequestParam("oldStr") String oldStr, @RequestParam("newStr") String newStr) {
        return userService.upPassword(request, oldStr, newStr);
    }

    /**
     * 查询该用户的考试id
     *
     * @param request
     * @return
     */
    @ResponseBody
    @GetMapping("/getExamId")
    public ApiResult<?> getExamId(HttpServletRequest request) {
        return ApiResult.success(
                examService.getExamIdByUserId(
                        jwtUtils.getUserInfo(request.getHeader(jwtUtils.getHeader()))
                                .getId()));
    }

}
