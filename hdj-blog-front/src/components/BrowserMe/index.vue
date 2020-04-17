<template>
  <div
    class="browse-more"
    :class="{loading: loading}"
  >
    <a @click="browseMore">
      <p
        class="text"
        v-show="!loading"
      >{{ tipStr }}</p>
      <div
        class="spinner"
        v-show="loading"
      >
        <div class="line1" />
        <div class="line2" />
        <div class="line3" />
        <div class="line4" />
        <div class="line5" />
      </div>
    </a>
  </div>
</template>

<script>
export default {
  props: {
    tipText: {
      type:String,
      default: "浏览更多"
    },
    noMoreData: {
       type:Boolean,
      default: false
    }
  },
  data() {
    return {
      loading: false
    };
  },
  computed: {
    tipStr: function() {
      const isNoMore = this.noMoreData;
      return isNoMore ? "暂无更多" : "浏览更多";
    }
  },
  methods: {
    browseMore() {
      if (this.noMoreData) {
        this.loading = false;
        return;
      }
      this.loading = true;
      this.$emit("browseMore");
      this.loading = false;
    },
    stopLoading(noMoreData) {
      this.loading = false;
      this.noMore = noMoreData;
    }
  }
};
</script>

<style lang="scss" scoped>
$color-main-primary: #409eff;
$border-radius: 4px;

.browse-more {
  width: 110px;
  padding: 5px;
  margin: 10px auto;
  border: 1px solid $color-main-primary;
  border-radius: $border-radius;
  &.loading {
    border: none;
  }
  a {
    display: block;
    position: relative;
    width: 100px;
    height: 30px;
    line-height: 30px;
    &:hover {
      cursor: pointer;
    }
    .text {
      position: absolute;
      margin: 0;
      width: 100%;
      height: 100%;
      top: 0;
      left: 0;
      text-align: center;
      font-size: 18px;
      color: $color-main-primary;
    }
    .spinner {
      width: 100px;
      height: 30px;
      margin: 0 auto;
      text-align: center;
      > div {
        display: inline-block;
        width: 6px;
        height: 100%;
        background: $color-main-primary;
        animation: strechdelay 1.2s infinite ease-in-out;
      }
      .line2 {
        animation-delay: -1.1s;
      }
      .line3 {
        animation-delay: -1s;
      }
      .line4 {
        animation-delay: -0.9s;
      }
      .line5 {
        animation-delay: -0.8s;
      }
    }
  }
}
@keyframes strechdelay {
  0%,
  40%,
  100% {
    -webkit-transform: scaleY(0.4);
  }
  20% {
    -webkit-transform: scaleY(1);
  }
}
</style>
