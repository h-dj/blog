<template>
  <div class="app-container">
    <div class="filter-container" style="margin-bottom: 10px;">
      <el-input
        v-model="listQuery.roleName"
        size="mini"
        placeholder="角色名称"
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
      :data="roleList"
      border
      style="width: 100%"
      height="350"
      size="small"
      @sort-change="sortChange"
    >
      <el-table-column label="ID" prop="id" align="center" width="80" type="index" />
      <el-table-column label="角色名称" min-width="150">
        <template slot-scope="{row}">
          <span class="link-type">{{ row.roleName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="角色编码" min-width="150">
        <template slot-scope="{row}">
          <span class="link-type">{{ row.roleCode }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" width="110" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.remark }}</span>
        </template>
      </el-table-column>

      <el-table-column
        label="创建时间"
        prop="create_time"
        width="150px"
        align="center"
        sortable="custom"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="修改时间"
        prop="update_time"
        width="150px"
        align="center"
        sortable="custom"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.updateTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>

      <el-table-column
        label="操作"
        align="center"
        width="250px"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="handleUpdate(row)" />
          <el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(row)" />
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

    <role-menu ref="roleForm" :is-add="isAdd" @reload="getList" />
  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import { parseTime } from '@/utils'
import RoleMenu from './form'
import { fetchList, deleteRole } from '@/api/role'
import { setTimeout } from 'timers'
export default {
  components: {
    Pagination,
    RoleMenu
  },
  filters: {
    parseTime
  },
  data() {
    return {
      isAdd: true,
      roleList: [],
      total: 0,
      listLoading: true,
      tableKey: 0,
      listQuery: {
        page: 1,
        pageSize: 10,
        roleName: '',
        sortType: '',
        sort: []
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
          this.roleList = response.data.list || []
          this.total = response.data.totalCount || 0
          // Just to simulate the time of the request
          setTimeout(() => {
            this.listLoading = false
          }, 0.3 * 1000)
        })
        .catch(() => {
          this.roleList = []
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
      this.$refs.roleForm.loadMenuSelectList()
      this.$refs.roleForm.dialogFormVisible = true
    },
    handleUpdate(row) {
      this.isAdd = false
      this.$refs.roleForm.dialogFormVisible = true
      this.$refs.roleForm.getRoleInfo(row.id)
    },
    handleDelete(row) {
      this.$confirm('确定删除该角色吗？', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const data = [row.id]
        deleteRole(data).then(() => {
          this.getList()
        })
      })
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
