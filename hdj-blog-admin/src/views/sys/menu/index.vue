<template>
  <div class="app-container">
    <div class="filter-container" style="margin-bottom: 10px;">
      <el-input
        v-model="listQuery.key"
        size="small"
        placeholder="输入菜单名称，模糊查询"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <el-button
        size="small"
        class="filter-item"
        style="margin-left: 10px;"
        type="success"
        icon="el-icon-search"
        @click="handleFilter"
      >搜索</el-button>
      <el-button
        size="small"
        class="filter-item"
        style="margin-left: 10px;"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
      >添加</el-button>
    </div>

    <el-table
      v-loading="listLoading"
      :data="menuList"
      row-key="id"
      border
      style="width: 100%"
      size="mini"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column label="ID" prop="id" align="center" type="index" />
      <el-table-column prop="icon" label="图标">
        <template slot-scope="{row}">
          <svg-icon :icon-class="row.icon || ''" />
        </template>
      </el-table-column>
      <el-table-column prop="menuName" label="菜单名称" />
      <el-table-column prop="type" label="type" width="80px">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.type |menuTypeFilter }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="url" label="路由" width="150px" />
      <el-table-column prop="component" label="组件" width="150px" />
      <el-table-column prop="permission" label="权限标识" width="180px" />
      <el-table-column prop="hidden" label="是否隐藏" width="80px">
        <template slot-scope="scope">
          <el-tag :type="scope.row.hidden?'success':'danger'">{{ scope.row.hidden }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="handleUpdate(row)" />
          <el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(row)" />
        </template>
      </el-table-column>
    </el-table>

    <menu-form ref="menuForm" :is-add="isAdd" @reload="reload" />
  </div>
</template>

<script>
import { fetchListAll, deleteMenu } from '@/api/menu'
import { setTimeout } from 'timers'
import { treeDataTranslate, parseTime } from '@/utils'
import MenuForm from './form'
export default {
  components: {
    MenuForm
  },
  filters: {
    parseTime,
    menuTypeFilter(type = 0) {
      const menuTypeMap = {
        0: '目录',
        1: '菜单',
        2: '按钮'
      }
      return menuTypeMap[type.toString()]
    }

  },
  data() {
    return {
      isAdd: true,
      listQuery: {
        key: ''
      },
      menuList: [],
      listLoading: true,
      tableKey: 0
    }
  },
  created() {
    this.getList()
  },
  methods: {
    handleFilter() {
      this.getList()
    },
    getList() {
      this.listLoading = true
      fetchListAll(this.listQuery)
        .then(response => {
          this.menuList = treeDataTranslate(response.data) || []
          setTimeout(() => {
            this.listLoading = false
          }, 0.3 * 1000)
        })
        .catch(() => {
          this.menuList = []
          this.listLoading = false
        })
    },

    handleCreate() {
      this.isAdd = true
      this.$refs.menuForm.loadMenuSelectList()
      this.$refs.menuForm.dialogFormVisible = true
    },

    handleUpdate(row) {
      this.isAdd = false
      this.$refs.menuForm.dialogFormVisible = true
      this.$refs.menuForm.getMenuInfo(row.id)
    },

    handleDelete(row) {
      this.$confirm(
        '确定删除该菜单吗？',
        {
          confirmButtonText: 'Confirm',
          cancelButtonText: 'Cancel',
          type: 'warning'
        }
      ).then(() => {
        deleteMenu(row.id)
          .then(() => {
            this.getList()
          })
      })
    },
    reload() {
      this.getList()
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
