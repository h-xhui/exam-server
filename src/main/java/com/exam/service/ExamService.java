package com.exam.service;

import com.exam.pojo.dto.CreateExamDto;
import com.exam.pojo.entity.UserExam;
import com.exam.pojo.result.ApiResult;
import com.exam.pojo.result.ExamResult;
import com.exam.pojo.result.helper.QuestionImpl;

import java.util.List;

/**
 * @author hongjinhui
 * 2022/5/20
 */

public interface ExamService {
    List<QuestionImpl> getQuestionInfoById(Long examId);
    ExamResult getExamInfo(Long examId);
    List<Long> getExamIdByUserId(Long userId);
    void autoJudgment(Long stuId, Long examId);

    ApiResult<?> getExamListByClass(String clas,Long examId);

    ApiResult<?> getExamListBy();

    ApiResult<?> createExam(CreateExamDto createExamDto);

    List<UserExam> getUserExamInfo(Long userId);

    void setUserJoinExam(Long examId, Long userId);

    void submitPaper(Long stuId, Long examId, String answer, String questionIds);
}
