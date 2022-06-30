package com.exam.pojo.dto;

import com.exam.utils.excel.ExcelImport;
import lombok.Data;

/**
 * @author hongjinhui
 * 2022/6/19
 */
@Data
public class QuestionExcel {
    @ExcelImport("知识点")
    private String tag;
    @ExcelImport("类型")
    private Integer type;
    @ExcelImport("题干")
    private String content;
    @ExcelImport("选项A")
    private String A;
    @ExcelImport("选项B")
    private String B;
    @ExcelImport("选项C")
    private String C;
    @ExcelImport("选项D")
    private String D;
    @ExcelImport("答案")
    private String answer;
}
