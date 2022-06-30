package com.exam.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author:hzh
 * @create:2022/6/6
 **/
@Data
public class CreateExamDto {
    //@NotBlank(message = "班级名称不能为空")
    private String clazzName;
    //@NotBlank(message = "试卷不能为空")
    private Long papersId;
    //@NotBlank(message = "开始时间不能为空")
    private Date startTime;
    //@NotBlank(message = "结束时间不能为空")
    private Date endTime;
    private Long userId;
    //@NotBlank(message = "班级名称不能为空")
    private String examTitle;
}
