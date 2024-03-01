package com.thuwsy.aoyou.common.service.impl;

import com.thuwsy.aoyou.common.domain.entity.UmsSysUser;
import com.thuwsy.aoyou.common.mapper.UmsSysUserMapper;
import com.thuwsy.aoyou.common.service.IUmsSysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: UmsSysUserServiceImpl
 * Package: com.thuwsy.aoyou.common.service.impl
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/2/20 18:13
 * @Version 1.0
 */
@Service
public class UmsSysUserServiceImpl implements IUmsSysUserService {
    @Autowired
    private UmsSysUserMapper umsSysUserMapper;

    @Override
    public int save(UmsSysUser user) {
        return umsSysUserMapper.insert(user);
    }

    @Override
    public List<UmsSysUser> list() {
        return umsSysUserMapper.selectList(null);
    }
}
