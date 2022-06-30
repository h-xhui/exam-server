package com.exam.service.impl;

import com.exam.dao.TagMapper;
import com.exam.pojo.entity.Tag;
import com.exam.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:hzh
 * @create:2022/5/20
 **/
@Service
public class TagServiceImp implements TagService {
    @Autowired
    TagMapper tagMapper;

    @Override
    public void addTag(Tag tag) {
        tagMapper.addTag(tag);
    }

    @Override
    public void deleteTagById(long id) {
        tagMapper.delete(id);
    }

    @Override
    public void updateTag(Tag tag) {
        tagMapper.update(tag);
    }

    @Override
    public List<Tag> getTagList() {
        return tagMapper.getTagList();
    }

    @Override
    public Tag getTagById(int id) {
        return tagMapper.getTagById(id);
    }
}
