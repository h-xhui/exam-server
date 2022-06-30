package com.exam.service;

import com.exam.pojo.entity.UserExamQuestion;

import java.util.List;

/**
 * @author:hzh
 * @create:2022/5/27
 **/
public interface UserExamQuestionService {
    List<UserExamQuestion> getUserExamQuestionByUserId(long userId);
    void insertAnswer(Long userId, Long examId, Long questionId, String answer);
}
