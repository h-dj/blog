<template>
  <div>
    <el-dialog
      :close-on-click-modal="false"
      :visible.sync="dialogFormVisible"
      title="新增友链"
      append-to-body
      width="480px"
      :before-close="resetForm"
    >
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="temp"
        label-position="left"
        label-width="80px"
        size="small"
      >
        <el-form-item label="昵称" prop="title">
          <el-input v-model="temp.title" placeholder="昵称" size="mini" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="temp.email" placeholder="邮箱" size="mini" />
        </el-form-item>

        <el-form-item label="链接" prop="url">
          <el-input v-model="temp.url" placeholder="链接" size="mini" />
        </el-form-item>

        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="temp.status" size="mini">
            <el-radio-button label="0">新建</el-radio-button>
            <el-radio-button label="1">审核通过</el-radio-button>
            <el-radio-button label="2">不通过</el-radio-button>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="描述">
          <el-input
            v-model="temp.remark"
            :autosize="{ minRows: 2, maxRows: 4}"
            type="textarea"
            placeholder="Please input"
            size="mini"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="resetForm">取消</el-button>
        <el-button size="mini" type="primary" :loading="submitLoading" @click="createData()">确认</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { addLink } from '@/api/friend-link'
export default {
  name: 'FriendLinkForm',
  data() {
    return {
      submitLoading: false,
      dialogFormVisible: false,
      temp: {
        id: null,
        status: 0,
        remark: null,
        url: null,
        email: null,
        title: null
      },
      rules: {
        url: [{ required: true, message: '路由地址必填' }],
        title: [{ required: true, message: '昵称必填' }]
      }
    }
  },
  methods: {
    resetForm() {
      this.dialogFormVisible = false
      this.temp = {
        id: null,
        status: 0,
        remark: null,
        url: null,
        email: null
      }
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
        this.$emit('reload')
      })
    },
    createData() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          this.submitLoading = true
          addLink(this.temp).then(r => {
            if (r.success) {
              this.$message('添加成功！')
              this.resetForm()
            }
            this.submitLoading = false
          })
            .catch(() => {
              this.submitLoading = false
            })
        }
      })
    }
  }
}
</script>
