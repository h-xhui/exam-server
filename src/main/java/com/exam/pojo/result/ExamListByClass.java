package com.exam.pojo.result;

import lombok.Data;

import java.util.Date;

/**
 * @author:hzh
 * @create:2022/6/5
 **/
@Data
public class ExamListByClass {
    private String name;
    private String studentId;
    private int code;
    private String title;
    private int join;
    private Integer userId;
}
