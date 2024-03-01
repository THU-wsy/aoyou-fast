package com.thuwsy.aoyou.common.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.thuwsy.aoyou.common.constants.HttpStatus;
import com.thuwsy.aoyou.common.domain.dto.UmsMenuParamDto;
import com.thuwsy.aoyou.common.domain.entity.UmsMenu;
import com.thuwsy.aoyou.common.domain.vo.RouterVO;
import com.thuwsy.aoyou.common.exception.ServiceException;
import com.thuwsy.aoyou.common.mapper.UmsMenuMapper;
import com.thuwsy.aoyou.common.mapper.UmsRoleMapper;
import com.thuwsy.aoyou.common.response.CommonPageResult;
import com.thuwsy.aoyou.common.service.IUmsMenuService;
import com.thuwsy.aoyou.common.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: UmsMenuServiceImpl
 * Package: com.thuwsy.aoyou.common.service.impl
 * Description:
 *
 * @Author THU_wsy
 * @Create 2024/2/20 18:14
 * @Version 1.0
 */
@Service
public class UmsMenuServiceImpl implements IUmsMenuService {
    @Autowired
    private UmsRoleMapper roleMapper;
    @Autowired
    private UmsMenuMapper menuMapper;

    /**
     * 获取个人菜单列表
     */
    @Override
    public List<RouterVO> searchSelfMenu() {
        // 1. 通过Security上下文获取登录的用户id
        Long userId = SecurityUtil.getUserId();
        // 2. 查询用户的角色信息
        List<Long> roleIds = roleMapper.selectByUserId(userId);
        // 3. 查询用户的菜单
        List<UmsMenu> menuList = menuMapper.selectByRoleIds(roleIds);
        // 4. 递归设置菜单的树形结构
        return getRouter(menuList);
    }


    /**
     * 获取路由
     */
    private List<RouterVO> getRouter(List<UmsMenu> menuList) {
        List<RouterVO> list = new ArrayList<>();
        // 1. 首先获取所有1级路由【parentId为0】
        List<UmsMenu> parentMenu = menuList.stream()
                .filter(item -> item.getParentId().equals(0L))
                .sorted((item1, item2) -> {
                    return item1.getSort() - item2.getSort();
                })
                .collect(Collectors.toList());
        // 2. 转换类型
        for (UmsMenu menu : parentMenu) {
            RouterVO routerVO = new RouterVO();
            BeanUtil.copyProperties(menu, routerVO);
            list.add(routerVO);
        }
        // 3. 遍历1级路由，获取其所有子节点【子节点的parentId为当前1级路由的id】
        for (RouterVO router : list) {
            List<RouterVO> children = buildTree(menuList, router.getId());
            router.setChildren(children);
        }
        return list;
    }

    /**
     * 递归获取当前菜单的所有子菜单
     */
    private List<RouterVO> buildTree(List<UmsMenu> menuList, Long parentId) {
        List<RouterVO> children = new ArrayList<>();
        // 遍历所有数据，如果当前菜单的parentId与传入参数相同，则是子菜单
        List<UmsMenu> menus = menuList.stream()
                .filter(item -> item.getParentId().equals(parentId))
                .sorted((item1, item2) -> {
                    return item1.getSort() - item2.getSort();
                })
                .collect(Collectors.toList());
        for (UmsMenu menu : menus) {
            RouterVO routerVO = new RouterVO();
            BeanUtil.copyProperties(menu, routerVO);
            children.add(routerVO);
        }

        // children中的菜单，还需要递归设置其子菜单
        for (RouterVO item : children) {
            item.setChildren(buildTree(menuList, item.getId()));
        }
        return children;
    }


