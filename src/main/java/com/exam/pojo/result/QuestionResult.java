package com.exam.pojo.result;

import com.exam.pojo.entity.Question;
import lombok.Data;

import java.util.List;

/**
 * @author hongjinhui
 * 2022/6/18
 */
@Data
public class QuestionResult {
    private String content;
    private Integer type;
    private List<String> choices;
    private Long questionId;
    private String answer;
    private boolean checked;
    private Integer score;
}
