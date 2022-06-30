package com.exam.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.exam.common.ResultCode;
import com.exam.pojo.entity.Tag;
import com.exam.pojo.result.ApiResult;
import com.exam.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author:hzh
 * @create:2022/6/5
 **/
@Controller
@RequestMapping("tag")
public class TagController {
    @Autowired
    TagService tagService;

    @PostMapping("/tag_add")
    @ResponseBody
    public ApiResult<?> tagAdd(Tag tag){
        if(StrUtil.isEmpty(tag.getName())){
            return ApiResult.fail(ResultCode.MISSING_PARAM,"请输入知识点",null);
        }
        if(StrUtil.isEmpty(tag.getUserName())){
            return ApiResult.fail(ResultCode.MISSING_PARAM,"请登录后添加",null);
        }
        tag.setUpdateTime(new DateTime());
        tagService.addTag(tag);
        return ApiResult.success();
    }

    @PostMapping("/tag_delete")
    @ResponseBody
    public ApiResult<?> tagDelete(long tagId){
        tagService.deleteTagById(tagId);
        return ApiResult.success();
    }

    @PostMapping("/tag_update")
    @ResponseBody
    public ApiResult<?> tagUpdate(Tag tag){
        tag.setUpdateTime(new DateTime());
        tagService.updateTag(tag);
        return ApiResult.success();
    }
    @GetMapping("/tag_getList")
    @ResponseBody
    public ApiResult<?> tagGetList(){
        List<Tag> tagList = tagService.getTagList();
        return ApiResult.success(tagList);
    }
}
