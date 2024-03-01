import request from "@/utils/request";

// 登录接口
export function login(data: any) {
    return request({
        url: 'auth/sys',
        method: 'POST',
        data: data
    }) 
}

// 退出登录接口
export function logout() {
    return request({
        url: 'auth/logout',
        method: 'POST'
    })
}