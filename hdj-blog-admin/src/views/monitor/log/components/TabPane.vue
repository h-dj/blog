<template>
  <div>
    <el-table
      :data="list"
      border
      fit
      highlight-current-row
      row-key="id"
      size="small"
      height="580"
      style="width: 100%"
    >
      <el-table-column
        v-loading="loading"
        align="center"
        label="ID"
        width="65"
        element-loading-text="请给我点时间！"
        type="index"
      />
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" class="demo-table-expand">
            <el-form-item label="描述">
              <span>{{ props.row.title }}</span>
            </el-form-item>
            <el-form-item label="请求方法">
              <span>{{ props.row.method }}</span>
            </el-form-item>
            <el-form-item label="ip">
              <span>{{ props.row.ip }}</span>
            </el-form-item>
            <el-form-item label="ip地址">
              <span>{{ props.row.ipAddress }}</span>
            </el-form-item>
            <el-form-item label="耗时">
              <span>{{ props.row.time }}ms</span>
            </el-form-item>
            <el-form-item label="请求参数">
              <span>{{ props.row.params || 'NAN' }}</span>
            </el-form-item>
            <el-form-item label="请求url">
              <span>{{ props.row.url }}</span>
            </el-form-item>
            <el-form-item label="日志等级">
              <span>{{ props.row.level }}</span>
            </el-form-item>
            <el-form-item label="浏览器">
              <span>{{ props.row.browser }}</span>
            </el-form-item>
            <el-form-item label="操作系统">
              <span>{{ props.row.os }}</span>
            </el-form-item>
            <el-form-item v-if="props.row.exception" label="异常信息">
              <p>{{ props.row.exception }}</p>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>

      <el-table-column class-name="status-col" label="等级">
        <template slot-scope="{row}">
          <el-tag :type="row.level | statusFilter">{{ row.level }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="描述">
        <template slot-scope="{row}">
          <span>{{ row.title }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="ip">
        <template slot-scope="scope">
          <span>{{ scope.row.ip }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="ip来源">
        <template slot-scope="scope">
          <span>{{ scope.row.ipAddress }}</span>
        </template>
      </el-table-column>

      <el-table-column width="120px" label="请求耗时">
        <template slot-scope="{row}">
          <el-tag :type="new Number(row.time)>1000?'danger':'info'">{{ row.time }}ms</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="浏览器">
        <template slot-scope="{row}">
          <el-tag type="primary">{{ row.browser }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column width="180px" align="center" label="创建日期">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
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
  </div>
</template>

<script>
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
import { fetchLogList } from '@/api/logs'
export default {
  components: { Pagination },
  filters: {
    parseTime,
    statusFilter(status) {
      const statusMap = {
        info: 'info',
        error: 'danger'
      }
      return statusMap[status.toLocaleLowerCase()]
    }
  },
  props: {
    level: {
      type: String,
      default: 'INFO'
    }
  },
  data() {
    return {
      list: [],
      total: 0,
      listQuery: {
        page: 1,
        pageSize: 10,
        level: this.level
      },
      loading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      fetchLogList(this.listQuery)
        .then(response => {
          this.list = response.data.list
          this.total = response.data.totalCount
          this.loading = false
        })
        .catch(() => {
          this.loading = false
        })
    }
  }
}
</script>
<style lang="scss">
p {
  width: 100%;
  height: auto;
  word-wrap: break-word;
  word-break: break-all;
  overflow: hidden;
}
</style>
