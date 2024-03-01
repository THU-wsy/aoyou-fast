package com.thuwsy.aoyou.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thuwsy.aoyou.common.domain.entity.UmsMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ClassName: UmsMenuMapper
 * Package: com.thuwsy.aoyou.common.mapper
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/2/20 18:08
 * @Version 1.0
 */
@Mapper
public interface UmsMenuMapper extends BaseMapper<UmsMenu> {
    // 根据角色id，查询其拥有的菜单权限
    List<UmsMenu> selectByRoleIds(@Param("roleIds") List<Long> roleIds);
}
