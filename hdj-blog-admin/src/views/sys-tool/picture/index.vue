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
        v-model="date"
        size="mini"
        type="daterange"
        range-separator=":"
        style="width: 200px"
        value-format="yyyy-MM-dd"
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
      highlight-current-row
      style="width: 100%;"
      size="mini"
      @sort-change="sortChange"
    >
      <el-table-column label="ID" prop="id" align="center" width="55" type="index" />
      <el-table-column label="文件名称">
        <template slot-scope="{row}">
          <span>{{ row.fileName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="缩略图" align="center">
        <template slot-scope="{row}">
          <el-image
            style="width: 45px; height: 45px"
            :src="row.url+'?imageView2/1/w/45/h/45'"
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
      <el-table-column label="文件类型">
        <template slot-scope="scope">
          <span>{{ scope.row.fileSuffix }}</span>
        </template>
      </el-table-column>
      <el-table-column label="文件大小">
        <template slot-scope="{row}">
          <el-tag>{{ row.fileSize | formatFileSize }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
      >
        <template slot-scope="{row}">
          <el-button
            size="mini"
            type="success"
            @click="handleCopy(row.url,$event)"
          >复制外链</el-button>
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
import clip from '@/utils/clipboard'
import { parseTime, formatFileSize } from '@/utils'
import { fetchList } from '@/api/picture'
import Pagination from '@/components/Pagination'
import DragUploader from '@/components/Uploader/DragUploader'
export default {
  components: {
    Pagination,
    DragUploader
  },
  filters: {
    parseTime, formatFileSize
  },
  data() {
    return {
      isAdd: true,
      tableKey: 0,
      list: [],
      total: 0,
      listLoading: true,
      date: [],
      listQuery: {
        page: 1,
        pageSize: 10,
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
      if (this.date && this.date.length > 1) {
        this.listQuery.uploadTimeFrom = this.date[0]
        this.listQuery.uploadTimeTo = this.date[1]
      }

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

    handleCopy(text, event) {
      console.log(text)
      clip(text, event)
    }
  }
}
</script>
