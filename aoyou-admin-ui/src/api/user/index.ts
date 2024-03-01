import request from '@/utils/request';

// 查询个人的菜单列表
export function searchSelfRouter() {
    return request({
        url: 'sys/menu/self',
        method: "GET"
    })
}

// 查询个人信息
export function searchSelfInfo() {
    return request({
        url: 'sys/user/info',
        method: "GET"
    })
}