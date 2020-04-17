/* eslint-disable no-unused-vars */
<template>
  <div>
    <el-menu
      :default-active="activeIndex"
      class="d-flex"
      mode="horizontal"
      :router="true"
      type="flex"
    >
      <el-menu-item class="mr-auto">
        HDJ'S BLOG
      </el-menu-item>
      <el-menu-item index="/">
        {{ $t("header.home") }}
      </el-menu-item>
      <el-menu-item index="/archive">
        {{ $t("header.archive") }}
      </el-menu-item>
      <el-menu-item index="/article/about">
        {{ $t("header.about") }}
      </el-menu-item>
      <!-- <el-submenu index>
        <template slot="title">
          {{ $t("header.language") }}
        </template>
        <el-menu-item
          @click="toggleLang('zh')"
        >
          {{ $t("header.chinaese") }}
        </el-menu-item>
        <el-menu-item
          @click="toggleLang('en')"
        >
          {{ $t("header.english") }}
        </el-menu-item>
      </el-submenu> -->
    </el-menu>
  </div>
</template>

<script>
export default {
  data() {
    return {
      activeIndex: this.$route.path || "/"
    };
  },
  watch: {
    $route(to, from) {
      this.activeIndex = to.path
    }
  },
  methods: {
    toggleLang(lang) {
      if (lang === "zh") {
        localStorage.setItem("locale", "zh");
        this.$i18n.locale = localStorage.getItem("locale");
        this.$message({
          message: "切换为中文！",
          type: "success"
        });
      } else if (lang === "en") {
        localStorage.setItem("locale", "en");
        this.$i18n.locale = localStorage.getItem("locale");
        this.$message({
          message: "Switch to English!",
          type: "success"
        });
      }
    }
  }
};
</script>

<style scoped>
</style>