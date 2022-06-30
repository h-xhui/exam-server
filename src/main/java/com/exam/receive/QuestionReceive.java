package com.exam.receive;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author:hzh
 * @create:2022/5/23
 **/
@Data
public class QuestionReceive {

    private long userId;

    @NotBlank(message = "题目内容不能为空")
    private String content;

    @NotBlank(message = "题目答案不能为空")
    private String answer;

    private int type;

}
