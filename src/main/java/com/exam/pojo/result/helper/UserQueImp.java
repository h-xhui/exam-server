package com.exam.pojo.result.helper;

import lombok.Data;

import java.util.List;

@Data
public class UserQueImp {
    private Long id;
    private String content;
    private List<String> choice;
    private Integer type;
    //学生本题得分
    private Long queScore;
    //题目分数
    private Long score;
    //学生答案
    private String queAnswer;
    //正确答案
    private String answer;
}
