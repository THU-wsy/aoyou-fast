package com.thuwsy.aoyou.common.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.thuwsy.aoyou.common.constants.HttpStatus;
import com.thuwsy.aoyou.common.domain.dto.LoginDto;
import com.thuwsy.aoyou.common.domain.vo.LoginUserVO;
import com.thuwsy.aoyou.common.exception.ServiceException;
import com.thuwsy.aoyou.common.service.IAuthService;
import com.thuwsy.aoyou.common.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * ClassName: AuthServiceImpl
 * Package: com.thuwsy.aoyou.common.service.impl
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/2/21 17:35
 * @Version 1.0
 */
@Service
public class AuthServiceImpl implements IAuthService {
    // SpringSecurity中使用AuthenticationManager验证账号密码
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String login(LoginDto loginDto) {
        // 1. 传入要验证的账号和密码
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginDto.getAccount(),
                        loginDto.getPassword()
                );

        // 2. 调用AuthenticationManager的authenticate()方法验证用户名和密码
        // 其底层会调用UserDetailsService的loadUserByUsername()方法获取用户
        LoginUserVO user = null;
        try {
            Authentication authentication =
                    authenticationManager.authenticate(authenticationToken);
            // 3. 获取用户信息
            user = (LoginUserVO) authentication.getPrincipal();
            if (ObjectUtil.isNull(user))
                throw new Exception();
        } catch (Exception e) {
            throw new ServiceException(HttpStatus.ERROR, "用户名或密码错误");
        }

        // 4. 创建jwt
        return jwtUtil.createJwt(user);
    }
}
