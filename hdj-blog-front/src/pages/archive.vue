<template>
  <div class="archive">
    <div>{{ archiveName($t('header.archive')) }}：{{ articleCount }}{{ $t('archive.article') }}</div>
    <el-timeline>
      <el-timeline-item
        size="large"
        v-for="(activity, index) in activities"
        :key="index"
        :color="activity.color"
        :timestamp="activity.year + ' 年【'+activity.count +'】篇'"
        placement="top"
        @mouseenter="hoverLine(activity)"
      >
        <el-timeline>
          <el-timeline-item
            size="normal"
            v-for="(m, mIndex) in activity.months"
            :key="mIndex"
            :timestamp="m.month + ' 月【'+m.count +'】篇'"
            placement="top"
            @mouseenter="hoverLine(activity)"
          >
            <p
              v-for="(p,pIndex) in m.posts"
              :key="pIndex"
            >
              <el-link
                :underline="false"
                :href="`/article/${p.slug}`"
              >
                {{ p.createTime | formatTime }} {{ p.title }}
              </el-link>
            </p>
          </el-timeline-item>
        </el-timeline>
      </el-timeline-item>
    </el-timeline>
  </div>
</template>

<script>
import { parseTime } from "@/utils";
import { archives } from "@/api/article";
export default {
  name: "Archive",
  data() {
    return {
      activities: [],
      query: {
        tag: null
      }
    };
  },
  filters: {
    formatTime(time) {
      return parseTime(time, "{y}-{m}-{d}");
    }
  },
  computed: {
    articleCount: function() {
      let sum = 0;
      this.activities.forEach(element => {
        sum += element.count;
      });
      return sum;
    }
  },
  mounted() {
    this.query = this.$route.query;
    this.getArchive();
  },
  methods: {
    archiveName: function(name) {
      if (this.$route.params.name) {
        return this.$route.params.name;
      }
      if (this.$route.query.tag) {
        return name + " " + this.$route.query.tag;
      }
      return name;
    },
    hoverLine(activity) {
      activity.color = "#409eff";
    },
    getArchive() {
      archives(this.query).then(r => {
        if (r && r.success) {
          this.activities = r.data;
        }
      });
    }
  }
};
</script>

<style scoped>
.archive .el-timeline {
  margin: 20px auto !important;
}
</style>