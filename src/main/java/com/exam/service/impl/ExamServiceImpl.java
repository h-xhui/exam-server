package com.exam.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.exam.dao.*;
import com.exam.pojo.dto.CreateExamDto;
import com.exam.pojo.entity.Exam;
import com.exam.pojo.entity.Question;
import com.exam.pojo.entity.UserExam;
import com.exam.pojo.result.ApiResult;
import com.exam.pojo.result.ExamListByClass;
import com.exam.pojo.result.ExamListForClass;
import com.exam.pojo.result.ExamResult;
import com.exam.pojo.result.helper.QuestionImpl;
import com.exam.pojo.result.helper.ScoreIMP;
import com.exam.pojo.result.helper.UserExamImp;
import com.exam.service.ExamService;
import com.exam.service.UserExamQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hongjinhui
 * 2022/5/20
 */
@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserExamMapper userExamMapper;

    @Autowired
    private UserExamQuestionService userExamQuestionService;

    @Autowired
    private UserExamQuestionMapper userExamQuestionMapper;


    /**
     * 获得对应考试id的试题信息
     *
     * @param examId 考试id
     * @return 考试信息
     */
    @Override
    public List<QuestionImpl> getQuestionInfoById(Long examId) {
        List<Question> questions = examMapper.getQuestionById(examId);
        List<QuestionImpl> res = new ArrayList<>(20);
        for (Question question : questions) {
            QuestionImpl now = new QuestionImpl();
            now.setContent(question.getContent());
            now.setChoice(questionMapper.getChoiceById(question.getId()));
            now.setType(question.getType());
            now.setQuestionId(question.getId());
            res.add(now);
        }

        return res;
    }

    @Override
    public ExamResult getExamInfo(Long examId) {
        Exam exam = examMapper.getExamById(examId);
        ExamResult examResult = new ExamResult();
        examResult.setInstId(0L);
        examResult.setStartTime(exam.getStartTime());
        examResult.setEndTime(exam.getEndTime());
        examResult.setQuestions(getQuestionInfoById(examId));
        return examResult;
    }

    @Override
    public List<Long> getExamIdByUserId(Long userId) {
        return examMapper.getExamIdByUserId(userId);
    }

    /**
     * @author hzh
     * 根据class查询考试信息
     **/
    @Override
    public ApiResult<?> getExamListByClass(String clas,Long id) {
        List<ScoreIMP> listByClazz = userMapper.getListByClazz(clas);
        List<ExamListByClass> examListByClasses = new ArrayList<>();
        for (ScoreIMP byClazz : listByClazz) {

            List<UserExamImp> userExamById = userExamMapper.getUserExamById(byClazz.getId(),id);

            for (UserExamImp userExamImp : userExamById) {
                ExamListByClass examListByClass = new ExamListByClass();
                examListByClass.setName(byClazz.getName());
                examListByClass.setStudentId(byClazz.getPhone());
                examListByClass.setTitle(userExamImp.getTitle());
                examListByClass.setCode(userExamImp.getScore());
                examListByClass.setJoin(userExamImp.getJoin());
                examListByClass.setUserId(userExamImp.getUserId());
                if(id==userExamImp.getExamId()){
                    examListByClasses.add(examListByClass);
                }
            }
        }

        return ApiResult.success(examListByClasses);
    }

    /**
     * @author hzh
     * 查询班级考试信息
     **/
    @Override
    public ApiResult<?> getExamListBy() {
        List<ExamListForClass> examListForClassList=new ArrayList<>();
        List<String> clazzName=userMapper.getClazzName();
        for (String s : clazzName) {
            long id=userMapper.getOneUserIdByClazzName(s);
            List<Long> examId = userExamMapper.getExamId(id);
            for (Long aLong : examId) {
                Exam examById = examMapper.getExamById(aLong);
                ExamListForClass exam=new ExamListForClass();
                exam.setExamId(examById.getId());
                exam.setTitle(exam.getTitle());
                exam.setClazzName(s);
                exam.setBeginTime(examById.getStartTime());
                exam.setEndTime(examById.getEndTime());
                exam.setTitle(examById.getTitle());

                if(StrUtil.isNotEmpty(exam.getTitle())){
                    examListForClassList.add(exam);
                }
            }
        }
        return ApiResult.success(examListForClassList);
    }

    @Transactional
    @Override
    public ApiResult<?> createExam(CreateExamDto createExamDto) {
        Exam exam=new Exam();
        exam.setTitle(createExamDto.getExamTitle());
        exam.setEndTime(createExamDto.getEndTime());
        exam.setStartTime(createExamDto.getStartTime());
        exam.setCreateTime(new DateTime());
        exam.setUserId(createExamDto.getUserId());
        exam.setPapersId(createExamDto.getPapersId());
        examMapper.addExam(exam);
        Long neW = examMapper.getNeW();
        List<ScoreIMP> listByClazz = userMapper.getListByClazz(createExamDto.getClazzName());
        for (ScoreIMP byClazz : listByClazz) {
            UserExam userExam=new UserExam();
            userExam.setUserId(byClazz.getId());
            userExam.setExamId(neW);
            userExam.setJoin(0);
            userExam.setScore(-1);
            userExamMapper.addUserExam(userExam);
        }
        return ApiResult.success();
    }

    @Override
    public List<UserExam> getUserExamInfo(Long userId) {
        return userExamMapper.getExamInfoByUser(userId);
    }

    @Override
    public void setUserJoinExam(Long examId, Long userId) {
        userExamMapper.setUserJoinExam(examId, userId);
    }

    @Transactional
    @Override
    public void submitPaper(Long stuId, Long examId, String answer, String questionIds) {
        JSONArray answers = JSON.parseArray(answer);
        JSONArray questionList = JSON.parseArray(questionIds);
        userExamMapper.setUserJoinExam(examId, stuId);
        for (int i = 0; i < answers.size(); ++i) {
            userExamQuestionService.insertAnswer(stuId, examId, questionList.getLong(i), answers.getString(i));
        }
    }

    /**
     * 自动
     * @param stuId
     * @param examId
     */
    @Override
    public void autoJudgment(Long stuId, Long examId) {
        userExamQuestionMapper.autoJudgePaper(examId, stuId);
    }


}
