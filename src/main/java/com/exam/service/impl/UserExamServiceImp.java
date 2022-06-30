package com.exam.service.impl;

import com.exam.dao.UserExamMapper;
import com.exam.pojo.entity.UserExam;
import com.exam.service.UserExamService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author:hzh
 * @create:2022/5/27
 **/
public class UserExamServiceImp implements UserExamService {
    @Autowired
    UserExamMapper userExamMapper;

    public void addUserExam(UserExam userExam){
        userExamMapper.addUserExam(userExam);
    }
}
