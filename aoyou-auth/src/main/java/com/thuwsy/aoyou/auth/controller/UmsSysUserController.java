package com.thuwsy.aoyou.auth.controller;

import com.thuwsy.aoyou.common.domain.entity.UmsSysUser;
import com.thuwsy.aoyou.common.response.CommonResult;
import com.thuwsy.aoyou.common.service.IUmsSysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: UmsSysUserController
 * Package: com.thuwsy.aoyou.auth.controller
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/2/20 18:38
 * @Version 1.0
 */
@RestController
@RequestMapping("/ums/user")
public class UmsSysUserController {
    @Autowired
    private IUmsSysUserService sysUserService;

    /**
     * 新增用户接口
     */
    @PostMapping
    public CommonResult addSysUser(@RequestBody UmsSysUser user) {
        int result = sysUserService.save(user);
        return result > 0 ? CommonResult.success() : CommonResult.error();
    }

    /**
     * 查询用户列表接口
     */
    @GetMapping
    public CommonResult searchList() {
        List<UmsSysUser> list = sysUserService.list();
        return CommonResult.success(list);
    }
}
