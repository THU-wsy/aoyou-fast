package com.thuwsy.aoyou.common.domain.vo;

import cn.hutool.core.util.ObjectUtil;
import com.thuwsy.aoyou.common.domain.entity.UmsSysUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: LoginUserVO
 * Package: com.thuwsy.aoyou.common.domain.vo
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/2/21 13:06
 * @Version 1.0
 */
@Data
public class LoginUserVO implements UserDetails {
    private Long id;
    private String token;
    private long loginTime;
    // 用户信息
    private UmsSysUser sysUser = new UmsSysUser();

    @Override // 用户的权限
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> perms = sysUser.getPerms();
        if (ObjectUtil.isNotEmpty(perms)) {
            // 通过stream将List<String>转为Collection<GrantedAuthority>
            return perms.stream().map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return sysUser.getStatus() == 0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return sysUser.getStatus() == 0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return sysUser.getStatus() == 0;
    }

    @Override
    public boolean isEnabled() {
        return sysUser.getStatus() == 0;
    }
}
