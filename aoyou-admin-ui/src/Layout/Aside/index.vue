<template>
    <div>
        <el-menu
            background-color="#304157"
            text-color="#b1becd"
            active-text-color="#336cab"
        >
            <!-- 遍历子元素 -->
            <template v-for="menu in menuList" :key="menu.path">
                <!-- 判断是否有子菜单 -->
                <el-sub-menu v-if="hasChildren(menu)" :index="menu.path">
                    <template #title>
                        <!-- 添加icon -->
                        <svg-icon
                            v-if="menu.icon"
                            slot="prefix"
                            :name="menu.icon" 
                            width="18px" 
                            height="18px"
                        />
                        <span class="spanStyle">{{ menu.menuName }}</span>
                    </template>
                    <!-- 渲染子菜单 -->
                    <el-menu-item-group>
                        <el-menu-item 
                            v-for="children in menu.children"
                            :index="children.path"
                            @click="handleRouter(children)"
                            >
                            <svg-icon
                                v-if="children.icon"
                                slot="prefix"
                                :name="children.icon" 
                                width="18px" 
                                height="18px"
                            />
                            <span class="spanStyle">{{ children.menuName }}</span>
                        </el-menu-item>
                    </el-menu-item-group>
                </el-sub-menu>

                <!-- 没有子菜单 -->
                <el-menu-item v-else :index="menu.path" @click="handleRouter(menu)">
                    <!-- <Edit style="width: 1em; height: 1em; margin-right: 8px" /> -->
                    <!-- <el-icon><Setting /></el-icon> -->
                    <svg-icon
                        v-if="menu.icon"
                        slot="prefix"
                        :name="menu.icon" 
                        width="18px" 
                        height="18px"
                    />
                    <span class="spanStyle">{{ menu.menuName }}</span>
                </el-menu-item>
            </template>
        </el-menu>
    </div>
</template>

<script setup lang="ts" name="Aside">
    import { useMenuStore } from '@/stores/menu';
    import { storeToRefs } from 'pinia';
    import { useRouter } from 'vue-router';
    const router = useRouter()
    const menuStore = useMenuStore();
    const { menuList } = storeToRefs(menuStore);
    // 判断是否有子菜单
    function hasChildren(menu) {
        if (menu.children && menu.children.length > 0) {
            return true;
        }
        return false;
    }

    // 切换路由
    function handleRouter(menu) {
        // 向tabList中添加数据，已经添加过的tab就不需要再添加了
        // tab的结构：{title: '首页', path: 'index'}
        let hasNode = menuStore.tabList.filter(item => item.path == menu.path);
        if (hasNode == null || hasNode.length == 0) {
            let data = {title: menu.menuName, path: menu.path};
            menuStore.setTabList(data);
        }
        // 修改activeTab的值
        menuStore.setActive(menu.path);
        router.push('/home/' + menu.path);
    }
</script>

<style lang="scss" scoped>
.spanStyle {
    margin-left: 10px;
}
</style>