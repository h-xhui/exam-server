package com.exam.dao;

import com.exam.pojo.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author:hzh
 * @create:2022/5/20
 **/
@Mapper
public interface TagMapper {
    void addTag(Tag tag);
    void delete(long id);
    void update(Tag tag);
    List<Tag> getTagList();
    Tag getTagById(int id);
    Tag getTagByName(String name);
    void save(Tag tag);
}
