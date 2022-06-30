package com.exam.dao;

import com.exam.pojo.entity.Exam;
import com.exam.pojo.entity.Question;
import com.exam.pojo.result.PapersList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author hongjinhui
 * 2022/5/26
 */
@Mapper
public interface ExamMapper {
    List<Question> getQuestionById(Long examId);
    Exam getExamById(Long examId);
    List<Long> getExamIdByUserId(Long userId);
    Boolean addExam(Exam exam);
    Long getNeW();
    List<PapersList> getAllList();

    Long getPaperIdById(Long examId);

    void insertAnswer(Long userId, Long examId, Long questionId, String answer);
}
