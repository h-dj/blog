<template>
  <div class="app">
    <div
      class="title_box"
      ref="pride_tab_fixed"
    >
      <f-header :class="{'navBarWrap':navBarFixed}" />
    </div>
    
    <el-row
      type="flex"
      justify="center"
      id="content"
    >
      <el-col
        :xs="20"
        :md="20"
        :style="{'minHeight':minHeight+'px'}"
      >
        <router-view />
      </el-col>
    </el-row>

    <el-tooltip
      placement="top"
      content="返回顶部"
    >
      <back-to-top
        :visibility-height="300"
        transition-name="fade"
      />
    </el-tooltip>
    
    <f-footer />
  </div>
</template>

<script>
import BackToTop from '@/components/BackToTop'
export default {
  name: "App",
  data() {
    return {
      minHeight: 0,
      navBarFixed: false
    };
  },
  components: {BackToTop},
  methods: {
    watchScroll() {
      //修复吸顶抖动问题
      //https://juejin.im/post/5caa0c2d51882543fa41e478#heading-11
      let scrollTop= this.$refs.pride_tab_fixed.getBoundingClientRect().top;
      if (scrollTop < 0) {
        this.navBarFixed = true;
      } else {
        this.navBarFixed = false;
      }
    }
  },
  mounted() {
    let that = this;
    that.minHeight = document.documentElement.clientHeight;
    window.addEventListener("scroll", that.watchScroll);
    window.onresize = function() {
      that.minHeight = document.documentElement.clientHeight;
    };
  }
};
</script>

<style scoped>
.app {
  font-family: "microsoft yahei";
}
#content {
  background-color: #f9f9f9;
  padding: 30px 0;
}
.navBarWrap {
  position: fixed;
  top: 0;
  z-index: 9999;
  width: 100%;
}
</style>
