<template>
  <div class="friend">
    <el-card class="box-card">
      <div
        slot="header"
        class="d-flex align-items-center"
      >
        <img
          class="card-icon"
          src="../assets/lianjie.png"
        >
        <span>{{ $t('friend.friend') }}</span>
        <el-button
          class="ml-auto"
          style="padding: 3px 0"
          type="text"
        >
          <router-link
            to="/apply"
            tag="span"
          >
            {{ $t('friend.exchange') }}
          </router-link>
        </el-button>
      </div>
      <div
        class="text item"
        v-for="(item,index) in links"
        :key="index"
      >
        <a
          :href="item.url"
          target="_blank"
        >{{ item.title }}</a>
      </div>
    </el-card>
  </div>
</template>

<script>
import { friendLinks } from "@/api/article";
export default {
  name: "Friend",
  data() {
    return {
      links: []
    };
  },
  mounted() {
    friendLinks().then(r => {
      if (r.success) {
        this.links = r.data;
      }
    });
  }
};
</script>

<style scoped>
.box-card .item a {
  text-decoration: none;
  /*color: #F56C6C;*/
}
.box-card span {
  font-weight: bold;
}
.card-icon {
  width: 20px;
  height: 20px;
  margin-right: 10px;
}
</style>