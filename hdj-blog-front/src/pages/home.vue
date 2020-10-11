<template>
  <div class="home">
    <el-row id="artList">
      <el-col
        :sm="22"
        :md="18"
        :lg="18"
        :xl="18"
      >
        <el-alert
          v-show="search.is_search"
          class="search-hit"
          type="success"
          :closable="false"
        >
          搜索结果：{{ total }} 条
        </el-alert>
        <el-alert
          v-show="listQuery.categoryId !=null"
          class="search-hit"
          type="success"
          :closable="false"
        >
          当前分类：{{ currentCategory }}
        </el-alert>
        <el-row
          v-for="(item, index) in list"
          :key="index"
          class="art-item"
        >
          <el-card shadow="hover">
            <h5>
              <router-link
                :to="`/article/${item.id}`"
                tag="span"
                class="art-title"
              >
                <span v-html="item.title" />
              </router-link>
            </h5>
            <el-row class="art-info">
              <el-col
                :sm="24"
                :md="12"
              >
                <div class="art-time">
                  <i class="el-icon-time" />
                  ：{{ item.publishTime | formatTime }}
                </div>
              </el-col>
              <el-col
                :sm="24"
                :md="12"
              >
                <div
                  class="d-flex align-items-center"
                  v-if="item.tags"
                >
                  <img
                    class="tag"
                    src="../assets/tag.png"
                  >：
                  <el-tag
                    style="margin-left:5px"
                    size="mini"
                    :key="i"
                    v-for="(tag, i) in item.tags.split(',')"
                  >
                    {{ tag }}
                  </el-tag>
                </div>
              </el-col>
            </el-row>
            <el-row class="art-body">
              <div
                class="side-img hidden-sm-and-down"
                v-if="item.cover"
              >
                <img
                  class="art-banner"
                  :src="item.cover+'?imageView2/2/w/270/h/150'"
                >
              </div>
              <div class="side-abstract">
                <div class="art-abstract">
                  <span v-html="item.description" />
                </div>
                <div class="art-more">
                  <router-link
                    :to="`/article/${item.slug}`"
                    tag="span"
                  >
                    <el-button plain>
                      {{ $t('home.readMore') }}
                    </el-button>
                  </router-link>
                  <div class="view">
                    <i class="el-icon-view" />
                    {{ item.readNum }}
                  </div>
                </div>
              </div>
            </el-row>
          </el-card>
          <img
            v-if="item.top"
            class="star"
            src="../assets/star.png"
          >
        </el-row>

        <div class="block pagination">
          <browser-me
            :no-more-data="totalPage <= currPage"
            @browseMore="browseMore"
          />
          <el-divider />
        </div>
      </el-col>
      <el-col
        :md="6"
        :lg="6"
        :xl="6"
        id="side"
      >
        <div class="item hidden-sm-and-down">
          <search @search="handleSearch" />
        </div>
        <div class="item">
          <tag />
        </div>
        <div class="item">
          <friend />
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { fetchList, searchArticles } from '@/api/article';
import { formatTime } from '@/utils';
import friend from '@/components/friend';
import tag from '@/components/tag';
import Search from '@/components/Search';
import BrowserMe from '@/components/BrowserMe';
export default {
  name: 'Home',
  components: {
    friend,
    tag,
    Search,
    BrowserMe,
  },
  filters: {
    formatTime(time) {
      return formatTime(time, '{y}-{m}-{d}');
    },
  },
  data() {
    return {
      list: [],
      total: 0,
      totalPage: 0,
      currPage: 0,
      listLoading: true,
      currentCategory:'',
      listQuery: {
        page: 1,
        limit: 10,
        title: null,
        tag:null,
        categoryId:null
      },
      search: {
        keyword: '',
        is_search: false,
        page: 1,
        pageSize: 10,
      },
      tagWall: [],
    };
  },
  watch: {
    $route() {
       this.getList();
    }
  },
  created() {
    this.getList();
  },
  computed: {
    tagList: function(tagStr) {
      if (tagStr) {
        return tagStr.splite(',');
      }
      return [];
    },
  },
  methods: {
    handleSearch(keyword) {
      if (keyword) {
        this.search.keyword = keyword;
        const query = {
          keyword: this.search.keyword,
          page: this.search.page,
          pageSize: this.search.pageSize,
        };

        searchArticles(query).then(r => {
          if (r.success) {
            this.search.is_search = true;
            this.list = r.data.list;
            this.totalPage = r.data.totalPage;
            this.currPage = r.data.currPage;
            this.total = r.data.totalCount;
          }
        });
      }else{
        window.location.reload()
      }
    },
    getList() {
      this.listQuery.categoryId = this.$route.query.categoryId || null
      this.currentCategory = this.$route.query.categoryName || null
      
      this.listLoading = true;
      fetchList(this.listQuery)
        .then(resp => {
          this.list = resp.data.list;
          this.totalPage = resp.data.totalPage;
          this.currPage = resp.data.currPage;
          this.listLoading = false;
        })
        .catch(() => {
          this.listLoading = false;
        });
    },
    browseMore() {
      if (this.totalPage <= this.currPage) {
        return;
      }
      this.listQuery.page++;
      const query = Object.assign({}, this.listQuery);
      fetchList(query).then(resp => {
        if (resp.success && resp.data.list) {
          this.list.push(...resp.data.list);
          this.currPage = resp.data.currPage;
        }
      });
    },
  },
};
</script>
<style>
#artList .hdj-hl{
  color: red;
}
</style>

<style scoped>
#side .item {
  margin-bottom: 30px;
  margin-left: 5px;
}

.art-item {
  margin-bottom: 30px;
  position: relative;
}

.art-item .star {
  width: 60px;
  height: 60px;
  position: absolute;
  top: 0;
  right: 0;
}

img.tag {
  width: 16px;
  height: 16px;
}

.art-title {
  border-left: 3px solid #f56c6c;
  padding-left: 5px;
  cursor: pointer;
}

.art-title:hover {
  padding-left: 10px;
  color: #409eff;
}

.art-time {
  margin-right: 20px;
}

.art-body {
  display: flex;
  padding: 10px 0;
}

.side-img {
  height: 150px;
  width: 270px;
  overflow: hidden;
  margin-right: 10px;
}

img.art-banner {
  width: 100%;
  height: 100%;
  transition: all 0.6s;
}

img.art-banner:hover {
  transform: scale(1.4);
}

.side-abstract {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.art-abstract {
  text-indent: 2em;
  color: #aaa;
  word-break: break-all;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 4;
  overflow: hidden;
}

.art-more {
  height: 40px;
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
}

.art-more .view {
  color: #aaa;
}
h5 {
  font-size: 18px;
}
.pagination {
  background-color: #f9f9f9;
  margin-bottom: 30px;
}

.search-hit {
  margin-bottom: 10px;
}
</style>
