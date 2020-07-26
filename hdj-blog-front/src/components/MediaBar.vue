<template>
  <div class="box-card">
    <el-row>
      <el-col
        :span="icons.length % 24"
        v-for="(s,i) in icons"
        :key="i"
      >
        <el-link
          target="_blank"
          :href="s.link"
        >
          <svg-icon
            :icon-class="s.icon"
            class-name="svg"
          />
        </el-link>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "MediaBar",
  data() {
    return {
      icons: [
        {
          icon: "yuque",
          link: "https://www.yuque.com/h_dj"
        },
        {
          icon: "juejin",
          link: "https://juejin.im/user/58aac8de2f301e006c36160a"
        },
        {
          icon: "GitHub",
          link: "https://github.com/h-dj"
        }
      ]
    };
  }
};
</script>
<style scoped>
.box-card{
  padding: 0 auto;
}
.box-card .svg {
  width: 2em;
  height: 2em;
  margin-bottom: 5px;
}
</style>
