package com.exam.controller;

import cn.hutool.http.HttpResponse;
import com.exam.pojo.result.ApiResult;
import com.exam.pojo.result.ScoreResult;
import com.exam.service.ScoreService;
import com.exam.utils.excel.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author hongjinhui
 * 2022/6/4
 */
@Controller
@RequestMapping("/score")
public class ScoreController {
    @Autowired
    private ScoreService scoreService;
    @RequestMapping("/export")
    public void export(HttpServletResponse response, @RequestParam String clazz, @RequestParam Long examId, @RequestParam String examName) {
        clazz = clazz.substring(0, 5);
        List<ScoreResult> scores = scoreService.getScoreByClazz(clazz, examId);
        // 表头数据
        List<Object> head = Arrays.asList("考试名称","班级","姓名","学号", "成绩");
        // 数据汇总
        List<List<Object>> sheetDataList = new ArrayList<>();
        sheetDataList.add(head);
        for (ScoreResult scoreResult : scores) {
            List<Object> now = new ArrayList<>();
            now.add(scoreResult.getExamTitle());
            now.add(scoreResult.getClazz());
            now.add(scoreResult.getName());
            now.add(scoreResult.getPhone());
            now.add(scoreResult.getScore());
            sheetDataList.add(now);
        }
        // 导出数据
        ExcelUtils.export(response,"成绩表", sheetDataList);
    }
}
