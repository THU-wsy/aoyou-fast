package com.thuwsy.aoyou.user.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: UserInfoVO
 * Package: com.thuwsy.aoyou.user.domain.vo
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/2/26 12:48
 * @Version 1.0
 */
@Data
public class UserInfoVO implements Serializable {
    private Long id;
    private String nickname;
    private String avatar;
}
