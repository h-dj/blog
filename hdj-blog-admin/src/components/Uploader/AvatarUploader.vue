<template>
  <div>
    <el-upload
      class="avatar-uploader"
      :action="url"
      :show-file-list="false"
      :on-success="handleAvatarSuccess"
      :before-upload="beforeAvatarUpload"
      :headers="headers"
    >
      <img v-if="imgUrl" :src="imgUrl +'?imageView2/1/w/178/h/178'" class="avatar">
      <i v-else class="el-icon-plus avatar-uploader-icon" />
    </el-upload>
  </div>
</template>

<script>
import { getuploadImageURL } from '@/api/upload'
import { getToken } from '@/utils/auth'
export default {
  name: 'AvatarUploader',
  model: {
    prop: 'imgSrc',
    event: 'onChange'
  },
  props: {
    imgSrc: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      url: getuploadImageURL(),
      imgUrl: this.imgSrc,
      dialogVisible: false,
      headers: {
        'x-auth-token': getToken()
      },
      accepts: ['image/png', 'image/gif', 'image/jpeg', 'image,jpg'],
      max: 2 * 1024 * 1024
    }
  },
  methods: {
    handleAvatarSuccess(res, file) {
      this.imgUrl = res.data.url
      this.$emit('onChange', this.imgUrl)
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

<style lang="scss" scope>
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
