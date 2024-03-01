package com.thuwsy.aoyou.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thuwsy.aoyou.common.domain.entity.UmsRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ClassName: UmsRoleMapper
 * Package: com.thuwsy.aoyou.common.mapper
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/2/20 18:08
 * @Version 1.0
 */
@Mapper
public interface UmsRoleMapper extends BaseMapper<UmsRole> {
    List<Long> selectByUserId(@Param("userId") Long userId);
}
