package com.exam.dao;

import com.exam.pojo.entity.Paper;
import com.exam.pojo.result.PapersList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author:hzh
 * @create:2022/6/6
 **/
@Mapper
public interface PapersMapper {
    List<PapersList> getAllList();

    List<Long> getIdByTagAndType(Long type, String tag);


    Paper getByExamId(Long examId);

    Long save(Paper paper);

    List<Long> getIdByTypeAndTagId(Integer type, Long tagId);

    void insert(Long questionID, Long paperId, Integer score);
}
