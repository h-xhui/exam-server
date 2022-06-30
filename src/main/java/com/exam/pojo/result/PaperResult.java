package com.exam.pojo.result;

import com.exam.pojo.result.helper.UserQueImp;
import lombok.Data;

import java.util.List;

@Data
public class PaperResult {
    private Long id;
    private String title;
    private String author;
    private List<UserQueImp> userQueImpList;
}
