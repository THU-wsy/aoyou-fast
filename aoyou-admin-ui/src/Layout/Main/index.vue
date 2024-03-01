<template>
    <el-tabs
        v-model="activeTab"
        type="card"
        closable
        class="demo-tabs"
        @tab-change="gotoTab"
        @tab-remove="closeTab"
    >
        <el-tab-pane
            v-for="item in tabList"
            :key="item.path"
            :label="item.title"
            :name="item.path"
        >
            <!-- 容器 -->
            <RouterView/>
        </el-tab-pane>
    </el-tabs>
</template>

<script setup lang="ts" name="Main">
    import { RouterView } from 'vue-router';
    // 从MenuStore中获取tab的数据
    import { useMenuStore } from '@/stores/menu';
    import { storeToRefs } from 'pinia';
    import { useRouter } from 'vue-router';
    const router = useRouter();
    const menuStore = useMenuStore();
    let { tabList, activeTab } = storeToRefs(menuStore);
    
    // 选中tab
    function gotoTab(path) {
        // 修改activeTab
        menuStore.setActive(path);
        // 路由页面
        router.push('/home/' + path);
    }
    // 移除tab
    function closeTab(path) {
        menuStore.delTabList(path);
        // 如果移除的是当前激活的tab，则需要激活另一个tab
        if (path == menuStore.activeTab) {
            if (tabList.value == null || tabList.value.length == 0) {
                menuStore.setActive(null);
                router.push('/home');
            } else {
                let newPath = tabList.value[tabList.value.length - 1].path;
                menuStore.setActive(newPath);
                router.push('/home/' + newPath);
            }
        }
    }
</script>

<style lang="scss" scoped>

</style>