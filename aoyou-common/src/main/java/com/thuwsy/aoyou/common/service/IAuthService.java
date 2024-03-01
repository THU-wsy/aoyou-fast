package com.thuwsy.aoyou.common.service;

import com.thuwsy.aoyou.common.domain.dto.LoginDto;

/**
 * ClassName: IAuthService
 * Package: com.thuwsy.aoyou.common.service
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/2/21 17:33
 * @Version 1.0
 */
public interface IAuthService {
    String login(LoginDto loginDto);
}
