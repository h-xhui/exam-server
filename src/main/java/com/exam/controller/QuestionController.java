package com.exam.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.exam.common.ResultCode;
import com.exam.dao.ChoiceMapper;
import com.exam.dao.QuestionMapper;
import com.exam.dao.QuestionTagMapper;
import com.exam.pojo.dto.QuestionDto;
import com.exam.pojo.dto.QuestionExcel;
import com.exam.pojo.entity.Choice;
import com.exam.pojo.entity.QuestionTag;
import com.exam.pojo.result.QuestionResult;
import com.exam.receive.QuestionReceive;
import com.exam.pojo.entity.Question;
import com.exam.pojo.result.ApiResult;
import com.exam.service.QuestionService;
import com.exam.service.TagService;
import com.exam.utils.excel.ExcelUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author:hzh
 * @create:2022/5/22
 **/
@Controller
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    TagService tagService;
    @Autowired
    QuestionTagMapper questionTagMapper;
    @Autowired
    ChoiceMapper choiceMapper;
    @Autowired
    QuestionService questionService;

    /**
     * @author hzh
     * 试题的录入
     **/
    @ResponseBody
    @PostMapping("/question_input")
    public ApiResult<?> questionInput(QuestionDto questionDto, long tagId){
        questionService.createQuestion(questionDto, tagId);
        return ApiResult.success();
    }
    @ResponseBody
    @RequestMapping("/getQuestionList")
    public ApiResult<?> getQuestionList() {
        return ApiResult.success(questionService.getQuestionInfo());
    }

    @ResponseBody
    @RequestMapping("/importQuestion")
    public ApiResult<?> importQuestion(@RequestParam("file") MultipartFile file, Long userId, String username) throws Exception {
        List<QuestionExcel> questions = ExcelUtils.readMultipartFile(file, QuestionExcel.class);
        questionService.importQuestion(questions, userId,username);
        return ApiResult.success();
    }

    @RequestMapping("/exportQuestionTemplate")
    public void getQuestionTemplate(HttpServletResponse response) {
        // 表头数据
        List<Object> head = Arrays.asList("知识点","类型","题干","选项A", "选项B", "选项C", "选项D", "答案");
        // 数据汇总
        List<List<Object>> sheetDataList = new ArrayList<>();
        sheetDataList.add(head);
        // 导出数据
        ExcelUtils.export(response,"题目模板", sheetDataList);
    }

}
