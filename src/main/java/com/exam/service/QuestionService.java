package com.exam.service;

import com.exam.pojo.dto.QuestionDto;
import com.exam.pojo.dto.QuestionExcel;
import com.exam.pojo.entity.Question;
import com.exam.pojo.entity.Tag;
import com.exam.pojo.result.QuestionResult;

import java.util.List;

/**
 * @author:hzh
 * @create:2022/5/22
 **/
/**
 * @author:hzh
 * @create:2022/5/22
 **/
public interface QuestionService {
    void addQuestion(Question question);
    void deleteQuestionById(int id);
    void updateQuestion(Question question);
    List<Question> getQuestionList();
    Question getQuestionById(int id);
    List<String> getChoiceById(Long questionId);
    List<QuestionResult> getQuestionInfo();
    void importQuestion(List<QuestionExcel> questions, Long userId, String username);
    void createQuestion(QuestionDto questionDto, long tagId);
}
