<template>
  <el-card style="margin-bottom:20px;">
    <div slot="header" class="clearfix">
      <span>关于我</span>
    </div>

    <div class="user-profile">
      <div class="box-center">
        <pan-thumb :image="user.avatar" :height="'100px'" :width="'100px'" :hoverable="false">
          <div>Hello</div>
          {{ user.userName }}
        </pan-thumb>

      </div>
      <div class="box-center">
        <div class="user-name text-center">{{ user.userName }}</div>
      </div>
    </div>

    <div class="user-bio">
      <div class="user-education user-bio-section">
        <div class="user-bio-section-header">
          <el-link
            icon="el-icon-upload"
            :underline="false"
            type="primary"
            @click="uploadAvatar"
          >上传头像</el-link>
          <el-link
            v-if="!isEdit"
            icon="el-icon-upload"
            :underline="false"
            type="primary"
            @click="handleUpdate"
          >编辑</el-link>
        </div>
        <div v-if="isEdit" class="user-bio-section-body">
          <el-form
            ref="dataForm"
            :rules="rules"
            :model="temp"
            label-position="left"
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
            <el-form-item label="密码" prop="password">
              <el-input v-model="temp.password" type="password" placeholder="输入密码" />
            </el-form-item>

            <el-form-item label="确认密码" prop="confirmPassword">
              <el-input v-model="temp.confirmPassword" type="password" placeholder="再次输入密码" />
            </el-form-item>
            <el-form-item label="备注">
              <el-input
                v-if="isEdit"
                v-model="temp.remark"
                :autosize="{ minRows: 4, maxRows: 16}"
                type="textarea"
                maxlength="200"
                show-word-limit
                placeholder="备注"
              />
            </el-form-item>

            <el-form-item>
              <el-button @click="resetForm">取消</el-button>
              <el-button type="primary" :loading="submitLoading" @click="updateData()">确认</el-button>
            </el-form-item>
          </el-form>
        </div>
        <div v-else class="user-bio-section-body">
          <ul class="user-info">
            <li>
              <el-row>
                <el-col :span="8">
                  <svg-icon icon-class="user" />用户名称
                </el-col>
                <el-col :span="10">
                  <div class="user-right">{{ user.userName }}</div>
                </el-col>
              </el-row>
            </li>
            <li>
              <el-row>
                <el-col :span="8">
                  <svg-icon icon-class="email" />用户邮箱
                </el-col>
                <el-col :span="10">
                  <div class="user-right">{{ user.email }}</div>
                </el-col>
              </el-row>
            </li>
            <li>
              <el-row>
                <el-col :span="8">
                  <svg-icon icon-class="remark" />备注
                </el-col>
                <el-col :span="10">
                  <div class="user-right">{{ user.remark }}</div>
                </el-col>
              </el-row>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </el-card>
</template>

<script>
import { updateProfile } from '@/api/user'
import PanThumb from '@/components/PanThumb'
import { validEmail, validPassword } from '@/utils/validate'
export default {
  components: { PanThumb },
  filters: {},
  props: {
    user: {
      type: Object,
      default: () => {
        return {
          userName: '',
          email: '',
          avatar: '',
          roles: '',
          remark: ''
        }
      }
    }
  },
  data() {
    return {
      submitLoading: false,
      isEdit: false,
      temp: {
        email: '',
        userName: '',
        password: '',
        remark: ''
      },
      rules: {
        userName: [{ required: true, message: '用户名不能为空！' }],
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
        password: [
          {
            validator: (rule, value, callback) => {
              if (!value) {
                callback()
                return
              }
              if (!validPassword(value)) {
                callback(new Error('密码只能是数字字符下划线@&$ 6~18位'))
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
              if (!this.temp.password) {
                callback()
                return
              }
              if (value === '') {
                callback(new Error('请再次输入密码'))
              } else if (this.temp.password === value) {
                callback()
              } else {
                callback(new Error('密码不一致！'))
              }
            }
          }
        ]
      }
    }
  },
  methods: {
    uploadAvatar() {
      this.$emit('uploadAvatar')
    },
    resetForm: function() {
      this.isEdit = false
      this.temp = {
        userName: '',
        email: '',
        password: '',
        confirmPassword: '',
        remark: ''
      }
    },
    handleUpdate: function() {
      this.temp = Object.assign({}, this.user)
      this.isEdit = true
    },
    updateData: function() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          this.submitLoading = true
          const tempData = Object.assign({}, this.temp)
          updateProfile(tempData)
            .then(() => {
              this.submitLoading = false
              this.$notify({
                title: 'Success',
                message: '更新成功！',
                type: 'success',
                duration: 2000
              })
              this.resetForm()
              this.$store.dispatch('user/getInfo')
                .then(() => {
                  this.$emit('refresh')
                })
            })
            .catch(e => {
              this.submitLoading = false
            })
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.box-center {
  margin: 0 auto;
  display: table;
}
.text-muted {
  color: #777;
}
.user-profile {
  .user-name {
    font-weight: bold;
  }
  .box-center {
    padding-top: 10px;
  }
  .user-role {
    padding-top: 10px;
    font-weight: 400;
    font-size: 14px;
  }
  .box-social {
    padding-top: 30px;
    .el-table {
      border-top: 1px solid #dfe6ec;
    }
  }
  .user-follow {
    padding-top: 20px;
  }
}
.user-bio {
  color: #606266;
  span {
    padding-left: 4px;
  }
  .user-bio-section {
    font-size: 14px;
    padding: 15px 0;
    .user-bio-section-header {
      border-bottom: 1px solid #dfe6ec;
      padding-bottom: 10px;
      margin-bottom: 10px;
      font-weight: bold;
      .el-link {
        margin-left: 5px;
      }
    }
    .user-bio-section-body {
      max-height: 220px;
      overflow-y: scroll;
      .user-info {
        padding-left: 0px;
        list-style: none;
        li {
          min-height: 25px;
          padding: 12px 0;
          font-size: 1em;
          border-bottom: 1px solid #f0f3f4;
          .user-right {
            word-wrap:break-word;
            a {
              color: #317ef3;
            }
          }
        }
      }
    }
  }
}
</style>
