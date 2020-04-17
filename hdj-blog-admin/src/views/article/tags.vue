<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.tagName"
        size="mini"
        placeholder="标签名称"
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
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
      >添加</el-button>
      <el-button
        size="mini"
        class="filter-item"
        type="danger"
        icon="el-icon-delete"
        @click="handleRemoves"
      >批量删除</el-button>
    </div>
    <el-table
      v-loading="listLoading"
      :data="list"
      border
      row-key="id"
      style="width: 100%"
      height="350"
      size="mini"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column align="center" label="ID" width="80" type="index" />

      <el-table-column align="center" label="标签名称" min-width="300px">
        <template slot-scope="scope">
          <span>{{ scope.row.tagName }}</span>
        </template>
      </el-table-column>

      <el-table-column label="创建时间">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="handleUpdate(row)" />
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

    <el-dialog :title="dialogStatus" :visible.sync="dialogFormVisible" width="580px">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="tagObj"
        label-position="left"
        label-width="80px"
      >
        <el-form-item label="标签名称" prop="tagName">
          <el-input v-model="tagObj.tagName" placeholder="标签名称" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button
          type="primary"
          :loading="submitLoading"
          @click="dialogStatus==='create'?createData():updateData()"
        >确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { parseTime } from '@/utils'
import { fetchTagList, addTag, updateTag, getTag, deleteTags } from '@/api/tag'
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
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 5,
        tagName: null
      },
      tagObj: {
        id: null,
        tagName: ''
      },
      rules: {
        tagName: {
          required: true,
          message: '请输入标签名称',
          trigger: 'change'
        }
      },
      selectList: []
    }
  },
  created() {
    this.getList()
  },
  methods: {
    resetTemp() {
      this.tagObj = {
        id: null,
        tagName: ''
      }
    },
    getList() {
      this.listLoading = true
      fetchTagList(this.listQuery)
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
    handleDelete(row) {
      this.$confirm('确认删除该标签吗？', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          const data = [row.id]
          deleteTags(data).then(() => {
            this.getList()
          })
        })
        .catch(() => {})
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          this.submitLoading = true
          const tempData = Object.assign({}, this.tagObj)
          addTag(tempData)
            .then(() => {
              this.dialogFormVisible = false
              this.submitLoading = false
              this.$notify({
                title: 'Success',
                message: '添加成功！',
                type: 'success',
                duration: 2000
              })
              this.getList()
            })
            .catch(e => {
              this.submitLoading = false
            })
        }
      })
    },
    handleUpdate(row) {
      getTag(row.id).then(response => {
        this.tagObj = response.data
        this.dialogStatus = 'update'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate()
        })
      })
    },
    updateData() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          this.submitLoading = true
          const tempData = Object.assign({}, this.tagObj) // copy obj
          updateTag(tempData['id'], tempData)
            .then(() => {
              this.submitLoading = false
              this.$notify({
                title: 'Success',
                message: '更新成功！',
                type: 'success',
                duration: 2000
              })
              this.getList()
              this.dialogFormVisible = false
            })
            .catch(e => {
              this.submitLoading = false
            })
        }
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
          message: '先选择要删除的标签',
          type: 'warning'
        })
        return
      }
      this.$confirm('确认删除选择的分类吗？', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteTags(this.selectList).then(() => {
          this.getList()
        })
      })
    }
  }
}
</script>
<style lang="scss" scope>
.filter-container {
  margin: 10px;
}
</style>
