<template>
  <div class="app-container">
    <div class="filter-container" style="margin-bottom: 10px;">
      <el-input
        v-model="listQuery.title"
        size="mini"
        placeholder="分类名称"
        style="width: 200px;margin-left: 10px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <el-button
        style="margin-left: 10px;"
        size="mini"
        class="filter-item"
        type="success"
        icon="el-icon-search"
        @click="handleFilter"
      >搜索</el-button>
      <el-button
        style="margin-left: 10px;"
        size="mini"
        class="filter-item"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
      >添加</el-button>

      <el-button
        style="margin-left: 10px;"
        size="mini"
        class="filter-item"
        type="danger"
        icon="el-icon-delete"
        @click="handleRemoves"
      >批量删除</el-button>
    </div>
    <el-table
      :key="0"
      v-loading="listLoading"
      :data="list"
      border
      row-key="id"
      style="width: 100%"
      height="400"
      size="mini"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column align="center" label="ID" width="80" type="index" />

      <el-table-column min-width="120px" align="center" label="分类名称">
        <template slot-scope="scope">
          <span>{{ scope.row.categoryName }}</span>
        </template>
      </el-table-column>

      <el-table-column min-width="300px" label="分类描述">
        <template slot-scope="{row}">
          <span>{{ row.description }}</span>
        </template>
      </el-table-column>

      <el-table-column width="180px" align="center" label="创建时间">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="操作">
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

    <el-dialog :title="dialogStatus" :visible.sync="dialogFormVisible">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="categoryObj"
        label-position="left"
        label-width="80px"
      >
        <el-form-item label="分类名称" prop="categoryName">
          <el-input v-model="categoryObj.categoryName" placeholder="分类名称" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input
            v-model="categoryObj.description"
            :autosize="{ minRows: 2, maxRows: 4}"
            type="textarea"
            placeholder="输入描述"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button
          type="primary"
          :loading="submitLoading"
          @click="dialogStatus==='create'?createData():updateData()"
        >Confirm</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { parseTime } from '@/utils'
import {
  categoryList,
  addCategory,
  updateCategory,
  getCategory,
  deleteCategory
} from '@/api/category'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
export default {
  name: 'Categorys',
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
        title: null
      },
      categoryObj: {
        id: null,
        categoryName: '',
        description: ''
      },
      rules: {
        categoryName: {
          required: true,
          message: '请输入分类名称',
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
      this.categoryObj = {
        id: null,
        categoryName: '',
        description: ''
      }
    },
    getList() {
      this.listLoading = true
      categoryList(this.listQuery)
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
      this.$confirm('确认删除该分类吗？', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          const data = [row.id]
          deleteCategory(data).then(() => {
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
          const tempData = Object.assign({}, this.categoryObj)
          addCategory(tempData)
            .then(() => {
              this.dialogFormVisible = false
              this.submitLoading = false
              this.$notify({
                title: 'Success',
                message: 'Created Successfully',
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
      console.log(row)
      getCategory(row.id).then(response => {
        this.categoryObj = response.data
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
          const tempData = Object.assign({}, this.categoryObj) // copy obj
          updateCategory(tempData['id'], tempData)
            .then(() => {
              this.submitLoading = false
              this.$notify({
                title: 'Success',
                message: 'Created Successfully',
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
          message: '先选择要删除的分类',
          type: 'warning'
        })
        return
      }

      this.$confirm('确认删除选择的分类吗？', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteCategory(this.selectList).then(() => {
          this.getList()
        })
      })
    }
  }
}
</script>
