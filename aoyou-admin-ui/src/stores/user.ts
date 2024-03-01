import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
    state: () => ({ 
        id: undefined,
        nickname: undefined,
        avatar: undefined,
    }),
    getters: {

    },
    actions: {
        setUserInfo(data) {
            this.id = data.id;
            this.nickname = data.nickname;
            this.avatar = data.avatar;
        }
    },
    // 使用持久化
    persist: {
    	enabled: true,
        storage: localStorage,
        key: 'userInfo',
        paths: ['id', 'nickname', 'avatar']
    }
})