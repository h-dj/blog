<template>
  <div class="app-container">
    <div class="filter-container" style="margin-bottom: 10px;">
      <el-input
        v-model="listQuery.account"
        size="mini"
        placeholder="文件名称"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <el-date-picker
        v-model="listQuery.date"
        type="daterange"
        range-separator=":"
        class="el-range-editor--small filter-item"
        style="height: 30.5px;width: 220px"
        value-format="yyyy-MM-dd HH:mm:ss"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
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
        type="primary"
        icon="el-icon-upload"
        @click="handleUpload"
      >上传</el-button>
    </div>

    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
      @sort-change="sortChange"
    >
      <el-table-column label="ID" prop="id" align="center" width="55" type="index" />
      <el-table-column label="文件名称">
        <template slot-scope="{row}">
          <span>{{ row.fileName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建人" width="80">
        <template slot-scope="{row}">
          <el-tag>{{ row.createBy }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="缩略图" width="80" align="center">
        <template slot-scope="{row}">
          <el-image
            style="width: 60px; height: 60px"
            :src="row.url"
            fit="contain"
            lazy
            :preview-src-list="srcList"
          >
            <div slot="error" class="image-slot">
              加载失败
            </div>
          </el-image>
        </template>
      </el-table-column>
      <el-table-column label="文件类型" width="80">
        <template slot-scope="scope">
          <span>{{ scope.row.fileSuffix }}</span>
        </template>
      </el-table-column>
      <el-table-column label="文件大小" width="80">
        <template slot-scope="{row}">
          <el-tag>{{ row.fileSize }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="文件位置">
        <template slot-scope="{row}">
          <span>{{ row.path }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        width="80"
      >
        <template slot-scope="{row}">
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(row)"
          >删除</el-button>
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

    <drag-uploader ref="dragUploader" @onSuccess="uploadSuccess" />
  </div>
</template>

<script>
import { parseTime } from '@/utils'
import { fetchList } from '@/api/picture'
import Pagination from '@/components/Pagination'
import DragUploader from '@/components/Uploader/DragUploader'
export default {
  components: {
    Pagination,
    DragUploader
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
        pageSize: 5,
        account: undefined,
        sortType: undefined,
        sort: undefined
      }
    }
  },
  computed: {
    srcList: function() {
      const srcList = []
      this.list.forEach((v, i, arr) => {
        srcList.push(v.url)
      })
      return srcList
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
    handleUpload() {
      this.$refs.dragUploader.show()
    },
    uploadSuccess(response, file, fileList) {
      if (response && response.success) {
        this.$message('上传成功!')
        this.$refs.dragUploader.close()
        this.getList()
      }
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

    },

    handleUpdate(row) {

    },

    handleDelete(row) {

    }
  }
}
</script>
