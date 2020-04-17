<template>
  <div class="tag">
    <el-card class="box-card">
      <div
        slot="header"
        class="d-flex align-items-center"
      >
        <img
          class="card-icon"
          src="../assets/biaoqian.png"
        >
        <span>{{ $t('tag.tag') }}</span>
      </div>
      <div class="text item">
        <el-tag
          v-for="(item,index) in tagList"
          :key="index"
          size="small"
          class="tag-item"
          :type="index | tagTypeFilter"
          effect="plain"
          @click="tag(item.tag)"
        >
          {{ item.tag }}[{{ item.num }}]
        </el-tag>
      </div>
    </el-card>
  </div>
</template>

<script>
import { tags } from "@/api/article";
export default {
  name: "Tag",
  data() {
    return {
      tagList: []
    };
  },
  filters: {
    tagTypeFilter(index) {
      const typeList = ["success", "danger", "warning", "info"]
      return typeList[index % typeList.length];
    }
  },
  mounted(){
      this.getTags()
  },
  methods: {
    tag(tagName) {
      this.$router.push({
        name: "archive",
        query: {
          tag: tagName
        }
      });
    },
    getTags() {
      tags().then(r => {
        if (r.success) {
          this.tagList = r.data;
        }
      });
    }
  }
};
</script>

<style scoped>
.box-card .item:hover {
  color: #409eff;
  cursor: pointer;
}

.box-card span {
  font-weight: bold;
}

.card-icon {
  width: 20px;
  height: 20px;
  margin-right: 10px;
}

.tag-item {
  margin-right: 10px;
  margin-bottom: 5px;
}
</style>