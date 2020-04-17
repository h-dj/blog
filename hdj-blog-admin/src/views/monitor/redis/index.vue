<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.title"
        size="mini"
        placeholder="关键字"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <el-button
        class="filter-item"
        size="mini"
        type="success"
        icon="el-icon-search"
        @click="handleFilter"
      >搜索</el-button>
      <el-button
        class="filter-item"
        type="warning"
        size="mini"
        icon="el-icon-delete"
        @click="handleTruncate"
      >清空</el-button>

    </div>
    <el-table
      v-loading="listLoading"
      :data="list"
      border
      stripe
      fit
      highlight-current-row
      size="small"
      style="width: 100%;"
    >
      <el-table-column align="center" label="ID" width="80" type="index" />

      <el-table-column align="center" label="缓存键">
        <template slot-scope="scope">
          <span>{{ scope.row.tagName }}</span>
        </template>
      </el-table-column>

      <el-table-column width="180px" align="center" label="缓存值">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="操作" width="250">
        <template slot-scope="{row}">
          <el-button
            type="danger"
            size="small"
            icon="el-icon-delete"
            @click="handleDelete(row)"
          >删除</el-button>
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
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
export default {
  name: 'Tags',
  components: {
    Pagination
  },
  filters: {
    parseTime
  },
  data() {
    return {
      submitLoading: false,
      dialogStatus: 'create',
      dialogFormVisible: false,
      list: null,
      total: 0,
      listLoading: false,
      listQuery: {
        page: 1,
        limit: 20,
        title: null
      },

      rules: {
        tagName: {
          required: true,
          message: '请输入标签名称',
          trigger: 'change'
        }
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {

    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    handleDelete(row) {
      this.$confirm('确认删除该缓存吗？', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {

      })
        .catch(() => {})
    },
    handleTruncate(){}
  }
}
</script>
