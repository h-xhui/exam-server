package com.exam.dao;

import com.exam.pojo.entity.UserExam;
import com.exam.pojo.result.helper.UserExamImp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author:hzh
 * @create:2022/5/27
 **/
@Mapper
public interface UserExamMapper {
    void addUserExam(UserExam userExam);

    List<Long> getExamId(long userId);

    List<UserExamImp> getUserExamById(Long userId,Long examId);

    void updateScoreByTwoId(Long examId, Long userId, Long score);

    List<UserExam> getExamInfoByUser(Long userId);

    void setUserJoinExam(Long examId, Long userId);
}
