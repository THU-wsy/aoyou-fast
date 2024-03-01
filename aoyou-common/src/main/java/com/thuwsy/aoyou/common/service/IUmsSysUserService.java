package com.thuwsy.aoyou.common.service;


import com.thuwsy.aoyou.common.domain.entity.UmsSysUser;

import java.util.List;

/**
 * ClassName: IUmsSysUserService
 * Package: com.thuwsy.aoyou.common.service
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/2/20 18:11
 * @Version 1.0
 */
public interface IUmsSysUserService {
    /**
     * 新增系统用户
     */
    int save(UmsSysUser user);

    /**
     * 查询所有系统用户
     */
    List<UmsSysUser> list();
}
