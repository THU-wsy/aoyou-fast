package com.thuwsy.aoyou.user.controller;

import com.thuwsy.aoyou.common.domain.dto.UmsMenuParamDto;
import com.thuwsy.aoyou.common.domain.entity.UmsMenu;
import com.thuwsy.aoyou.common.domain.vo.RouterVO;
import com.thuwsy.aoyou.common.response.CommonPageResult;
import com.thuwsy.aoyou.common.response.CommonResult;
import com.thuwsy.aoyou.common.service.IUmsMenuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: MenuController
 * Package: com.thuwsy.aoyou.user.controller
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/2/22 14:32
 * @Version 1.0
 */
@RestController
@RequestMapping("/sys/menu")
public class MenuController {
    @Autowired
    private IUmsMenuService menuService;

    /**
     * 查询自己的菜单列表
     */
    @GetMapping("/self")
    public CommonResult searchSelfMenu() {
        List<RouterVO> list = menuService.searchSelfMenu();
        return CommonResult.success(list);
    }

    /**
     * 查询所有菜单
     */
    @GetMapping("/list")
    public CommonResult list(UmsMenuParamDto menuParamDto) {
        CommonPageResult<UmsMenu> pageResult = menuService.selectList(menuParamDto);
        return CommonResult.success(pageResult);
    }

    /**
     * 新增菜单
     */
    @PostMapping
    public CommonResult saveMenu(@Valid @RequestBody UmsMenu umsMenu) {
        int row = menuService.saveMenu(umsMenu);
        if (row > 0)
            return CommonResult.success();
        return CommonResult.error("新增菜单失败");
    }

    /**
     * 根据id查询菜单详情
     */
    @GetMapping("/{id}")
    public CommonResult searchInfo(@PathVariable("id") Long id) {
        UmsMenu menu = menuService.searchInfo(id);
        return CommonResult.success(menu);
    }

    /**
     * 修改菜单
     */
    @PutMapping
    public CommonResult updateMenu(@Valid @RequestBody UmsMenu umsMenu) {
        int row = menuService.updateMenu(umsMenu);
        if (row > 0) {
            return CommonResult.success();
        }
        return CommonResult.error("修改菜单失败");
    }

    /**
     * 删除菜单
     */
    @DeleteMapping
    public CommonResult removeMenu(@RequestBody Long[] ids) {
        int row = menuService.removeMenu(ids);
        if (row > 0) {
            return CommonResult.success();
        }
        return CommonResult.error("删除菜单失败");
    }
}
