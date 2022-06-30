package com.exam.pojo.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author:hzh
 * @create:2022/5/20
 **/
@Data
public class Tag {
    private String userName;
    private long id;
    private String name;
    private Date updateTime;
}
