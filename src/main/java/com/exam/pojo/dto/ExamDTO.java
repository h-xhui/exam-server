package com.exam.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@AllArgsConstructor
@Data
public class ExamDTO {
    private Integer score;
    private ArrayList<String> request;
}
