package com.exam.pojo.entity;

import lombok.Data;

/**
 * @author:hzh
 * @create:2022/6/5
 **/
@Data
public class Choice {
    private Long id;
    private Long questionId;
    private String content;
    private boolean ans;
}
