package com.exam.service;

import com.alibaba.fastjson.JSONArray;
import com.exam.pojo.dto.ExamDTO;
import com.exam.pojo.dto.MarkingDTO;
import com.exam.pojo.result.ApiResult;

import java.util.List;

public interface PaperService {
    ApiResult<?> creatPaper(List<ExamDTO> list, String title, String author);

    ApiResult<?> getUserPaperInfo(Long userId, Long examId);

    ApiResult<?> mark(Long examId, Long userId, List<MarkingDTO> list);

    void createPaperByTag(String typeJson, String tagJson, String numJson,
                          String scoreJson, String title, String author);
    void createPaperByQuestion(String questionList, String title, String author);
}
