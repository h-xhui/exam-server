package com.exam.pojo.result;

import lombok.Data;

import java.util.Date;


/**
 * @author:hzh
 * @create:2022/6/5
 * 按班级分类考试信息
 **/
@Data
public class ExamListForClass {
    private Long examId;
    private String title;
    private String clazzName;
    private Date beginTime;
    private Date endTime;
}
