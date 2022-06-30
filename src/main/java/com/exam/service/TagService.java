package com.exam.service;

import com.exam.pojo.entity.Tag;

import java.util.List;

/**
 * @author:hzh
 * @create:2022/5/20
 **/
public interface TagService {
    void addTag(Tag tag);
    void deleteTagById(long id);
    void updateTag(Tag tag);
    List<Tag> getTagList();
    Tag getTagById(int id);
}
