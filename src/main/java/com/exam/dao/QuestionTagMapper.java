package com.exam.dao;

import com.exam.pojo.entity.QuestionTag;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author:hzh
 * @create:2022/6/5
 **/
@Mapper
public interface QuestionTagMapper {
    Boolean addQuestionTag(QuestionTag questionTag);
}
