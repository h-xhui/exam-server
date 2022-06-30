package com.exam.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.exam.dao.UserMapper;
import com.exam.pojo.VO.UserVO;
import com.exam.pojo.dto.LoginDTO;
import com.exam.pojo.dto.UserDTO;
import com.exam.pojo.entity.User;
import com.exam.pojo.result.ApiResult;
import com.exam.service.UserService;
import com.exam.utils.JWTUtils;
import com.exam.utils.RedisUtils;
import com.exam.utils.SendMail;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.exam.common.ResultCode.CODE_ERROR;
import static com.exam.common.ResultCode.NO_PERMISSION;
import static com.exam.utils.RedisConstants.*;

/**
 * @author hongjinhui
 * 2022/5/20
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SendMail sendMail;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private JWTUtils jwtUtils;

    @Override
    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }


    @Override
    public ApiResult<?> sendCode(String email) {

        String code = RandomUtil.randomNumbers(6);
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY + email, code, LOGIN_CODE_TTL, TimeUnit.MINUTES);

        sendMail.sendStringEmail("ktl18wzj@qq.com", email, "考试登录验证码", "您的验证码为：" + code);

        System.out.println("验证码为：" + code);
        return ApiResult.success();
    }

    @Override
    public User login(String phone) {
        return userMapper.getUserByPhone(phone);
    }

//    @Override
//    public ApiResult<?> login(LoginDTO loginDto) {
//        String codeCache = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + loginDto.getEmail());
//        String code = loginDto.getCode();
//        if (codeCache == null || !codeCache.equals(code)) {
//            return ApiResult.fail(CODE_ERROR, "验证码错误", "验证错误");
//        }
//        User user = userMapper.getByEmail(loginDto.getEmail());
//        if (user == null) {
//            return ApiResult.fail(NO_PERMISSION, "用户不存在", "用户不存在");
//        }
//        UserDTO userDTO = new UserDTO(user.getId(), user.getName());
//        String userStr = JSONUtil.toJsonStr(userDTO);
//        String jwt = jwtUtils.creatToken(userStr);
//        System.out.println(jwt);
//        return ApiResult.success(jwt);
//    }

    @Override
    public ApiResult<?> register(User user) {
        boolean save = userMapper.save(user);
        if (save)
            redisUtils.set(USER_KEY + user.getEmail(), user, USER_TTL, TimeUnit.MINUTES);
        return ApiResult.success(save);
    }

    @Override
    public ApiResult<?> logout(HttpServletRequest request) {
        String jwt = request.getHeader(jwtUtils.getHeader());
        Claims claims = jwtUtils.getClaimsByToken(jwt);
        long time = (claims.getExpiration().getTime() - new Date().getTime()) / 60000;
        redisUtils.setRandom(jwt, "", time, TimeUnit.MINUTES);
        return ApiResult.success();
    }

    @Override
    public ApiResult<?> update(UserVO user) {
        User one = userMapper.getByEmail(user.getEmail());
        Assert.notNull(one, "用户不存在");
        boolean update = userMapper.update(user);
        return ApiResult.success(update);

    }

    /**
     * 更新密码
     *
     * @param request
     * @param oldStr  老密码
     * @param newStr  新密码
     * @return
     */
    @Override
    public ApiResult<?> upPassword(HttpServletRequest request, String oldStr, String newStr) {
        String jwt = request.getHeader(jwtUtils.getHeader());
        Long id = jwtUtils.getUserInfo(jwt).getId();
        User user = userMapper.getById(id);
        Assert.notNull(user, "用户不存在");
        if (!oldStr.equals(user.getPassword())) {
            return ApiResult.fail(NO_PERMISSION, "密码错误", "密码错误");
        }
        boolean updatePassword = userMapper.updatePassword(id, newStr);
        return ApiResult.success(updatePassword);
    }


}
