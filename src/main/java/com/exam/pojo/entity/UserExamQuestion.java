package com.exam.pojo.entity;

import lombok.Data;

/**
 * @author:hzh
 * @create:2022/5/27
 **/
@Data
public class UserExamQuestion {
    private long userId;
    private long examId;
    private long questionId;
    private String answer;
    private int score;
}
