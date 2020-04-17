<template>
  <div class="app-container">
    <div>
      <el-row>
        <el-col :md="6" :xs="{span: 24, offset: 0}">
          <user-card ref="user-card" :user="user" @refresh="getUser" @uploadAvatar="uploadAvatar" />
        </el-col>
        <el-col :md="{span: 16, offset: 1}" :xs="{span: 24, offset: 0}">
          <el-card>
            <el-tabs v-model="activeTab">
              <el-tab-pane label="通知" name="message">
                <message />
              </el-tab-pane>
            </el-tabs>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <drag-uploader ref="dragUploader" @onSuccess="uploadSuccess" />
  </div>
</template>

<script>
import store from '@/store'
import Message from './components/Message'
import UserCard from './components/UserCard'
import DragUploader from '@/components/Uploader/DragUploader'
import { uploadUserAvatar } from '@/api/user'
export default {
  name: 'Profile',
  components: { Message, UserCard, DragUploader },
  data() {
    return {
      user: {
        userName: '',
        email: '',
        avatar: '',
        remark: ''
      },
      activeTab: 'message'
    }
  },
  created() {
    this.getUser()
  },
  methods: {
    uploadSuccess(response, file, fileList) {
      if (response && response.success) {
        uploadUserAvatar(response.data.url)
          .then((res) => {
            this.$message('上传成功!')
            this.$refs.dragUploader.close()
            this.getUser()
          })
      }
    },
    uploadAvatar() {
      this.$refs.dragUploader.show()
    },
    getUser() {
      store.dispatch('user/getInfo').then((data) => {
        this.user = {
          userName: data.userName || '',
          email: data.email || '',
          avatar: data.avatar || '',
          remark: data.remark || ''
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.message{
  margin-left: 10px;
}
</style>
