package com.exam.pojo.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author hongjinhui
 * 2022/6/13
 */
@Data
public class UserExamInfo {
    private Long userId;
    private Long examId;
    private Integer join;
    private String examName;
    private Date startTime;
    private Date endTime;
}
