package com.thuwsy.aoyou.user.controller;

import com.thuwsy.aoyou.common.response.CommonResult;
import com.thuwsy.aoyou.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: UserController
 * Package: com.thuwsy.aoyou.user.controller
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/2/26 12:51
 * @Version 1.0
 */
@RestController
@RequestMapping("/sys/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/info")
    public CommonResult searchUserInfo() {
        return CommonResult.success(userService.searchUserInfo());
    }
}
