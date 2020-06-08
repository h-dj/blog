<template>
  <div>
    <el-dialog
      :close-on-click-modal="false"
      :visible.sync="dialogFormVisible"
      :title="isAdd ? '新增菜单' : '编辑菜单'"
      append-to-body
      width="580px"
      :before-close="resetForm"
    >
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="temp"
        label-position="left"
        label-width="80px"
        size="small"
        :loading="editLoading"
      >
        <el-form-item label="菜单类型" prop="type">
          <el-radio-group v-model="temp.type" size="small">
            <el-radio-button :label="0">目录</el-radio-button>
            <el-radio-button :label="1">菜单</el-radio-button>
            <el-radio-button :label="2">按钮</el-radio-button>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="上级菜单">
          <treeselect
            v-model="temp.parentId"
            :normalizer="normalizer"
            :options="menuSelectList"
            style="width: 450px;"
            placeholder="选择上级菜单"
          />
        </el-form-item>

        <el-form-item label="菜单图标">
          <el-popover
            placement="bottom-start"
            width="450"
            trigger="click"
            @show="$refs['iconSelect'].reset()"
          >
            <IconSelect ref="iconSelect" @selected="selected" />
            <el-input
              slot="reference"
              v-model="temp.icon"
              style="width: 450px;"
              placeholder="点击选择图标"
              readonly
            >
              <svg-icon
                v-if="temp.icon"
                slot="prefix"
                :icon-class="temp.icon"
                class="el-input__icon"
                style="height: 32px;width: 16px;"
              />
              <i v-else slot="prefix" class="el-icon-search el-input__icon" />
            </el-input>
          </el-popover>
        </el-form-item>

        <el-form-item label="菜单名称" prop="menuName">
          <el-input v-model="temp.menuName" placeholder="菜单名称" />
        </el-form-item>
        <el-form-item v-if="temp.type!=0" label="权限标识" prop="permission">
          <el-input
            v-model="temp.permission"
            placeholder="权限标识"
          />
        </el-form-item>
        <el-form-item v-if="temp.type==1" label="路由" prop="url">
          <el-input v-model="temp.url" placeholder="路由" />
        </el-form-item>
        <el-form-item v-if="temp.type!=2" label="组件" prop="component">
          <el-input v-model="temp.component" placeholder="组件" />
        </el-form-item>
        <el-row>
          <el-col v-if="temp.type!=2" :span="12">
            <el-form-item label="是否隐藏" prop="hidden">
              <el-radio-group v-model="temp.hidden" size="small">
                <el-radio-button :label="true">true</el-radio-button>
                <el-radio-button :label="false">false</el-radio-button>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排序号" prop="sort">
              <el-input-number
                v-model="temp.sort"
                controls-position="right"
                :min="0"
                :max="99"
                label="排序号"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="描述">
          <el-input
            v-model="temp.remark"
            :autosize="{ minRows: 2, maxRows: 4}"
            type="textarea"
            placeholder="Please input"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm">取消</el-button>
        <el-button
          type="primary"
          :loading="submitLoading"
          @click="isAdd?createData():updateData()"
        >确认</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import IconSelect from '@/components/IconSelect'
import { fetchSelectList, addMenu, menuInfo, updateMenu } from '@/api/menu'
import { treeDataTranslate } from '@/utils'
export default {
  name: 'MenuForm',
  components: { IconSelect },
  props: {
    isAdd: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      editLoading: false,
      submitLoading: false,
      dialogFormVisible: false,
      menuSelectList: [],
      temp: {
        url: '',
        id: null,
        menuName: '',
        type: 0,
        parentId: '0',
        parentName: '根目录',
        sort: 99,
        permission: '',
        icon: null,
        remark: '',
        enable: true,
        component: 'layout/index',
        hidden: false
      },
      rules: {
        menuName: [{ required: true, message: '菜单名称必填' }],
        url: [{ required: true, message: '路由地址必填' }],
        component: [{ required: true, message: '组件必填' }]
      }
    }
  },
  methods: {
    selected(name) {
      this.temp.icon = name
    },
    resetForm() {
      this.dialogFormVisible = false
      this.temp = {
        id: null,
        menuName: '',
        type: 0,
        parentId: '0',
        parentName: '根目录',
        sort: 99,
        permission: '',
        icon: null,
        remark: '',
        enable: true,
        component: 'layout/index',
        hidden: false
      }
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    loadMenuSelectList() {
      fetchSelectList().then(r => {
        let menuTree = []
        if (r.data && r.data.length > 0) {
          menuTree = r.data
        }
        menuTree.push({ id: 0, menuName: '根目录', children: [] })
        this.menuSelectList = treeDataTranslate(menuTree)
      })
    },
    createData() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          this.submitLoading = true
          addMenu(tempData)
            .then(r => {
              this.resetForm()
              this.submitLoading = false
              this.$notify({
                title: 'Success',
                message: '创建成功！',
                type: 'success',
                duration: 2000
              })
              this.$emit('reload')
            })
            .catch(e => {
              this.submitLoading = false
            })
        }
      })
    },
    getMenuInfo(menuId) {
      this.editLoading = true
      menuInfo(menuId)
        .then(r => {
          this.editLoading = false
          this.temp = r.data
        })
        .catch(e => {
          this.editLoading = false
        })

      this.loadMenuSelectList()
    },
    updateData() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          this.submitLoading = true
          this.tempData = Object.assign({}, this.temp) // copy obj
          updateMenu(this.tempData['id'], this.tempData)
            .then(() => {
              this.submitLoading = false
              this.$notify({
                title: 'Success',
                message: '更新成功！',
                type: 'success',
                duration: 2000
              })
              this.$emit('reload')
              this.resetForm()
            }).catch((e) => {
              this.submitLoading = false
            })
        }
      })
    },
    normalizer(node) {
      return {
        id: node.id,
        label: node.menuName,
        children: node.children
      }
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
