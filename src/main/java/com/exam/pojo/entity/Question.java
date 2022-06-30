package com.exam.pojo.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author:hzh
 * @create:2022/5/22
 **/
@Data
public class Question {
    private long id;
    private long userId;
    private String content;
    private String answer;
    private int type;
    private Date updateTime;
}
