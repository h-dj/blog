<template>
  <div>
    <el-card class="box-card">
      <el-input
        placeholder="本站搜索"
        v-model="keyword"
        class="input-with-select"
        @keyup.enter.native="handleSearch"
      />
    </el-card>
  </div>
</template>

<script>
export default {
  name: "Search",
  data() {
    return {
      keyword: ""
    };
  },
  methods:{
    handleSearch(){
        this.$emit("search",this.keyword)
    }
  }
};
</script>

<style lang="scss" scoped>
</style>