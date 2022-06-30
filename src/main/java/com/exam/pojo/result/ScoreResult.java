package com.exam.pojo.result;

import lombok.Data;

/**
 * @author:hzh
 * @create:2022/5/27
 **/
@Data
public class ScoreResult {

    private long id;
    private String name;
    private String clazz;
    private String examTitle;
    private String phone;
    private int score;
}
