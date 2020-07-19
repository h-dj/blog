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
        index="#language"
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

      <el-submenu
        index="#category"
        class="hidden-md-and-down"
      >
        <template slot="title">
          {{ $t("header.category") }}
        </template>
        <el-menu-item
          @click="goto('/home')"
        >
          全部
        </el-menu-item>
        <el-menu-item
          :key="index"
          v-for="(item,index) in categoryList"
          @click="goto(`/?categoryId=${item.id}&categoryName=${item.categoryName}`)"
        >
          {{ item.categoryName }}
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
            index="#language"
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

          <el-submenu
            index="#category"
          >
            <template slot="title">
              {{ $t("header.category") }}
            </template>
            <el-menu-item
              @click="goto('/home')"
            >
              全部
            </el-menu-item>
            <el-menu-item
              :key="index"
              v-for="(item,index) in categoryList"
              @click="goto(`/?categoryId=${item.id}&categoryName=${item.categoryName}`)"
            >
              {{ item.categoryName }}
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </div>
    </el-drawer>
  </div>
</template>

<script>
const defaultSettings = require('@/settings.js')
import {categorys} from '@/api/article'

export default {
  data() {
    return {
      activeIndex: this.$route.path || "/",
      title:defaultSettings.title,
      drawer:false,
      categoryList:[]
    };
  },
  created(){
    this.getCategoryList()
  },
  watch: {
    $route(to) {
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
    getCategoryList(){
        categorys()
        .then(res=>{
          this.categoryList = res.data
        })
    },
    goto(url){
      if(this.activeIndex != url){
        this.$router.push(url)
      }
    },
  }
};
</script>

<style scoped>
</style>