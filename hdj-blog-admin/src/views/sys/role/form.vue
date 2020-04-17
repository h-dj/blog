<template>
  <div>
    <el-dialog
      :title="isAdd?'添加角色':'更新角色'"
      :visible.sync="dialogFormVisible"
      :close-on-click-modal="false"
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
        style="padding:15px;"
      >
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="temp.roleName" placeholder="角色名称: 管理员" />
        </el-form-item>

        <el-form-item label="角色标识" prop="roleCode">
          <el-input v-model="temp.roleCode" placeholder="角色标识:如admin " />
        </el-form-item>

        <el-form-item label="描述">
          <el-input
            v-model="temp.remark"
            :autosize="{ minRows: 2, maxRows: 4}"
            type="textarea"
            placeholder="角色描述"
          />
        </el-form-item>

        <el-form-item>
          <el-button @click="resetForm">取消</el-button>
          <el-button type="primary" :loading="submitLoading" @click="isAdd?createData():updateData()">确认</el-button>
        </el-form-item>

        <el-form-item label="菜单分配">
          <el-divider />
          <el-tree
            id="menuList"
            ref="tree"
            v-model="temp.menuIds"
            :data="menuSelectList"
            show-checkbox
            node-key="id"
            :default-expanded-keys="temp.menuIds"
            :default-checked-keys="temp.menuIds"
            :props="defaultProps"
          />
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { treeDataTranslate } from '@/utils'
import { fetchSelectList } from '@/api/menu'
import { addRole, roleInfo, updateRole } from '@/api/role'
export default {
  name: 'RoleForm',
  props: {
    isAdd: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      defaultProps: {
        children: 'children',
        label: 'menuName'
      },
      submitLoading: false,
      dialogFormVisible: false,
      menuSelectList: [],
      temp: {
        id: null,
        roleName: '',
        roleCode: '',
        menuIds: [],
        remark: ''
      },
      rules: {
        roleName: [
          {
            required: true,
            trigger: 'blur',
            message: '角色名称必填'
          }
        ],
        roleCode: [
          {
            required: true,
            trigger: 'blur',
            message: '角色编码必填'
          }
        ]
      }
    }
  },
  methods: {
    resetForm() {
      this.dialogFormVisible = false
      this.temp = {
        id: null,
        roleName: '',
        roleCode: '',
        remark: '',
        menuIds: [],
        enable: true
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
          this.submitLoading = true
          const tempData = Object.assign({}, this.temp)
          tempData['menuIds'] = this.$refs.tree.getCheckedKeys(true).filter(item => item !== 0)
          addRole(tempData)
            .then(() => {
              this.submitLoading = false
              this.$notify({
                title: 'Success',
                message: '创建成功！',
                type: 'success',
                duration: 2000
              })
              this.$emit('reload')
              this.resetForm()
            })
            .catch(e => {
              this.submitLoading = false
            })
        }
      })
    },
    updateData() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          this.submitLoading = true
          const tempData = Object.assign({}, this.temp) // copy obj
          tempData['menuIds'] = this.$refs.tree.getCheckedKeys(true).filter(item => item !== 0)
          updateRole(tempData['id'], tempData)
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
            })
            .catch(e => {
              this.submitLoading = false
            })
        }
      })
    },
    getRoleInfo(roleId) {
      roleInfo(roleId)
        .then((response) => {
          this.temp = response.data
        })
      this.loadMenuSelectList()
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
