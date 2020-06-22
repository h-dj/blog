<template>
  <div class="f-footer">
    <el-main class="main">
      <el-row>
        <el-col
          :span="10"
          class="item-col"
        >
          <div class>
            <a
              href="https://github.com/h-dj"
              class="out-link"
              target="_blank"
            >{{ $t('footer.github') }}</a>
          </div>
          <div class>
            <a
              href="https://juejin.im/user/58aac8de2f301e006c36160a"
              class="out-link"
              target="_blank"
            >{{ $t('footer.juejin') }}</a>
          </div>
        </el-col>
        <el-col
          :span="14"
          class="item-col weside"
        >
          <div class="item">
            <i class="el-icon-caret-right" />
            1432517356@qq.com
          </div>
          <div class="item">
            <i class="el-icon-caret-right" />
            <a
              href="http://www.beian.miit.gov.cn"
              class="out-link"
              target="_blank"
            >粤ICP备20053312号</a>
          </div>
        </el-col>
      </el-row>
    </el-main>
  </div>
</template>

<script>

export default {
  name: "Footer"
};
</script>

<style scoped>
.f-footer {
  padding: 20px;
  text-align: center;
  color: #fff;
  background-color: #555;
}

.main {
  width: 80%;
  margin: 0 auto;
  color: #ccc;
}
.out-link {
  text-decoration: none;
  color: #ccc;
}
.out-link:hover {
  color: #ffffff;
}
.item-col {
  text-align: left;
}
.item-col.weside {
  font-size: 14px;
}
.item-col .title {
  color: #ddd;
  margin-bottom: 10px;
}
.item span {
  margin-right: 5px;
}
</style>