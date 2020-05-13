<template>
  <div class="app-container">
    <div class="filter-container" style="margin-bottom: 10px;">
      <el-input
        v-model="listQuery.key"
        size="mini"
        placeholder="邮箱或用户名"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <el-button
        size="mini"
        class="filter-item"
        type="success"
        icon="el-icon-search"
        @click="handleFilter"
      >搜索</el-button>
      <el-button
        size="mini"
        class="filter-item"
        style="margin-left: 10px;"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
      >添加</el-button>
    </div>

    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      row-key="id"
      style="width: 100%"
      size="small"
      @sort-change="sortChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column align="center" label="ID" width="80" type="index" />
      <el-table-column
        label="登录时间"
        prop="login_time"
        width="150px"
        align="center"
        sortable="custom"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.loginTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="邮箱" min-width="150">
        <template slot-scope="{row}">
          <span>{{ row.email }}</span>
        </template>
      </el-table-column>
      <el-table-column label="用户名" width="110" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.userName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" class-name="status-col" width="100px">
        <template slot-scope="{row}">
          <el-tag>{{ row.enable }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="locked" class-name="status-col" width="100px">
        <template slot-scope="{row}">
          <el-tag>{{ row.locked }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        width="250px"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="{row}">
          <div v-if="row.id != '0'" class="operation">
            <el-button
              type="primary"
              size="mini"
              @click="handleUpdate(row)"
            >编辑</el-button>
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(row)"
            >删除</el-button>
          </div>
          <div v-else>
            超级管理员
          </div>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="listQuery.page"
      :limit.sync="listQuery.pageSize"
      @pagination="getList"
    />

    <user-form ref="userForm" :is-add="isAdd" @reload="getList" />
  </div>
</template>

<script>
import { parseTime } from '@/utils'
import { fetchList, deleteUser } from '@/api/user'
import Pagination from '@/components/Pagination'
import UserForm from './form'
export default {
  components: {
    Pagination,
    UserForm
  },
  filters: {
    parseTime
  },
  data() {
    return {
      isAdd: true,
      tableKey: 0,
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        pageSize: 20,
        key: undefined,
        sortType: undefined,
        sort: undefined
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchList(this.listQuery)
        .then(response => {
          this.list = response.data.list
          this.total = response.data.totalCount
          // Just to simulate the time of the request
          setTimeout(() => {
            this.listLoading = false
          }, 0.3 * 1000)
        })
        .catch(() => {
          this.list = []
          this.total = 0
          this.listLoading = false
        })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    sortChange(data) {
      const { prop, order } = data
      this.listQuery.sort = null
      this.listQuery.sortType = null
      if (prop) {
        this.listQuery.sort = prop
      }
      if (order) {
        this.listQuery.sortType = order
      }
      this.getList()
    },
    handleCreate() {
      this.isAdd = true
      this.$refs.userForm.getRoleList()
      this.$refs.userForm.dialogFormVisible = true
    },

    handleUpdate(row) {
      this.isAdd = false
      this.$refs.userForm.dialogFormVisible = true
      this.$refs.userForm.getUserInfo(row.id)
    },

    handleDelete(row) {
      this.$confirm(
        '确定删除该用户吗？',
        {
          confirmButtonText: 'Confirm',
          cancelButtonText: 'Cancel',
          type: 'warning'
        }
      ).then(() => {
        const data = [row.id]
        deleteUser(data)
          .then(() => {
            this.getList()
          })
      })
    }
  }
}
</script>
