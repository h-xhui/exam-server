package com.exam;

import com.exam.dao.PaperQuestionMapper;
import com.exam.dao.PapersMapper;
import com.exam.dao.UserMapper;
import com.exam.service.QuestionService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExamApplicationTests {
    @Autowired
    QuestionService questionService;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    PaperQuestionMapper paperQuestionMapper;

    @Test
    public void test() {
        userMapper.getListById("13197096592");
    }

    @Autowired
    PapersMapper papersMapper;


}
