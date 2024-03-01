import request from '@/utils/request';

// 查询所有菜单
export function searchMenuList(data) {
    return request({
        url: 'sys/menu/list',
        method: "GET",
        params: data
    })
}

// 新增菜单
export function saveMenu(data) {
    return request({
        url: 'sys/menu',
        method: "POST",
        data: data
    })
}

// 修改菜单
export function updateMenu(data) {
    return request({
        url: 'sys/menu',
        method: "PUT",
        data: data
    })
}

// 查询菜单详情
export function searchMenuById(id) {
    return request({
        url: `sys/menu/${id}`,
        method: "GET"
    })
}

// 删除数据，传数组
export function removeMenu(ids) {
    return request({
        url: 'sys/menu',
        data: ids,
        method: "DELETE"
    })
}