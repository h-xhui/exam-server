package com.exam.pojo.result.helper;

import lombok.Data;

import java.util.List;

/**
 * @author hongjinhui
 * 2022/5/23
 */
@Data
public class QuestionImpl {
    private String content;
    private List<String> choice;
    private Integer type;
    private Long questionId;
}
