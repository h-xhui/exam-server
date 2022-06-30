package com.exam.pojo.dto;

import lombok.Data;

/**
 * @author:hzh
 * @create:2022/6/5
 **/
@Data
public class QuestionDto {
    private Long userId;
    private String content;
    private String answer;
    private Integer type;
    private String a;
    private String b;
    private String c;
    private String d;
}
