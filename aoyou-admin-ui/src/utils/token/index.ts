// 存储token
export function setToken(tokenKey: string, token: string) {
    return localStorage.setItem(tokenKey, token);
}

// 获取token
export function getToken(tokenKey: string) {
    return localStorage.getItem(tokenKey);
}
// 移除token
export function removeToken(tokenKey: string) {
    if (getToken(tokenKey)) {
        return localStorage.removeItem(tokenKey);
    }
    return null;
}