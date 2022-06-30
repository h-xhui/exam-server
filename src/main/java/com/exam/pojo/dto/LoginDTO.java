package com.exam.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDTO {
    @Email(message = "邮箱格式错误")
    private String email;
    @NotBlank
    private String code;
    @NotBlank
    private String password;
    @NotNull
    private Integer type;
}
