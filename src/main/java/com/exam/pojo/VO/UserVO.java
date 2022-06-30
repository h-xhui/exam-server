package com.exam.pojo.VO;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hongjinhui
 * 2022/5/20
 */
@Data
public class UserVO {


    private String phone;

    private String password;

    private String name;

    private String clazz;

    private String sex;

    private Integer type;
    @NotBlank(message = "邮箱不允许为空")
    @Email(message = "邮箱格式错误")
    private String email;
}
