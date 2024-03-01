package com.thuwsy.aoyou.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thuwsy.aoyou.common.domain.entity.UmsSysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * ClassName: UmsSysUserMapper
 * Package: com.thuwsy.aoyou.common.mapper
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/2/20 18:07
 * @Version 1.0
 */
@Mapper
public interface UmsSysUserMapper extends BaseMapper<UmsSysUser> {
    // 根据用户名或手机或邮箱，查询用户及其角色信息
    UmsSysUser selectUserByUserName(
            @Param("account") String account,
            @Param("accountType") int accountType
    );
}
