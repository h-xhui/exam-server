package com.exam.dao;

import com.exam.pojo.entity.Question;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author:hzh
 * @create:2022/5/22
 **/
@Mapper
public interface QuestionMapper {
    void addQuestion(Question question);

    void delete(int id);

    void update(Question question);

    long getNew();

    List<Question> getQuestionList();

    Question getQuestionById(int id);

    List<String> getChoiceById(Long questionId);

    Long getScoreByAndExamId(Long examId, Long questionId);

}
