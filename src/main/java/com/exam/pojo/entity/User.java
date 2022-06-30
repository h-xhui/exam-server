package com.exam.pojo.entity;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hongjinhui
 * 2022/5/20
 */
@Data
public class User {
    private Long id;
    @NotBlank(message = "学号不允许为空")
    private String phone;
    @NotBlank(message = "密码不允许为空")
    private String password;
    @NotBlank(message = "姓名不允许为空")
    private String name;
    @NotBlank(message = "班级不允许为空")
    private String clazz;
    @NotBlank(message = "性别不允许为空")
    private String sex;
    @NotNull(message = "身份不允许为空")
    private Integer type;
    @Email(message = "邮箱格式错误")
    @NotBlank(message = "邮箱不允许为空")
    private String email;
}
