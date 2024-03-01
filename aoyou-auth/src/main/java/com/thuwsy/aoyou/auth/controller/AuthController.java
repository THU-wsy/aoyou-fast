package com.thuwsy.aoyou.auth.controller;

import com.thuwsy.aoyou.common.domain.dto.LoginDto;
import com.thuwsy.aoyou.common.response.CommonResult;
import com.thuwsy.aoyou.common.service.IAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: AuthController
 * Package: com.thuwsy.aoyou.auth.controller
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/2/20 23:10
 * @Version 1.0
 */
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    @Autowired
    private IAuthService authService;

    /**
     * 系统用户登录
     */
    @PostMapping("/sys")
    public CommonResult sysLogin(@RequestBody LoginDto loginDto) {
        String jwt = authService.login(loginDto);
        return CommonResult.success().put("token", jwt);
    }
}
