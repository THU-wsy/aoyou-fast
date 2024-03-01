package com.thuwsy.aoyou.common.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: LoginDto
 * Package: com.thuwsy.aoyou.common.domain.dto
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/2/20 23:08
 * @Version 1.0
 */
@Data
public class LoginDto implements Serializable {
    private String account;
    private String password;
    private Boolean rememberMe;
}
