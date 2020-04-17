<template>
  <div>
    <el-row
      class="main"
      type="flex"
      justify="center"
    >
      <el-col
        :sm="24"
        :md="22"
        :lg="20"
        :xl="18"
      >
        <div
          class="cover"
          v-if="this.article.cover"
        >
          <el-image
            style="width: 100%; height: 100%"
            :src="this.article.cover"
            fit="cover"
          />
        </div>
        <el-card class="box-card article-body">
          <div
            slot="header"
            class="clearfix"
          >
            <h2 class="text-center">
              <strong>{{ this.article.title }}</strong>
            </h2>
            <!-- 描述：文章信息 -->
            <div class="text-center timeAndView">
              <span class="article-time">
                <i class="el-icon-time" />
                发表于：
                <span>{{ this.article.publishTime | formatTime }}</span>
              </span>
              &nbsp;|&nbsp;
              <span class="article-views">
                <i class="el-icon-view" />
                阅读量：
                <span>{{ this.article.readNum || 0 }}</span>
              </span>
            </div>
          </div>
          <div
            id="articleContent"
            v-html="this.article.contentFormat"
          />
        </el-card>
        <div id="statement">
          <div class="item">
            {{ $t('article.author') }}：{{ this.statement.author }}
          </div>
          <div class="item">
            {{ $t('article.originalLink') }}：
            <a
              :href="this.statement.originalLink"
              target="_blank"
            >{{ this.statement.originalLink }}</a>
          </div>
          <div class="item">
            {{ $t('article.copyright') }}：{{ this.statement.copyright }}
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import VditorPreview from "vditor/dist/method.min";
import "vditor/dist/index.classic.css";
import { articleInfo } from "@/api/article";
import { formatTime } from "@/utils";
const defaultSettings = require("@/settings.js");

export default {
  name: "Article",

  data() {
    return {
      article: {},
      statement: defaultSettings.statement,
      fullscreenLoading: false
    };
  },
  beforeRouteUpdate(to, from, next) {
    //组件复用，数据更新
    this.fetchArticleInfo(to.params.id);
    next();
  },
  filters: {
     formatTime(time) {
      return formatTime(time, "{y}-{m}-{d}");
    }
  },

  mounted() {
    this.fetchArticleInfo(this.$route.params.id);
  },
  methods: {
    fetchArticleInfo(id) {
      articleInfo(id).then(r => {
        if (r.success) {
          this.article = r.data;
          this.preview(this.article.content);
        }
      });
    },
    preview(content) {
      const artcleDiv = document.querySelector("#articleContent");
      VditorPreview.preview(artcleDiv, content);
    }
  }
};
</script>

<style scoped>
.cover {
  width: 100%;
  height: 300px;
  margin-bottom: 10px;
  overflow: hidden;
}

.el-image {
  transition: all 0.6s;
}
.el-image:hover {
  transform: scale(1.4);
}

.article-body {
  margin-bottom: 10px;
}

#artcle-info .timeAndView {
  padding: 20px;
  line-height: 30px;
  font-size: 16px;
  color: #ffffff;
}

#statement {
  border-left: 3px solid #f56c6c;
  padding: 20px;
  background-color: #ebeef5;
}
</style>