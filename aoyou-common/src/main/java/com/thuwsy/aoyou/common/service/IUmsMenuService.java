package com.thuwsy.aoyou.common.service;

import com.thuwsy.aoyou.common.domain.dto.UmsMenuParamDto;
import com.thuwsy.aoyou.common.domain.entity.UmsMenu;
import com.thuwsy.aoyou.common.domain.vo.RouterVO;
import com.thuwsy.aoyou.common.response.CommonPageResult;

import java.util.List;

/**
 * ClassName: IUmsMenuService
 * Package: com.thuwsy.aoyou.common.service
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/2/20 18:12
 * @Version 1.0
 */
public interface IUmsMenuService {
    /**
     * 查询已登录用户自己的菜单
     */
    List<RouterVO> searchSelfMenu();

    CommonPageResult<UmsMenu> selectList(UmsMenuParamDto menuParamDto);

    int saveMenu(UmsMenu umsMenu);

    UmsMenu searchInfo(Long id);
    int updateMenu(UmsMenu umsMenu);
    int removeMenu(Long[] ids);
}
