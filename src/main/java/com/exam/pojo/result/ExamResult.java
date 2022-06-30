package com.exam.pojo.result;

import com.exam.pojo.entity.Question;
import com.exam.pojo.result.helper.QuestionImpl;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author hongjinhui
 * 2022/5/23
 */
@Data
public class ExamResult {
    private Long instId;
    private Date startTime;
    private Date endTime;
    private List<QuestionImpl> questions;
}