    @Override
    public CommonPageResult<UmsMenu> selectList(UmsMenuParamDto menuParamDto) {
        LambdaQueryWrapper<UmsMenu> wrapper = new LambdaQueryWrapper<>();
        // 如果有搜索条件
        if (StrUtil.isNotBlank(menuParamDto.getMenuName()))
            wrapper.like(UmsMenu::getMenuName, menuParamDto.getMenuName());
        if (StrUtil.isNotBlank(menuParamDto.getPerms()))
            wrapper.like(UmsMenu::getPerms, menuParamDto.getPerms());
        // 注意，是对一级目录进行分页查询（子目录会折叠在一级目录下，无需分页）
        wrapper.eq(UmsMenu::getParentId, 0);

        // 进行分页查询
        Page<UmsMenu> page = new Page<>(menuParamDto.getPageNum(), menuParamDto.getPageSize());
        menuMapper.selectPage(page, wrapper);
        // 封装分页结果
        CommonPageResult<UmsMenu> parentMenuList = new CommonPageResult<>();
        parentMenuList.setList(page.getRecords());
        Collections.sort(parentMenuList.getList(), (item1, item2) -> {
            return item1.getSort() - item2.getSort();
        });
        parentMenuList.setTotal(page.getTotal());

        // 查询这些一级目录的所有子目录（由于我们一共只有两级目录，就不用递归了）
        List<Long> parentIds = parentMenuList.getList().stream()
                .map(UmsMenu::getId).toList();
        List<UmsMenu> childrenList = menuMapper.selectList(new LambdaQueryWrapper<UmsMenu>()
                .in(UmsMenu::getParentId, parentIds));

        // 给每个一级目录设置子目录
        for (int i = 0; i < parentMenuList.getList().size(); i++) {
            UmsMenu parent = parentMenuList.getList().get(i);
            List<UmsMenu> children = childrenList.stream()
                    .filter(item -> item.getParentId().equals(parent.getId()))
                    .sorted((item1, item2) -> {
                        return item1.getSort() - item2.getSort();
                    })
                    .toList();
            parent.setChildren(children);
        }
        return parentMenuList;
    }

    @Transactional
    @Override
    public int saveMenu(UmsMenu umsMenu) {
        // 获取登录的用户名
        String creator = SecurityUtil.getUsername();
        // 设置该菜单的创建者和修改者
        umsMenu.setCreator(creator);
        umsMenu.setUpdater(creator);
        // 判断是否有重复菜单
        // 1. 同一级目录下，不能有同名的菜单
        // 2. 菜单的路由地址必须唯一
        Long parentId = umsMenu.getParentId();
        String menuName = umsMenu.getMenuName();
        String path = umsMenu.getPath();
        LambdaQueryWrapper<UmsMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UmsMenu::getParentId, parentId)
                .eq(UmsMenu::getMenuName, menuName)
                .or()
                .eq(UmsMenu::getPath, path);
        Long count = menuMapper.selectCount(wrapper);
        if (count > 0) {
            throw new ServiceException(HttpStatus.ERROR, "该菜单已存在");
        }
        return menuMapper.insert(umsMenu);
    }

    @Override
    public UmsMenu searchInfo(Long id) {
        return menuMapper.selectById(id);
    }

    @Transactional
    @Override
    public int updateMenu(UmsMenu umsMenu) {
        // 获取登录的用户名
        String updater = SecurityUtil.getUsername();
        // 设置该菜单的修改者
        umsMenu.setUpdater(updater);
        // 判断是否有重复菜单（除了当前修改的菜单自己）
        // 1. 同一级目录下，不能有同名的菜单
        // 2. 菜单的路由地址必须唯一
        Long parentId = umsMenu.getParentId();
        String menuName = umsMenu.getMenuName();
        String path = umsMenu.getPath();
        LambdaQueryWrapper<UmsMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.ne(UmsMenu::getId, umsMenu.getId())
                .and(w -> w.eq(UmsMenu::getParentId, parentId)
                        .eq(UmsMenu::getMenuName, menuName)
                        .or()
                        .eq(UmsMenu::getPath, path));
        Long count = menuMapper.selectCount(wrapper);
        if (count > 0) {
            throw new ServiceException(HttpStatus.ERROR, "该菜单已存在");
        }
        return menuMapper.updateById(umsMenu);
    }

    @Transactional
    @Override
    public int removeMenu(Long[] ids) {
        return menuMapper.deleteBatchIds(Arrays.asList(ids));
    }
}
