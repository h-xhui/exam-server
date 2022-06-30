package com.exam.dao;

import com.exam.pojo.VO.UserVO;
import com.exam.pojo.entity.User;
import com.exam.pojo.result.helper.ScoreIMP;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hongjinhui
 * 2022/5/20
 */
@Mapper
public interface UserMapper {
    List<User> getAllUser();

    User getByEmail(@Param("email") String email);

    boolean save(@Param("one") User user);

    boolean update(@Param("one") UserVO user);

    List<ScoreIMP> getListByClazz(String clazz);

    List<ScoreIMP> getListById(String phone);

    List<ScoreIMP> getListByName(String name);

    List<String> getClazzName();

    long getOneUserIdByClazzName(String s);

    User getById(@Param("id") Long id);

    boolean updatePassword(@Param("id") Long id, @Param("password") String password);

    User getUserByPhone(String phone);

}
