package com.exam.dao;

import com.exam.pojo.entity.UserExamQuestion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author:hzh
 * @create:2022/5/27
 **/
@Mapper
public interface UserExamQuestionMapper {
    List<UserExamQuestion> getUserExamQuestionByUserId(long userId);

    List<UserExamQuestion> getByUserIdAndExamId(Long userId, Long examId);


    void updateScoreByThreeId(Long userId, Long examId, Long questionId, Long score);

    List<Long> getScoreByUserIdExamID(Long userId, Long examId);

    void insertAnswer(Long userId, Long examId, Long questionId, String answer);

    void autoJudgePaper(Long examId, Long userId);
}
