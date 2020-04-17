<template>
  <div class="app-container">
    <div class="filter-container" style="margin-bottom: 10px;">
      <el-input
        v-model="listQuery.title"
        placeholder="文章标题"
        style="width: 200px;"
        size="mini"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <el-button
        class="filter-item"
        type="primary"
        size="mini"
        icon="el-icon-search"
        @click="handleFilter"
      >搜索</el-button>
      <el-button
        style="margin-left: 10px;"
        size="mini"
        class="filter-item"
        type="danger"
        icon="el-icon-delete"
        @click="handleRemoves"
      >批量删除</el-button>

      <el-button
        style="margin-left: 10px;"
        size="mini"
        class="filter-item"
        type="success"
        icon="el-icon-import"
        @click="handleImport"
      >导入</el-button>
    </div>
    <el-table
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      row-key="id"
      style="width: 100%"
      size="mini"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column align="center" label="ID" width="80" type="index" />

      <el-table-column width="160px" align="center" label="发布时间">
        <template slot-scope="scope">
          <span v-if="scope.row.publishTime">{{ scope.row.publishTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="作者">
        <template slot-scope="scope">
          <span>{{ scope.row.authorName }}</span>
        </template>
      </el-table-column>

      <el-table-column class-name="status-col" label="状态">
        <template slot-scope="{row}">
          <el-tag>{{ row.status | statusFilter }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column min-width="300px" label="标题">
        <template slot-scope="{row}">
          <router-link :to="'/article/edit/'+row.id" class="link-type">
            <el-link type="primary">{{ row.title }}</el-link>
          </router-link>
        </template>
      </el-table-column>

      <el-table-column align="center" label="操作">
        <template slot-scope="{row}">
          <router-link :to="'/article/edit/'+row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit" />
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="handleDelete(row)" />
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="listQuery.page"
      :limit.sync="listQuery.limit"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { parseTime } from '@/utils'
import { fetchList, deleteArticle } from '@/api/article'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
export default {
  name: 'ArticleList',
  components: {
    Pagination
  },
  filters: {
    parseTime,
    statusFilter(status) {
      const statusMap = {
        0: '新建',
        1: '发布',
        2: '删除'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        title: null
      },
      selectList: []
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
          this.listLoading = false
        })
        .catch(() => {
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
    handleDelete(row) {
      this.$confirm(
        '是否删除该文章？',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      ).then(() => {
        const data = [row.id]
        deleteArticle(data)
          .then(() => {
            this.getList()
            this.$notify({
              title: '成功',
              message: '删除成功',
              type: 'success'
            })
          })
      })
    },
    handleSelectionChange(val) {
      this.selectList = []
      if (val) {
        val.forEach(element => {
          this.selectList.push(element.id)
        })
      }
    },
    handleRemoves() {
      if (this.selectList == null || this.selectList.length === 0) {
        this.$message({
          message: '先选择要删除的文章',
          type: 'warning'
        })
        return
      }

      this.$confirm('确认删除选择的文章吗？', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteArticle(this.selectList).then(() => {
          this.getList()
        })
      })
    },
    handleImport() {

    }
  }
}
</script>

<style scoped>
.edit-input {
  padding-right: 100px;
}

.cancel-btn {
  position: absolute;
  right: 15px;
  top: 10px;
}
</style>
