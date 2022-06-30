package com.exam.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaperQuestionMapper {
    void insert(Long questionID, Long paperId, Integer score);
}
