<template>
    <!-- 根div -->
    <div class="login_container">
        <!-- 登录表单 -->
        <div class="login_form">
            <h3 class="title">遨游快速开发平台</h3>
            <el-form ref="formRef" :model="loginForm"> 
                <!-- 用户名 -->
                <el-form-item>
                    <el-input v-model="loginForm.account" placeholder="请输入账号">
                        <template #prefix>
                            <el-icon class="el-input__icon"><User /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <!-- 密码 -->
                <el-form-item>
                    <el-input v-model="loginForm.password" type="password" placeholder="请输入密码">
                        <template #prefix>
                            <el-icon class="el-input__icon"><Lock /></el-icon>
                        </template>                        
                    </el-input>
                </el-form-item>

                <div class="rememberMe">
                    <!-- 记住我 -->
                    <el-checkbox v-model="loginForm.rememberMe" label="记住我" size="large" />
                    <!-- 忘记密码 -->
                    <el-text class="forgetpass mx-1" type="primary">忘记密码?</el-text>
                </div>
                <!-- 分割线 -->
                <el-divider>其他登录方式</el-divider>

                <!-- 其他登录方式 -->
                <div class="other_login">
                    <el-icon class="other_login_item"><ChromeFilled /></el-icon>
                    <el-icon class="other_login_item"><ElemeFilled /></el-icon>
                    <el-icon class="other_login_item"><WindPower /></el-icon>
                </div>

                <!-- 按钮 -->
                <el-form-item>
                    <el-button style="width: 100%;" type="primary" @click="handleLogin">登录</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script setup lang="ts">
    import {ref} from 'vue'
    import {login} from '@/api/auth';
    import {setToken} from '@/utils/token';
    import {searchSelfRouter, searchSelfInfo} from '@/api/user';
    import {useMenuStore} from '@/stores/menu';
    import {useUserStore} from '@/stores/user';
    import {useRouter} from 'vue-router';
    

    const router = useRouter();

    // 构建store
    const menuStore = useMenuStore();
    const userStore = useUserStore();

    // 声明表单绑定值
    const loginForm = ref({
        account: undefined,
        password: undefined,
        rememberMe: undefined
    })

    // 登录方法
    function handleLogin() {
        // 调用login方法
        login(loginForm.value).then((res) => {
            console.log('登录===>', res);
            if (res.data.code == 200) {
                // 1. 将token存储到localStorage中
                setToken("aoyouToken", res.data.token);
                
                // 2. 查询用户的昵称和头像，存储到pinia中
                searchSelfInfo().then(res => {
                    if (res.data.code == 200)
                        userStore.setUserInfo(res.data.data);
                })

                // 3. 查询用户的菜单权限，存储到pinia中
                searchSelfRouter().then(res => {
                    if (res.data.code == 200)
                        menuStore.setMenuList(res.data.data);
                })

                // 4. 跳转页面
                router.push("/home")
            }
        })
    }
</script>

<style lang="scss" scoped>
.login_container {
    // 背景图（放一张背景图到src/assets/bgimg/1.jpg）
    background-image: url('../assets/bgimg/1.jpg');
    background-size: 100%;
    height: 100vh;
    display: flex;
    justify-content: flex-end;

    .login_form {
        display: flex;
        justify-content: center;
        align-items: center;
        // 设置换行
        flex-direction: column;
        // 登录表单所占宽度
        width: 40%;
        background-color: #fff;

        .title {
            margin-bottom: 20px;
        }
        .rememberMe {
            display: flex;
            justify-content: space-between;
            align-items: center;
            .forgetpass {
                cursor: pointer;
            }
        }
        .other_login {
            display: flex;
            justify-content: center;
            margin-bottom: 20px;
            .other_login_item {
                margin-left: 30px;
                margin-right: 30px;
                cursor: pointer;
            }
        }
    }
}

.el-form {
    width: 60%;
}
.el-form-item {
    width: 100%;
}
</style>