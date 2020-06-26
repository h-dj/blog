/* eslint-disable no-unused-vars */
<template>
  <div>
    <el-menu
      :default-active="activeIndex"
      class="d-flex"
      mode="horizontal"
      type="flex"
    >
      <el-menu-item class="mr-auto">
        <el-link
          href="/"
          :underline="false"
          style="font-size:1.5em"
        >
          {{ title }}
        </el-link>
      </el-menu-item>
      <el-menu-item
        index="/"
        @click="goto('/')"
        class="hidden-md-and-down"
      >
        {{ $t("header.home") }}
      </el-menu-item>
      <el-menu-item
        index="/archive"
        @click="goto('/archive')"
        class="hidden-md-and-down"
      >
        {{ $t("header.archive") }}
      </el-menu-item>
      <el-menu-item
        index="/article/about"
        @click="goto('/article/about')"
        class="hidden-md-and-down"
      >
        {{ $t("header.about") }}
      </el-menu-item>

      <el-submenu
        index="#"
        class="hidden-md-and-down"
      >
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
      </el-submenu>

      <el-menu-item class="hidden-md-and-up">
        <i
          class="el-icon-s-fold "
          @click="drawer = true"
        />
      </el-menu-item>
    </el-menu>

    <el-drawer
      :visible.sync="drawer"
      size="60%"
    >
      <div>
        <el-menu
          :default-active="activeIndex"
        >
          <el-menu-item
            index="/"
            @click="goto('/')"
          >
            {{ $t("header.home") }}
          </el-menu-item>
          <el-menu-item
            index="/archive"
            @click="goto('/archive')"
          >
            {{ $t("header.archive") }}
          </el-menu-item>
          <el-menu-item
            index="/article/about"
            @click="goto('/article/about')"
          >
            {{ $t("header.about") }}
          </el-menu-item>

          <el-submenu
            index="#"
          >
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
          </el-submenu>
        </el-menu>
      </div>
    </el-drawer>
  </div>
</template>

<script>
const defaultSettings = require('@/settings.js')
export default {
  data() {
    return {
      activeIndex: this.$route.path || "/",
      title:defaultSettings.title,
      drawer:false
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
      } else if (lang === "en") {
        localStorage.setItem("locale", "en");
        this.$i18n.locale = localStorage.getItem("locale");
      }
    },
    goto(url){
      if(this.activeIndex != url){
        this.$router.push(url)
      }
    }
  }
};
</script>

<style scoped>
</style>