package com.thuwsy.aoyou.support.config.security;

import com.thuwsy.aoyou.common.constants.HttpStatus;
import com.thuwsy.aoyou.common.domain.entity.UmsMenu;
import com.thuwsy.aoyou.common.domain.entity.UmsRole;
import com.thuwsy.aoyou.common.domain.entity.UmsSysUser;
import com.thuwsy.aoyou.common.domain.vo.LoginUserVO;
import com.thuwsy.aoyou.common.exception.ServiceException;
import com.thuwsy.aoyou.common.mapper.UmsMenuMapper;
import com.thuwsy.aoyou.common.mapper.UmsSysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: SysUserDetailsService
 * Package: com.thuwsy.aoyou.support.config.security
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/2/21 16:32
 * @Version 1.0
 */
@Service
@Slf4j
public class SysUserDetailsService implements UserDetailsService {
    @Autowired
    private UmsSysUserMapper sysUserMapper;
    @Autowired
    private UmsMenuMapper umsMenuMapper;

    /**
     * 根据account查询用户
     * @param account 用户名/手机号/邮箱
     */
    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        // TODO 验证账号类型，这里为了我们简便，直接使用用户名类型
        int accountType = 0;

        // 1. 根据账号查询用户（同时查询出角色信息）
        UmsSysUser sysUser = sysUserMapper.selectUserByUserName(account, accountType);
        log.info("sysUser======>{}", sysUser);
        if (sysUser == null)
            throw new UsernameNotFoundException("用户名或密码错误");

        // 2. 取出用户的角色id
        List<Long> roleIds = sysUser.getRoleList().stream()
                .map(UmsRole::getRoleId)
                .collect(Collectors.toList());

        // 3. 根据角色id，查询用户能操作的菜单
        List<UmsMenu> menuList = umsMenuMapper.selectByRoleIds(roleIds);

        // 4. 获取菜单中的权限字段
        List<String> perms = menuList.stream()
                .map(UmsMenu::getPerms)
                .collect(Collectors.toList());
        log.info("权限=======>{}", perms);

        // 5. 设置用户的权限
        sysUser.setPerms(perms);

        // 6. 封装成UserDetails并返回
        LoginUserVO loginUserVO = new LoginUserVO();
        loginUserVO.setSysUser(sysUser);
        loginUserVO.setId(sysUser.getId());
        return loginUserVO;
    }
}
