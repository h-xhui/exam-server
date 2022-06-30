package com.exam.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.exam.dao.*;
import com.exam.pojo.dto.ExamDTO;
import com.exam.pojo.dto.MarkingDTO;
import com.exam.pojo.entity.Paper;
import com.exam.pojo.entity.Question;
import com.exam.pojo.entity.UserExamQuestion;
import com.exam.pojo.result.ApiResult;
import com.exam.pojo.result.PaperResult;
import com.exam.pojo.result.QuestionResult;
import com.exam.pojo.result.helper.UserQueImp;
import com.exam.service.PaperService;
import com.exam.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    private PapersMapper papersMapper;

    @Autowired
    private PaperQuestionMapper paperQuestionMapper;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private UserExamQuestionMapper userExamQuestionMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserExamMapper userExamMapper;

    /**
     * 生成试卷
     *
     * @param list   知识点，题目个数
     * @param title
     * @param author
     * @return
     */
    @Transactional
    @Override
    public ApiResult<?> creatPaper(List<ExamDTO> list, String title, String author) {
        Random random = new Random();
        Long type = 1L;
        Paper paper = new Paper("title", "author");
        papersMapper.save(paper);
        Long paperId = paper.getId();
        for (ExamDTO examDTO : list) {
            ArrayList<String> request = examDTO.getRequest();
            String tag = request.get(0);
            int count = Integer.parseInt(request.get(1));
            if (count == 0) {
                type++;
                continue;
            }
            Integer score = examDTO.getScore();
            List<Long> question = papersMapper.getIdByTagAndType(type, tag);
            ArrayList<Integer> questionIDs = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                int questionID = random.nextInt(question.size());
                while (questionIDs.contains(questionID)) {
                    questionID = random.nextInt(question.size());
                }
//                paperQuestionMapper.insert((long) questionID, paperId, score);
                questionIDs.add(questionID);
            }
            type++;
        }
        return ApiResult.success(paperId);
    }


    /**
     * 显示考生试卷答题信息
     *
     * @param examId
     * @return
     */
    @Override
    public ApiResult<?> getUserPaperInfo(Long userId, Long examId) {
        //Long userId = jwtUtils.getUserInfo(request.getHeader(jwtUtils.getHeader())).getId();
        // 自动判题
        userExamQuestionMapper.autoJudgePaper(examId, userId);
        List<UserExamQuestion> userExamQuestions = userExamQuestionMapper.getByUserIdAndExamId(userId, examId);
        Paper paper = papersMapper.getByExamId(examId);
        PaperResult paperResult = new PaperResult();
        paperResult.setId(paper.getId());
        paperResult.setAuthor(paper.getAuthor());
        paperResult.setTitle(paper.getTitle());
        List<UserQueImp> res = new ArrayList<>();
        for (UserExamQuestion userExamQuestion : userExamQuestions) {
            UserQueImp userQue = new UserQueImp();
            Question question = questionMapper.getQuestionById((int) userExamQuestion.getQuestionId());
            userQue.setId(userExamQuestion.getQuestionId());
            userQue.setQueScore((long) userExamQuestion.getScore());
            userQue.setQueAnswer(userExamQuestion.getAnswer());
            userQue.setAnswer(question.getAnswer());
            userQue.setContent(question.getContent());
            userQue.setType(question.getType());
            if (userQue.getType() == 1 || userQue.getType() == 2) {
                List<String> choices = questionMapper.getChoiceById(userExamQuestion.getQuestionId());
                choices.set(0, "A:"+choices.get(0));
                choices.set(1, "B:"+choices.get(1));
                choices.set(2, "C:"+choices.get(2));
                choices.set(3, "D:"+choices.get(3));
                userQue.setChoice(choices);
            }
            userQue.setScore(questionMapper.getScoreByAndExamId(examId, userExamQuestion.getQuestionId()));
            res.add(userQue);
        }
        paperResult.setUserQueImpList(res);
        return ApiResult.success(res);
    }

    /**
     * 打分
     *
     * @param list
     * @return
     */
    @Transactional
    @Override
    public ApiResult<?> mark(Long examId, Long userId, List<MarkingDTO> list) {
        for (MarkingDTO markingDTO : list) {
            userExamQuestionMapper.updateScoreByThreeId(userId, examId, markingDTO.getQuestionId(), markingDTO.getScore());
        }

        Long score = 0L;
        for (Long aLong : userExamQuestionMapper.getScoreByUserIdExamID(userId, examId)) {
            score += aLong;
        }
        userExamMapper.updateScoreByTwoId(examId, userId, score);
        return ApiResult.success();
    }

    @Transactional
    @Override
    public void createPaperByTag(String typeJson, String tagJson, String numJson, String scoreJson, String title, String author) {
        JSONArray typeList = JSON.parseArray(typeJson);
        JSONArray tagList = JSON.parseArray(tagJson);
        JSONArray numList = JSON.parseArray(numJson);
        JSONArray scoreList = JSON.parseArray(scoreJson);
        Paper paper = new Paper(title, author);
        papersMapper.save(paper);
        Long paperId = paper.getId();
        for (int i = 0; i < typeList.size(); ++i) {
            Integer type = typeList.getInteger(i);
            Long tadId = tagList.getLong(i);
            Integer num = numList.getInteger(i);
            if (num == 0) {
                continue;
            }
            List<Long> questions = papersMapper.getIdByTypeAndTagId(type, tadId);
            List<Long> randomQuestions = getKNumsByRandom(questions, num);

            for (Long questionId : randomQuestions) {
                papersMapper.insert(questionId, paperId, scoreList.getInteger(type-1));
            }
        }
    }

    /**
     * 蓄水池抽样算法，随机获取一个列表中的k个数
     * @param list 原列表
     * @param k 个数
     * @return 新列表
     */
    private List<Long> getKNumsByRandom(List<Long> list, Integer k) {
        List<Long> res = new ArrayList<>();
        if (k >= list.size()) {
            return list;
        }
        for (int i = 0; i < list.size(); ++i) {
            if (i < k) {
                res.add(list.get(i));
            } else {
                Random random = new Random();
                int m = random.nextInt(i+1);
                if (m < k) {
                    Long tmp = res.get(m);
                    res.set(m, list.get(i));
                    list.set(i, tmp);
                }
            }
        }
        return res;
    }

    @Transactional
    @Override
    public void createPaperByQuestion(String questionList, String title, String author) {
        JSONArray questions = JSON.parseArray(questionList);
        Paper paper = new Paper(title, author);
        papersMapper.save(paper);
        Long paperId = paper.getId();
        for (int i = 0; i < questions.size(); ++i) {
            QuestionResult questionResult = questions.getObject(i, QuestionResult.class);
            papersMapper.insert(questionResult.getQuestionId(), paperId, questionResult.getScore());
        }
    }


}
