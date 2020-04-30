<template>
  <div class="f-footer">
    <el-main class="main">
      <el-row>
        <el-col
          :span="10"
          class="item-col"
        >
          <div class="title">
          </div>
          <div class>
            <a
              href="#"
              class="out-link"
            ></a>
          </div>
          <div class>
            <a
              href="#"
              class="out-link"></a>
          </div>
          <div class>
            <a
              href="#"
              class="out-link"
            ></a>
          </div>
        </el-col>
        <el-col
          :span="14"
          class="item-col weside"
        >
          <div class="item">
          </div>
          <div class="item">
          </div>
          <div class="item">

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