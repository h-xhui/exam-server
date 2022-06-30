package com.exam.pojo.result.helper;

import lombok.Data;

/**
 * @author:hzh
 * @create:2022/6/5
 **/
@Data
public class UserExamImp {
    private Long examId;
    private String title;
    private int score;
    private int join;
    private Integer userId;
}
