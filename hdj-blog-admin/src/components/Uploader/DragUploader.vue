<template>
  <div>
    <el-dialog
      :visible.sync="centerDialogVisible"
      width="30%"
      center
    >
      <el-upload
        drag
        :action="url"
        :multiple="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload"
        :headers="headers"
      >
        <i class="el-icon-upload" />
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div slot="tip" class="el-upload__tip">
          <p>只能上传['image/png', 'image/gif', 'image/jpeg', 'image,jpg']文件</p>
          <p>且不超过2M</p>
        </div>
      </el-upload>
    </el-dialog>
  </div>
</template>

<script>
import { getuploadImageURL } from '@/api/upload'
import { getToken } from '@/utils/auth'
export default {
  name: 'DragUploader',
  data() {
    return {
      centerDialogVisible: false,
      url: getuploadImageURL(),
      imgUrl: this.imgSrc,
      headers: {
        'x-auth-token': getToken()
      },
      accepts: ['image/png', 'image/gif', 'image/jpeg', 'image,jpg'],
      max: 2 * 1024 * 1024
    }
  },
  methods: {
    show() {
      this.centerDialogVisible = true
    },
    close() {
      this.centerDialogVisible = false
    },
    handleAvatarSuccess(response, file, fileList) {
      this.$emit('onSuccess', response, file, fileList)
    },
    beforeAvatarUpload(file) {
      if (!this.accepts.includes(file.type.toLowerCase())) {
        this.$message.error('不支持该文件类型！ ' + file.type)
        return false
      }
      const size = file.size
      if (size > this.max) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
        return false
      }
      return true
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
