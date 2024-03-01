<template>
  <!-- 容器 -->
  <el-config-provider :locale="locale">
    <RouterView />
  </el-config-provider>
</template>

<script setup lang="ts">
  import { RouterView } from 'vue-router'
  import { ref } from 'vue'
  import { ElConfigProvider } from 'element-plus'
  import zhCn from 'element-plus/es/locale/lang/zh-cn'
  let locale = ref(zhCn)
</script>

<style scoped>

</style>
