package com.exam.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.exam.pojo.dto.ExamDTO;
import com.exam.pojo.dto.MarkingDTO;
import com.exam.pojo.receive.QuestionList;
import com.exam.pojo.result.ApiResult;
import com.exam.pojo.result.QuestionResult;
import com.exam.receive.QuestionReceive;
import com.exam.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/paper")
public class PaperController {

    @Autowired
    private PaperService paperService;

    @ResponseBody
    @PostMapping("/createPaper")
    public ApiResult<?> creatExam(String typeJson, String tagJson, String numJson,
                                  String scoreJson, String title, String author) {
        paperService.createPaperByTag(typeJson, tagJson, numJson, scoreJson, title, author);
        return ApiResult.success();
    }


    /**
     * 获取考试答题信息
     *
     * @param userId
     * @param examId
     * @return
     */
    @ResponseBody
    @GetMapping("getUserExam")
    public ApiResult<?> getUserPaperInfo(Long userId, @RequestParam Long examId) {
        return paperService.getUserPaperInfo(userId, examId);
    }

    /**
     * 批卷
     *
     * @param examId 考试id
     * @param userId 学生id
     * @return
     */
    @ResponseBody
    @PostMapping("/marking")
    public ApiResult<?> marking(Long examId, Long userId, String questions, String score) {
        JSONArray questionIds = JSONArray.parseArray(questions);
        JSONArray scores = JSONArray.parseArray(score);
        List<MarkingDTO> list = new ArrayList<>();
        for (int i = 0; i < questionIds.size(); ++i) {
            MarkingDTO markingDTO = new MarkingDTO(questionIds.getLong(i), scores.getLong(i));
            list.add(markingDTO);
        }
        System.out.println(list);
        return paperService.mark(examId, userId, list);
    }
    @ResponseBody
    @RequestMapping("/createPaperBySelect")
    public ApiResult<?> createPaperBySelect(String questionList, String title, String author) {
        paperService.createPaperByQuestion(questionList, title, author);
        return ApiResult.success();
    }
}
