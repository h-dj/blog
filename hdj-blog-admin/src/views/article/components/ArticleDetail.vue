<template>
  <div class="articleForm">
    <el-form
      ref="articleForm"
      element-loading-text="正在提交..."
      :model="article"
      :rules="rules"
      size="mini"
      label-position="top"
    >
      <el-form-item prop="title">
        <el-col :md="12">
          <el-input v-model="article.title" placeholder="博文标题" clearable />
        </el-col>
      </el-form-item>

      <el-form-item>
        <markdown-editor
          ref="vditor"
          v-model="article.content"
          :auto-save="true"
          @autoSubmit="saveArticleHandler"
          @submit="saveArticleHandler"
          @openSetting="openSetting"
        />
      </el-form-item>

      <el-drawer
        ref="formDrawer"
        :show-close="false"
        :visible.sync="dialog"
        direction="rtl"
        :with-header="false"
        custom-class="formDrawer"
      >
        <div class="form-body">
          <el-form-item label="封面" prop="cover">
            <avatar-uploader v-model="article.cover" />
          </el-form-item>
          <el-form-item label="slug" prop="slug">
            <el-col :md="20">
              <el-input v-model="article.slug" clearable placeholder="默认采用文章id" />
            </el-col>
          </el-form-item>
          <el-form-item label="作者" prop="authorName">
            <el-col :md="20">
              <el-input v-model="article.authorName" clearable placeholder="文章作者" />
            </el-col>
          </el-form-item>
          <el-form-item label="分类">
            <el-col :md="20">
              <el-select
                v-model="article.categoryId"
                style="width: 100%"
                filterable
                default-first-option
                placeholder="请选择文章分类"
              >
                <el-option
                  v-for="item in categoryList"
                  :key="item.id"
                  :label="item.categoryName"
                  :value="item.id"
                />
              </el-select>
            </el-col>
          </el-form-item>
          <el-form-item label="标签">
            <el-col :md="20">
              <el-select
                v-model="tagListSelect"
                style="width: 100%"
                multiple
                allow-create
                filterable
                default-first-option
                placeholder="请选择文章标签"
                @change="filterTagList"
              >
                <el-option
                  v-for="item in tagList"
                  :key="item.id"
                  :label="item.tagName"
                  :value="item.id"
                />
              </el-select>
            </el-col>
          </el-form-item>
          <el-form-item label="推荐">
            <el-radio-group v-model="article.recommend" size="mini">
              <el-radio-button :label="true">开启</el-radio-button>
              <el-radio-button :label="false">关闭</el-radio-button>
            </el-radio-group>
          </el-form-item>

          <el-form-item label="置顶">
            <el-radio-group v-model="article.top" size="mini">
              <el-radio-button :label="true">开启</el-radio-button>
              <el-radio-button :label="false">关闭</el-radio-button>
            </el-radio-group>
          </el-form-item>

          <el-form-item label="状态">
            <el-radio-group v-model="article.status" size="mini">
              <el-radio-button :label="1">发布</el-radio-button>
              <el-radio-button :label="0">草稿</el-radio-button>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="摘要" prop="description">
            <el-col :md="20">
              <el-input
                v-model="article.description"
                type="textarea"
                placeholder="摘要, 不填写会自动摘取"
                clearable
                :rows="8"
                maxlength="200"
                show-word-limit
              />
            </el-col>
          </el-form-item>
        </div>
      </el-drawer>
    </el-form>
  </div>
</template>

