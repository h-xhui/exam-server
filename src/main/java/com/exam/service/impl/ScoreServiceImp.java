package com.exam.service.impl;

import com.exam.dao.ExamMapper;
import com.exam.dao.UserExamQuestionMapper;
import com.exam.dao.UserMapper;
import com.exam.pojo.entity.Exam;
import com.exam.pojo.entity.UserExamQuestion;
import com.exam.pojo.result.ScoreResult;
import com.exam.pojo.result.helper.ScoreIMP;
import com.exam.service.ScoreService;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:hzh
 * @create:2022/5/27
 **/
@Service
public class ScoreServiceImp implements ScoreService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserExamQuestionMapper userExamQuestionMapper;
    @Autowired
    ExamMapper examMapper;

    @Override
    public List<ScoreResult> getScoreByClazz(String clazz,long examId) {

        List<ScoreIMP> listByClazz = userMapper.getListByClazz(clazz);
        return  getScore(listByClazz,examId);
    }

    @Override
    public List<ScoreResult> getScoreByStudentId(String studentId,long examId) {

        List<ScoreIMP> listByClazz = userMapper.getListById(studentId);
        for (ScoreIMP byClazz : listByClazz) {
            System.out.println(byClazz);
        }

        return getScore(listByClazz,examId);

    }

    @Override
    public List<ScoreResult> getScoreByName(String name,long examId) {
        List<ScoreIMP> listByClazz = userMapper.getListByName(name);
        return getScore(listByClazz,examId);

    }

    private List<ScoreResult> getScore(List<ScoreIMP> listByClazz,long examId){

        List<ScoreResult> scoreResultList=new ArrayList<>();

        for (ScoreIMP byClazz : listByClazz) {
            ScoreResult scoreResult=new ScoreResult();
            long id = byClazz.getId();
            List<UserExamQuestion> userExamQuestionByUserId = userExamQuestionMapper.getUserExamQuestionByUserId(id);
            scoreResult.setClazz(byClazz.getClazz());
            scoreResult.setExamTitle(examMapper.getExamById(examId).getTitle());
            scoreResult.setName(byClazz.getName());
            scoreResult.setPhone(byClazz.getClazz());
            scoreResult.setId(byClazz.getId());
            int score=0;
            for (UserExamQuestion userExamQuestion : userExamQuestionByUserId) {
                if(userExamQuestion.getExamId()==examId){
                    score=score+userExamQuestion.getScore();
                }
            }
            scoreResult.setScore(score);
            scoreResultList.add(scoreResult);
        }
        return scoreResultList;
    }
}
