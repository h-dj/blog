<template>
  <div class="tab-container">
    <el-tabs v-model="activeName" type="border-card">
      <el-tab-pane v-for="item in tabMapOptions" :key="item.key" :label="item.label" :name="item.key">
        <keep-alive>
          <tab-pane v-if="activeName==item.key" :level="item.key" />
        </keep-alive>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import tabPane from './components/TabPane'
export default {
  name: 'Tab',
  components: { tabPane },
  data() {
    return {
      tabMapOptions: [
        { label: '操作日志', key: 'INFO' },
        { label: '异常日志', key: 'ERROR' },
        { label: '登录', key: 'LOGIN' }
      ],
      activeName: 'INFO'
    }
  }
}
</script>

<style scoped>
  .tab-container {
    margin: 30px;
  }
</style>
