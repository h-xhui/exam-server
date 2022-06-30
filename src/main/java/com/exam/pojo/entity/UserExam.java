package com.exam.pojo.entity;

import lombok.Data;

/**
 * @author:hzh
 * @create:2022/5/27
 **/
@Data
public class UserExam {
    private long userId;
    private long examId;
    private int score;
    private int join;
}
