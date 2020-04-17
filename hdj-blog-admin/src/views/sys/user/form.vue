<template>
  <div>
    <el-dialog
      :title="isAdd?'添加用户':'修改用户'"
      :visible.sync="dialogFormVisible"
      :before-close="resetForm"
      :close-on-click-modal="false"
      append-to-body
      width="450px"
    >
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="temp"
        label-position="left"
        label-width="80px"
        size="small"
      >
        <el-form-item label="昵称" prop="userName">
          <el-input v-model="temp.userName" placeholder="输入昵称" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input
            v-model="temp.email"
            placeholder="输入邮箱"
          />
        </el-form-item>

        <el-form-item v-if="isAdd" label="密码" prop="password">
          <el-input v-model="temp.password" type="password" placeholder="输入密码" />
        </el-form-item>

        <el-form-item v-if="isAdd" label="确认密码" prop="confirmPassword">
          <el-input v-model="temp.confirmPassword" type="password" placeholder="再次输入密码" />
        </el-form-item>

        <el-form-item label="状态" prop="enable">
          <el-radio-group v-model="temp.enable" size="small">
            <el-radio-button :label="true">true</el-radio-button>
            <el-radio-button :label="false">false</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="temp.roleIds" multiple placeholder="请选择角色">
            <el-option
              v-for="item in roleList"
              :key="item.id"
              :label="item.roleName"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input
            v-model="temp.remark"
            :autosize="{ minRows: 2, maxRows: 4}"
            type="textarea"
            placeholder="备注"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm">取消</el-button>
        <el-button type="primary" :loading="submitLoading" @click="isAdd?createData():updateData()">确认</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { addUser, updateUser, getUser } from '@/api/user'
import { validEmail, validPassword } from '@/utils/validate'
import { fetchRoleListAll } from '@/api/role'
export default {
  name: 'UserForm',
  props: {
    isAdd: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      roleList: [],
      submitLoading: false,
      dialogFormVisible: false,
      temp: {
        id: undefined,
        avatar: '',
        email: '',
        userName: '',
        enable: true,
        remark: '',
        roleIds: []
      },
      rules: {
        email: [
          {
            required: true,
            validator: (rule, value, callback) => {
              if (validEmail(value)) {
                callback()
              } else {
                callback(new Error('请输入正确的邮箱格式'))
              }
            },
            trigger: 'blur'
          }
        ],
        userName: [
          {
            required: true,
            max: 10,
            message: '用户名最多 10 个字符',
            trigger: 'blur'
          }
        ],
        password: [
          {
            validator: (rule, value, callback) => {
              if (!this.isAdd) {
                callback()
                return
              }

              if (!value) {
                callback(new Error('请输入密码'))
              } else if (!validPassword(value)) {
                callback(new Error('密码只能是数字字符下划线6~8位'))
              } else {
                if (this.temp.confirmPassword) {
                  this.$refs.dataForm.validateField('confirmPassword')
                }
                callback()
              }
            }
          }
        ],
        confirmPassword: [
          {
            validator: (rule, value, callback) => {
              if (!this.isAdd) {
                callback()
                return
              }
              if (value === '') {
                callback(new Error('请再次输入密码'))
              } else if (this.temp.password === value) {
                callback()
              } else {
                console.log(this.temp.password, value, this.temp.confirmPassword)
                callback(new Error('密码不一致！'))
              }
            }
          }
        ]
      }
    }
  },
  methods: {
    getRoleList() {
      fetchRoleListAll().then(response => {
        this.roleList = response.data
        console.log(this.roleList)
      })
    },
    resetForm() {
      this.dialogFormVisible = false
      this.temp = {
        id: null,
        avatar: '',
        email: '',
        userName: '',
        enable: true,
        remark: '',
        roleIds: []
      }
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    getUserInfo(userId) {
      getUser(userId)
        .then(response => {
          this.temp = response.data
        })
      this.getRoleList()
    },
    createData() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          this.submitLoading = true
          const tempData = Object.assign({}, this.temp)
          addUser(tempData)
            .then(() => {
              this.submitLoading = false
              this.$notify({
                title: 'Success',
                message: '创建成功！',
                type: 'success',
                duration: 2000
              })
              this.$emit('reload')
              this.resetForm()
            }).catch((e) => {
              this.submitLoading = false
            })
        }
      })
    },
    updateData() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          this.submitLoading = true
          this.tempData = Object.assign({}, this.temp) // copy obj
          this.tempData['account'] = null
          updateUser(this.tempData['id'], this.tempData)
            .then(() => {
              this.submitLoading = false
              this.$notify({
                title: 'Success',
                message: '更新成功！',
                type: 'success',
                duration: 2000
              })
              this.$emit('reload')
              this.resetForm()
            }).catch((e) => {
              this.submitLoading = false
            })
        }
      })
    },
    normalizer(node) {
      return {
        id: node.id,
        label: node.menuName,
        children: node.children
      }
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
