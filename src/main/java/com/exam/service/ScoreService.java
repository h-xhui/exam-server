package com.exam.service;

import com.exam.pojo.result.ScoreResult;

import java.util.List;

/**
* @author:hzh
* @create:2022/5/27
*
**/

public interface ScoreService {
    List<ScoreResult> getScoreByClazz(String clazz,long examId);
    List<ScoreResult> getScoreByStudentId(String StudentId,long examId);
    List<ScoreResult> getScoreByName(String Name,long examId);
}
