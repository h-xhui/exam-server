package com.exam.dao;

import com.exam.pojo.entity.Choice;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author:hzh
 * @create:2022/6/5
 **/
@Mapper
public interface ChoiceMapper {
    void addChoice(Choice choice);
}
