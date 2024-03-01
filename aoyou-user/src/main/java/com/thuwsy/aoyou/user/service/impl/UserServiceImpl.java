package com.thuwsy.aoyou.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.thuwsy.aoyou.common.constants.HttpStatus;
import com.thuwsy.aoyou.common.domain.entity.UmsSysUser;
import com.thuwsy.aoyou.common.domain.vo.LoginUserVO;
import com.thuwsy.aoyou.common.exception.ServiceException;
import com.thuwsy.aoyou.common.utils.SecurityUtil;
import com.thuwsy.aoyou.user.domain.vo.UserInfoVO;
import com.thuwsy.aoyou.user.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * ClassName: UserServiceImpl
 * Package: com.thuwsy.aoyou.user.service.impl
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/2/26 12:55
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements IUserService {
    @Override
    public UserInfoVO searchUserInfo() {
        // 通过SecurityContext获取已登录的用户信息
        LoginUserVO loginUser = SecurityUtil.getLoginUser();
        UmsSysUser user = loginUser.getSysUser();
        if (ObjectUtil.isNull(user)) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED, "用户未登录");
        }

        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtil.copyProperties(user, userInfoVO);
        return userInfoVO;
    }
}
