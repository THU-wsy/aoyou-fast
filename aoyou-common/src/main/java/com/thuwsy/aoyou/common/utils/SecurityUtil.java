package com.thuwsy.aoyou.common.utils;

import cn.hutool.core.util.ObjectUtil;
import com.thuwsy.aoyou.common.constants.HttpStatus;
import com.thuwsy.aoyou.common.domain.vo.LoginUserVO;
import com.thuwsy.aoyou.common.exception.ServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * ClassName: SecurityUtil
 * Package: com.thuwsy.aoyou.common.utils
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/2/22 13:20
 * @Version 1.0
 */
public class SecurityUtil {
    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取用户
     */
    public static LoginUserVO getLoginUser() {
        return (LoginUserVO) getAuthentication().getPrincipal();
    }

    /**
     * 获取用户id
     */
    public static Long getUserId() {
        Long userId = getLoginUser().getId();
        if (ObjectUtil.isNull(userId)) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED, "用户未登录");
        }
        return userId;
    }

    /**
     * 获取用户名
     */
    public static String getUsername() {
        String username = getLoginUser().getUsername();
        if (ObjectUtil.isNull(username)) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED, "用户未登录");
        }
        return username;
    }
}
