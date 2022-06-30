package com.exam.service.impl;


import com.exam.dao.ExamMapper;
import com.exam.dao.UserExamQuestionMapper;
import com.exam.pojo.entity.UserExamQuestion;
import com.exam.service.UserExamQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:hzh
 * @create:2022/5/27
 **/
@Service
public class  UserExamQuestionServiceImp implements UserExamQuestionService {
    @Autowired
    private UserExamQuestionMapper userExamQuestionMapper;

    @Autowired
    private ExamMapper examMapper;

    @Override
    public List<UserExamQuestion> getUserExamQuestionByUserId(long userId) {
        return userExamQuestionMapper.getUserExamQuestionByUserId(userId);
    }

    @Override
    public void insertAnswer(Long userId, Long examId, Long questionId, String answer) {
        examMapper.insertAnswer(userId, examId, questionId, answer);
    }
}
