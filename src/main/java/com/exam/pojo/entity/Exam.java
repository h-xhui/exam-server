package com.exam.pojo.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author hongjinhui
 * 2022/5/23
 */
@Data
public class Exam {
    private Long id;
    private Long userId;
    private String title;
    private Date startTime;
    private Date endTime;
    private Date createTime;
    private Long papersId;
}
