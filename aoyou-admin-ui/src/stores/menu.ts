import {defineStore} from 'pinia';
import {searchSelfRouter} from '@/api/user';

export const useMenuStore = defineStore('menu', {
    // 定义数据
    state: () => ({
        menuList: [],
        routerList: [], // 动态路由数据，也就是左侧菜单的路由信息
        tabList: [], // 所有的tab
        activeTab: null // 当前选中的tab
    }),
    // 获取数据的方法
    getters: {

    }, 
    // 操作数据的方法
    actions: {
        setMenuList(data) {
            console.log("setMenuList=======>", data);
            this.menuList = data;
        },

        // 根据menuList渲染动态路由，存储到routerList中，这样动态路由就与pinia有相同的生命周期
        // 注意，routerList不要持久化，因为其中的component无法保存为string的形式，而我们会对menuList进行持久化
        // 如果手动刷新页面则会清除pinia中的数据，此时需要根据menuList重新渲染动态路由
        setRouterList() {
            this.menuList.forEach(item => {
                let routerInfo = {
                    name: item.menuName,
                    path: item.path,
                    meta: {title: item.menuName},
                    // 设置组件
                    component: () => import(/*@vite-ignore */`../views/${item.path}.vue`)
                }
                // 将路由信息添加到routerList中
                this.routerList.push(routerInfo);
                
                // 设置子菜单
                let childrenList = item.children;
                childrenList.forEach(children => {
                    let routerInfo = {
                        name: children.menuName,
                        path: children.path,
                        meta: {title: children.menuName},
                        component: () => import(/*@vite-ignore */`../views/${children.path}.vue`)
                    }
                    this.routerList.push(routerInfo)
                })
            })
        },
        
        // 设置tabList
        setTabList(data) {
            this.tabList.push(data);
        },
        // 删除tabList（根据path）
        delTabList(path) {
            this.tabList = this.tabList.filter(item => {
                return item.path != path;
            })
        },
        // 设置activeTab
        setActive(path) {
            this.activeTab = path;
        }
    },
    // 使用持久化
    persist: {
    	enabled: true,
        storage: localStorage,
        key: 'useMenu',
        paths: ['menuList', 'tabList', 'activeTab']
    }
})