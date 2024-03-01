// import router from '@/router'
// import { useMenuStore } from '@/stores/menu'
// import Layout from '@/Layout/index.vue'

// // 设置路由白名单
// const whiteRouter = ['/', '/login', '/error', '/404']
// // 标志，用于判断否已经添加动态路由，避免重复添加
// let isAddRoutes = false

// // 全局前置路由守卫
// router.beforeEach(async (to, from) => {
//     // 如果to在路由白名单中，或者已经添加过动态路由，则直接放行
//     if (whiteRouter.indexOf(to.path) != -1 || isAddRoutes)
//         return true;
        
//     // 如果routerList中还没有动态路由的数据，则进行设置
//     const menuStore = useMenuStore();
//     if (menuStore.routerList.length == 0)
//         await menuStore.setRouterList();

//     // 添加动态路由
//     // 所有的页面都是加载到Layout/Main组件的RouterView中
//     // 相当于所有的路由都是Layout的子路由
//     const routerList = menuStore.routerList;
//     router.addRoute(
//         {
//             component: Layout,
//             path: "/home",
//             children: routerList
//         }
//     )

//     // 动态路由添加完毕，设置标志为true
//     isAddRoutes = true;
//     // 返回to.fullPath，当添加完所有动态路由后，触发重定向
//     return to.fullPath;
// })