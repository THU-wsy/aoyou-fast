<template>
    <!-- 头部搜索框 -->
    <el-form :model="queryForm" :inline="true">
        <el-form-item label="菜单名称">
            <el-input v-model="queryForm.menuName" />
        </el-form-item>
        <el-form-item label="权限名称">
            <el-input v-model="queryForm.perms" />
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="handleQuery">搜索</el-button>
            <el-button @click="handleRest">重置</el-button>
        </el-form-item>
    </el-form>

    <!-- 功能按钮 -->
    <el-row :gutter="4">
        <el-col :span="6">
            <el-button type="primary" @click="handleAdd">新增</el-button>
            <el-button type="danger" @click="handleRemove(0)">删除</el-button>
        </el-col>
    </el-row>

    <!-- 列表 -->
    <el-table :data="menuList" style="width: 100%" row-key="id" default-expand-all @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" />
        <el-table-column type="index" label="序号" width="55" />
        <el-table-column prop="menuName" label="菜单名称" width="200" />
        <el-table-column prop="perms" label="权限标识" width="200" />
        <el-table-column prop="path" label="路由地址" width="200" />
        <el-table-column prop="componentPath" label="组件名称" width="200" />
        <!-- <el-table-column prop="createTime" label="创建时间" width="200" /> -->
        <!-- <el-table-column prop="updateTime" label="修改时间" width="200" /> -->
        <el-table-column prop="remark" label="备注" width="200" />
        <el-table-column label="操作" width="200">
            <template #default="scope">
                <el-button link type="success" size="small" @click="handleEdit(scope.row.id)">修改</el-button>
                <el-button link type="danger" size="small" @click="handleRemove(scope.row.id)">删除</el-button>
            </template>
        </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="pagination_container">
        <el-pagination v-model:current-page="queryForm.pageNum" v-model:page-size="queryForm.pageSize"
            :page-sizes="[10, 20, 30, 40, 50]" layout="total, sizes, prev, pager, next, jumper" :total="total"
            @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    </div>

    <!-- 新增和修改的弹窗 -->
    <el-dialog v-model="menuFormShow" :title="menuTitle" width="50%" :before-close="handleClose">
        <!-- 表单 -->
        <el-form :model="form" label-width="120px">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="上级菜单" prop="form.parentId">
                        <el-tree-select check-strictly v-model="form.parentId" :data="menuSelectData" :render-after-expand="false" placeholder="请选择上级菜单"/>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="菜单类型" prop="form.menuType">
                        <el-select v-model="form.menuType" class="m-2" placeholder="请选择菜单类型">
                            <el-option
                                v-for="item in menuTypeOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                            />
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>
           
            <el-row>
                <el-col :span="12">
                    <el-form-item label="菜单图标" prop="form.icon">
                        <el-popover
                            placement="bottom-start"
                            :width="460"
                            trigger="click"
                        >
                            <template #reference>
                                <!-- 通过插槽实现图标选择，默认是一个有图标的输入框 -->
                                <el-input v-model="form.icon" placeholder="请选择图标">
                                    <template #prefix>
                                        <!-- 判断是否选中了icon -->
                                        <svg-icon
                                            v-if="form.icon"
                                            slot="prefix"
                                            :name="form.icon"
                                            width="16px"
                                            height="16px"
                                        />
                                        <!-- 如果未选中，显示默认的搜索图标 -->
                                        <el-icon v-else class="el-input__icon"><search /></el-icon>
                                    </template>
                                </el-input>
                            </template>
                            <!-- 显示选择图标的组件 -->
                            <IconSelect ref="iconSelect" @selected="handleSelect" :active-icon="form.icon"/>
                        </el-popover>
                        
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="菜单名称" prop="form.menuName">
                        <el-input v-model="form.menuName" placeholder="请输入菜单名称"/>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row>
                <el-col :span="12">
                    <el-form-item label="排序" prop="form.sort">
                        <el-input-number :min="0" v-model="form.sort"/>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="路由地址" prop="form.path">
                        <el-input v-model="form.path" placeholder="请输入路由地址"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="组件名称" prop="form.componentPath">
                        <el-input v-model="form.componentPath" placeholder="请输入组件名称"/>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="权限标识" prop="form.perms">
                        <el-input v-model="form.perms" placeholder="请输入权限标识"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="状态" prop="form.status">
                        <el-switch
                            v-model="form.status"
                            active-text="可用"
                            inactive-text="不可用"
                            :active-value="0"
                            :inactive-value="1"
                        />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="备注" prop="form.remark">
                        <el-input v-model="form.remark" placeholder="请输入备注"/>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>

        <template #footer>
            <span class="dialog-footer">
                <el-button @click="menuFormShow = false">取消</el-button>
                <el-button type="primary" @click="handleSubmit">提交</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup lang="ts" name="menu">
    import { ref, onMounted } from 'vue';
    // 导入图标选择组件
    import IconSelect from '@/components/IconSelect/index.vue';
    // 导入消息提示
    import { ElMessage, ElMessageBox  } from 'element-plus';
    // 导入菜单接口
    import { searchMenuList,saveMenu,updateMenu,searchMenuById,removeMenu } from '@/api/menu';

    /* 数据 */

    let total = ref(0); // 一共有几条数据
    let selectIds = ref([]); // 多选选中的数据
    let menuFormShow = ref(false); // 新增菜单的弹窗是否显式，默认不显示
    let menuTitle = ref(""); // 弹窗的名称：新增菜单/修改菜单
    
    // 头部搜索框的数据
    let queryForm = ref({
        menuName: undefined,
        perms: undefined,
        pageNum: 1,
        pageSize: 10
    })
    // 弹窗表单数据
    let form = ref({
        id: undefined,
        parentId: undefined,
        menuName: undefined,
        path: undefined,
        componentPath: undefined,
        perms: undefined,
        icon: undefined,
        menuType: 0,
        sort: 0,
        status: 0,
        remark: '',
    })

    // 树形选择器
    let menuSelectData = ref([])
    // 菜单类型选择器
    let menuTypeOptions = ref([
        {
            value: 0,
            label: "目录"
        },
        {
            value: 1,
            label: "菜单"
        },
        {
            value: 2,
            label: "按钮"
        }
    ])

    // 菜单数据列表
    let menuList = ref([]);

    /* 挂载时，查询所有菜单 */
    onMounted(() => {
        handleSearchMenuList();
    })
    // 查询所有菜单
    function handleSearchMenuList() {
        searchMenuList(queryForm.value).then(res => {
            if (res.data.code == 200) {
                // 获取数据
                menuList.value = res.data.data.list;
                total.value = res.data.data.total;
                let menuSelect = []; 
                // 添加一个根目录，所有的菜单都是根目录的子菜单
                menuSelect.push({label: '主目录', value: 0, children: []})
                // 循环查出来的数据
                for (const item of res.data.data.list) {
                    let label = item.menuName;
                    let value = item.id;
                    let childrenList = [];
                    // 判断是否有子菜单
                    if(item.children.length > 0) {
                        for (const childrenItem of item.children) {
                            let label = childrenItem.menuName;
                            let value = childrenItem.id;
                            let children = {label: label,value: value}
                            childrenList.push(children)
                        }
                    }
                    let menu = {label: label, value: value, children: childrenList}
                    menuSelect[0].children.push(menu);
                }
                menuSelectData.value = menuSelect;
            }
        })
    }


    /* 头部搜索框相关方法 */

    // 搜索
    function handleQuery() {
        queryForm.value.pageNum = 1;
        queryForm.value.pageSize = 10;
        searchMenuList(queryForm.value).then(res => {
            if (res.data.code == 200) {
                // 获取数据
                menuList.value = res.data.data.list;
                total.value = res.data.data.total;
            }
        })
    }

    // 重置
    function handleRest() {
        queryForm.value.pageNum = 1;
        queryForm.value.pageSize = 10;
        queryForm.value.menuName = undefined;
        queryForm.value.perms = undefined;
        searchMenuList(queryForm.value).then(res => {
            if (res.data.code == 200) {
                // 获取数据
                menuList.value = res.data.data.list;
                total.value = res.data.data.total;
            }
        })
    }

    /* 功能按钮相关方法 */
    
    // 新增按钮（弹出表单即可）
    function handleAdd() {
        form.value.id = undefined;
        menuFormShow.value = true;
        menuTitle.value = "新增菜单";
    }

    // 删除按钮，弹出是否要删除数据，确定就删除，取消就不删除
    function handleRemove(id) {
        // ids是要删除的菜单id列表
        let ids = undefined; 
        if (id > 0) {
            ids = [id]
        } else {
            if (selectIds.value.length == 0)
                return;
            ids = selectIds.value;
        }

        ElMessageBox.confirm(
            `确定要删除这些数据吗?`,
            '删除菜单',
            {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }
        ).then(() => {
            removeMenu(ids).then(res => {
                if (res.data.code == 200) {
                    ElMessage.success('删除成功');
                    // 刷新列表
                    handleSearchMenuList();
                }
            })
        }).catch(() => {})
    }
    /* 列表相关方法 */

    // 修改按钮（首先根据id查询对应的菜单数据，然后弹出表单并回显数据）
    function handleEdit(id) {
        // 先查询数据，再弹窗
        searchMenuById(id).then(res => {
            if (res.data.code == 200) {
                // 保证后端返回的字段名和前端字段名相同，就可以一一对应赋值
                form.value = res.data.data;
                menuFormShow.value = true;
                menuTitle.value = "修改菜单";
            } else {
                ElMessage.error('数据查询失败')
            }
        })
    }

    // 多选变化
    function handleSelectionChange(selection) {
        let ids = selection.map(item => item.id)
        selectIds.value = ids;
    }

    /* 分页相关方法 */

    function handleSizeChange(sizeNumber) {
        queryForm.value.pageSize = sizeNumber;
        searchMenuList(queryForm.value).then(res => {
            if (res.data.code == 200) {
                // 获取数据
                menuList.value = res.data.data.list;
                total.value = res.data.data.total;
            }
        })
    }

    // 点击下一页，上一页
    function handleCurrentChange(pageNumber) {
        queryForm.value.pageNum = pageNumber;
        searchMenuList(queryForm.value).then(res => {
            if (res.data.code == 200) {
                // 获取数据
                menuList.value = res.data.data.list;
                total.value = res.data.data.total;
            }
        })
    }

    /* 表单相关方法 */
    // 数据校验，如果字符串为空，则返回true
    function isStrBlank(data) {
        return data === undefined || data === null ||
            data == '' || data.trim() === '';
    }
    // 数据校验，如果数值为空，则返回true
    function isNumBlank(data) {
        return data === undefined || data === null;
    }

    // 提交表单，根据form.id值判断是新增还是修改【有id值则为修改】
    function handleSubmit() {
        // 做数据校验
        if (isNumBlank(form.value.parentId)) {
            ElMessage.warning('上级菜单不能为空');
            return;
        }
        if (isStrBlank(form.value.menuName)) {
            ElMessage.warning('菜单名称不能为空');
            return;
        }
        if (isStrBlank(form.value.path)) {
            ElMessage.warning('路由地址不能为空');
            return;            
        }
        if (isStrBlank(form.value.componentPath)) {
            ElMessage.warning('组件名称不能为空');
            return;            
        }
        if (isStrBlank(form.value.perms)) {
            ElMessage.warning('权限标识不能为空');
            return;            
        }
        if (isStrBlank(form.value.icon)) {
            ElMessage.warning('菜单图标不能为空');
            return;            
        }        

        if (form.value.id) {
            // 修改
            updateMenu(form.value).then(res => {
                if(res.data.code == 200) {
                    // 关闭窗口
                    menuFormShow.value = false;
                    // 刷新列表
                    handleSearchMenuList();
                    // 弹窗提示新增成功
                    ElMessage.success('修改菜单成功')
                }
            })
        } else {
            // 新增，调用新增接口
            saveMenu(form.value).then(res => {
                if (res.data.code == 200) {
                    // 关闭窗口
                    menuFormShow.value = false;
                    // 刷新列表
                    handleSearchMenuList();
                    // 弹窗提示新增成功
                    ElMessage.success('新增菜单成功')
                }
            })
        }
    }

    // 关闭弹窗
    function handleClose() {
        menuFormShow.value = false;
    }

    // 选择图标
    function handleSelect(name) {
        form.value.icon = name;
    }
</script>

<style lang="scss" scoped>
.pagination_container {
    position: relative;
    height: 40px;
    margin-top: 15px;
}

.el-pagination {
    position: absolute;
    right: 110px;
}
</style>