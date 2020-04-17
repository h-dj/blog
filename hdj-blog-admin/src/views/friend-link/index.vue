<template>
  <div class="app-container">
    <div class="filter-container" style="margin-bottom: 10px;">
      <el-select v-model="listQuery.status" size="mini" placeholder="请选择">
        <el-option label="全部" value />
        <el-option label="通过" value="1" />
        <el-option label="不通过" value="2" />
      </el-select>
      <el-button
        size="mini"
        class="filter-item"
        style="margin-left: 10px;"
        type="success"
        icon="el-icon-search"
        @click="handleFilter"
      >搜索</el-button>

      <el-button
        size="mini"
        class="filter-item"
        style="margin-left: 10px;"
        type="primary"
        icon="el-icon-add"
        @click="handleAdd"
      >添加</el-button>
      <el-button
        size="mini"
        class="filter-item"
        style="margin-left: 10px;"
        type="danger"
        icon="el-icon-delete"
        @click="handleDeleteBatch"
      >批量删除</el-button>
    </div>

    <el-table
      v-loading="listLoading"
      :data="list"
      row-key="id"
      border
      style="width: 100%"
      size="mini"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column label="ID" prop="id" align="center" type="index" />
      <el-table-column prop="email" label="邮箱" />
      <el-table-column prop="url" label="链接" />
      <el-table-column prop="describe" label="描述" />

      <el-table-column prop="status" label="状态" width="80px">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.status ==1?'success':'info'"
          >{{ scope.row.status | statusFilter }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="createTime" label="创建时间">
        <template slot-scope="{row}">{{ row.createTime | parseTime }}</template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button size="mini" type="primary" @click="handleExamine(row)">审核</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(row)">删除</el-button>
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

    <friend-link-form ref="friendLinkForm" @reload="getList" />
  </div>
</template>

<script>
import { getList, deleteLinks, examineLink } from '@/api/friend-link'
import { setTimeout } from 'timers'
import { parseTime } from '@/utils'
import FriendLinkForm from './form'
import Pagination from '@/components/Pagination'
export default {
  components: { FriendLinkForm, Pagination },
  filters: {
    parseTime,
    statusFilter(type = 0) {
      const menuTypeMap = {
        0: '新建',
        1: '通过'
      }
      return menuTypeMap[type]
    }
  },
  data() {
    return {
      listQuery: {
        page: 1,
        pageSize: 10,
        status: undefined
      },
      listLoading: true,
      tableKey: 0,
      list: [],
      total: 0,
      selectList: []
    }
  },
  created() {
    this.getList()
  },
  methods: {
    handleExamine(row) {
      this.$confirm('是否通过审核?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const data = {
          id: row.id,
          status: 1
        }
        examineLink(data)
          .then((r) => {
            if (r.success) {
              row.status = 1
              this.$message('审核通过！')
            }
          })
      })
    },
    handleFilter() {
      this.getList()
    },
    handleSelectionChange(val) {
      this.selectList = []
      if (val) {
        val.forEach(element => {
          this.selectList.push(element.id)
        })
      }
    },
    getList() {
      this.listLoading = true
      getList(this.listQuery)
        .then(response => {
          this.list = response.data.list
          this.total = response.data.totalCount
          setTimeout(() => {
            this.listLoading = false
          }, 0.3 * 1000)
        })
        .catch(() => {
          this.list = []
          this.listLoading = false
        })
    },
    handleAdd() {
      this.$refs.friendLinkForm.dialogFormVisible = true
    },
    handleDelete(row) {
      this.$confirm('确定删除该链接吗？', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteLinks([row.id]).then(() => {
          this.getList()
        })
      })
    },
    handleDeleteBatch() {
      if (!this.selectList) {
        this.$message('先选择要删除的友链！')
        return
      }
      this.$confirm('确定删除选中的链接吗？', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteLinks(this.selectList).then(() => {
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
