package com.exam.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.exam.common.ResultCode;
import com.exam.dao.ChoiceMapper;
import com.exam.dao.QuestionMapper;
import com.exam.dao.QuestionTagMapper;
import com.exam.dao.TagMapper;
import com.exam.pojo.dto.QuestionDto;
import com.exam.pojo.dto.QuestionExcel;
import com.exam.pojo.entity.Choice;
import com.exam.pojo.entity.Question;
import com.exam.pojo.entity.QuestionTag;
import com.exam.pojo.entity.Tag;
import com.exam.pojo.result.ApiResult;
import com.exam.pojo.result.QuestionResult;
import com.exam.service.QuestionService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author:hzh
 * @create:2022/5/22
 **/

@Service
public class QuestionServiceImp implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private ChoiceMapper choiceMapper;
    @Autowired
    private QuestionTagMapper questionTagMapper;
    @Autowired
    private TagMapper tagMapper;

    @Override
    public void addQuestion(Question question) {
        questionMapper.addQuestion(question);
    }

    @Override
    public void deleteQuestionById(int id) {
        questionMapper.delete(id);
    }

    @Override
    public void updateQuestion(Question question) {
        questionMapper.update(question);
    }

    @Override
    public List<Question> getQuestionList() {
        return questionMapper.getQuestionList();
    }

    @Override
    public Question getQuestionById(int id) {
        return questionMapper.getQuestionById(id);
    }

    @Override
    public List<String> getChoiceById(Long questionId) {
        return questionMapper.getChoiceById(questionId);
    }

    @Override
    public List<QuestionResult> getQuestionInfo() {
        List<Question> questions = questionMapper.getQuestionList();
        List<QuestionResult> results = new ArrayList<>();
        for (Question question : questions) {
            List<String> choices = questionMapper.getChoiceById(question.getId());
            if (choices.size() == 4) {
                choices.set(0, "A:"+choices.get(0));
                choices.set(1, "B:"+choices.get(1));
                choices.set(2, "C:"+choices.get(2));
                choices.set(3, "D:"+choices.get(3));
            }
            QuestionResult questionResult = new QuestionResult();
            questionResult.setContent(question.getContent());
            questionResult.setType(question.getType());
            questionResult.setChoices(choices);
            questionResult.setQuestionId(question.getId());
            questionResult.setChecked(false);
            if (question.getAnswer() == null || "".equals(question.getAnswer())) {
                questionResult.setAnswer("略");
            } else {
                questionResult.setAnswer(question.getAnswer());
            }
            questionResult.setScore(0);

            results.add(questionResult);
        }
        return results;
    }

    @Transactional
    @Override
    public void importQuestion(List<QuestionExcel> questions, Long userId, String username) {
        for (QuestionExcel questionExcel : questions) {
            //System.out.println(questionExcel);
            Tag tag = tagMapper.getTagByName(questionExcel.getTag());
            if (tag == null) {
                tag = new Tag();
                tag.setName(questionExcel.getTag());
                tag.setUserName(username);
                tag.setUpdateTime(new Date());
                tagMapper.save(tag);
            }
            QuestionDto questionDto = new QuestionDto();
            questionDto.setUserId(userId);
            questionDto.setContent(questionExcel.getContent());
            questionDto.setAnswer(questionExcel.getAnswer());
            questionDto.setType(questionExcel.getType());
            questionDto.setA(questionExcel.getA());
            questionDto.setB(questionExcel.getB());
            questionDto.setC(questionExcel.getC());
            questionDto.setD(questionExcel.getD());
            createQuestion(questionDto,tag.getId());
        }
    }

    @Override
    public void createQuestion(QuestionDto questionDto, long tagId) {
        Question question=new Question();
        BeanUtils.copyProperties(questionDto,question);
        question.setUpdateTime(new DateTime());
        int type = question.getType();

        questionMapper.addQuestion(question);
        long aNew = questionMapper.getNew();
        if(type==1||type==2) {
            String answer = questionDto.getAnswer();
            char[] chars = answer.toCharArray();
            int ansType = 0;
            int num=65;
            for (int i = 0; i < 4; i++) {
                Choice choice = new Choice();
                choice.setQuestionId(aNew);
                if (i == 0) {
                    choice.setContent(questionDto.getA());
                }
                if (i == 1) {
                    choice.setContent(questionDto.getB());
                }
                if (i == 2) {
                    choice.setContent(questionDto.getC());
                }
                if (i == 3) {
                    choice.setContent(questionDto.getD());
                }
                if(ansType!=chars.length){
                    if (chars[ansType] == num) {
                        choice.setAns(true);
                        ansType++;
                    } else {
                        choice.setAns(false);
                    }
                    num++;
                }
                choiceMapper.addChoice(choice);
            }
        }
        QuestionTag questionTag = new QuestionTag();
        questionTag.setQuestionId(aNew);
        questionTag.setTagId(tagId);
        questionTagMapper.addQuestionTag(questionTag);
    }
}