<script>
import { detail, saveArticle, updateArticle } from '@/api/article'
import { tagSelectList } from '@/api/tag'
import { categorySelectList } from '@/api/category'
import MarkdownEditor from '@/components/MarkdownEditor'
import AvatarUploader from '@/components/Uploader/AvatarUploader'
export default {
  name: 'ArticleDetail',
  components: {
    MarkdownEditor,
    AvatarUploader
  },
  props: {
    isEdit: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      dialog: false,
      submitLoading: false,
      article: {
        id: '',
        title: '',
        description: '',
        content: null,
        tags: '',
        authorId: 0,
        readNum: 0,
        commentNum: 0,
        authorName: 'author',
        status: 0,
        allowComment: true,
        allowFeed: true,
        recommend: false,
        categoryId: null,
        cover: '',
        top: false,
        slug: '',
        tagList: []
      },
      tagList: [],
      tagListSelect: [],
      categoryList: [],
      url: '',
      file: [],
      rules: {
        title: {
          required: true,
          message: '请输入博文标题',
          trigger: 'change'
        },
        authorName: {
          required: true,
          message: '请输入博文作者',
          trigger: 'change'
        },
        content: {
          required: true,
          message: '请输入博文内容',
          trigger: 'change'
        }
      },
      defaultForm: {
        top: false,
        id: '',
        title: '',
        description: '',
        content: null,
        tags: '',
        authorId: 0,
        readNum: 0,
        commentNum: 0,
        authorName: 'author',
        status: 0,
        allowComment: true,
        allowFeed: true,
        recommend: false,
        cover: '',
        path: '',
        tagList: [],
        slug: ''
      }
    }
  },
  watch: {
    'article.content': function(val) {
      if (val) {
        this.article.description = val
          .replace(/#*.*#/g, '')
          .replace(/[^a-zA-Z0-9\u4e00-\u9fa5 \.、",，:()]/g, '')
          .substring(0, 200)
      }
    }
  },
  created() {
    if (this.isEdit) {
      const articleId = this.$route.params.id
      detail(articleId).then(r => {
        if (r.success) {
          this.article = Object.assign({}, r.data)
          this.article.tagList.forEach(v => {
            this.tagListSelect.push(v.id)
          })
          this.$refs.vditor.setValue(this.article.content)
        }
      })
    }
    tagSelectList().then(r => {
      if (r.success) {
        this.tagList = r.data || []
      }
    })

    categorySelectList().then(r => {
      if (r.success) {
        this.categoryList = r.data || []
      }
    })
  },
  methods: {
    openSetting() {
      this.dialog = true
    },
    reset() {
      this.article = this.defaultForm
    },
    filterTagList(selectValueList) {
      // 过滤标签
      const tagList = []
      if (!selectValueList) {
        this.article.tagListSelect = tagList
        return
      }
      selectValueList.forEach(value => {
        let isInput = true
        const len = this.tagList.length
        for (let i = 0; i < len; i++) {
          const tag = this.tagList[i]
          if (tag.id === value || value.id) {
            isInput = false
            tagList.push({ id: tag.id, tagName: tag.tagName })
          }
        }
        if (isInput) {
          tagList.push({ tagName: value })
        }
      })
      this.article.tagList = tagList
    },
    // 保存文章
    saveArticleHandler() {
      if (this.submitLoading) {
        this.$message('正在提交...')
        return
      }
      if (!this.article.content) {
        this.$message('文章内容不能为空！')
        return
      }
      if (this.article.id) {
        this.updateArticleHandler()
      } else {
        this.$refs['articleForm'].validate(valid => {
          if (valid) {
            this.submitLoading = true
            saveArticle(this.article)
              .then(r => {
                this.submitLoading = false
                this.$notify({
                  title: 'Success',
                  message: '创建成功',
                  type: 'success',
                  duration: 2000
                })
                this.article.id = r.data
                this.$router.push('/article/edit/' + r.data)
              })
              .catch(r => {
                this.submitLoading = false
                console.error(r)
              })
          } else {
            this.openSetting()
            return false
          }
        })
      }
    },
    updateArticleHandler() {
      if (this.submitLoading) {
        this.$message('正在提交...')
        return
      }
      if (!this.article.content) {
        this.$message('文章内容不能为空！')
        return
      }
      this.$refs['articleForm'].validate(valid => {
        if (valid) {
          this.submitLoading = true
          updateArticle(this.article.id, this.article)
            .then(r => {
              this.submitLoading = false
              this.$notify({
                title: 'Success',
                message: '更新成功',
                type: 'success',
                duration: 2000
              })
            })
            .catch(r => {
              this.submitLoading = false
            })
        } else {
          this.openSetting()
          return false
        }
      })
    }
  }
}
</script>

<style lang="scss" scope>
.articleForm {
  padding: 10px;
  .form-body {
    height: calc(100vh);
    padding: 10px;
    overflow-y: scroll;
  }
}

@media screen and (max-width: 992px) {
  .formDrawer {
    width: 48% !important;
  }
}
@media screen and (max-width: 768px) {
  .formDrawer {
    width: 60% !important;
  }
}
</style>
