<template>
  <div class="apply">
    <el-row
      class="main"
      type="flex"
      justify="center"
    >
      <el-col
        :sm="22"
        :md="16"
      >
        <h5 class="title">
          <i class="el-icon-circle-plus-outline" />
          {{ $t('apply.process') }}
        </h5>
        <hr>
        <el-card shadow="hover">
          <el-steps
            align-center
            :active="currStep"
          >
            <el-step
              :title="$t('apply.step1')"
              description="首先接入本博客站点"
            />
            <el-step
              :title="$t('apply.step2')"
              description="提交您的站点信息"
            />
            <el-step
              :title="$t('apply.step3')"
              description="等待审核"
            />
          </el-steps>
          <div
            class="stepContent"
            v-if="currStep === 1"
          >
            <div class="statement">
              <div class="item">
                {{ $t('apply.blogLink') }}：{{ statement.blogLink }}
              </div>
              <div class="item">
                {{ $t('apply.nickName') }}：{{ statement.author }}
              </div>
              <div class="item">
                {{ $t('apply.introduction') }}：{{ statement.motto }}
              </div>
            </div>
          </div>
          <div
            class="stepContent"
            v-if="currStep === 2"
          >
            <el-form
              label-position="right"
              :rules="rules"
              label-width="80px"
              ref="formLabelAlign"
              :model="formLabelAlign"
            >
              <el-form-item
                :label="$t('apply.nickName')"
                prop="title"
              >
                <el-input
                  v-model="formLabelAlign.title"
                  placeholder="name"
                />
              </el-form-item>
              <el-form-item
                :label="$t('apply.email')"
                prop="email"
              >
                <el-input
                  v-model="formLabelAlign.email"
                  placeholder="test@gmail.com"
                />
              </el-form-item>
              <el-form-item
                :label="$t('apply.blogLink')"
                prop="url"
              >
                <el-input
                  v-model="formLabelAlign.url"
                  placeholder="https://www.jiajianhuang.cn/"
                />
              </el-form-item>
              <el-form-item
                :label="$t('apply.notes')"
                prop="remark"
              >
                <el-input
                  type="textarea"
                  v-model="formLabelAlign.remark"
                />
              </el-form-item>
            </el-form>
          </div>
          <div
            class="stepContent"
            v-if="currStep === 3"
          >
            <p class="tips">
              <i
                class="el-icon-success"
                style="color: #409EFF;font-size: 30px;"
              />
              <br>感谢您对本站的信赖。
            </p>
          </div>
          <el-row
            type="flex"
            class="btn-tool"
            justify="space-around"
          >
            <el-button
              
              icon="el-icon-circle-close-outline"
              v-if="currStep === 2"
              @click="lastStep"
            >
              {{ $t('apply.process') }}
            </el-button>
            <el-button
              v-loading="submitLoading"
              type="primary"
              icon="el-icon-circle-check-outline"
              @click="nextStep"
              v-if="currStep !== 3"
            >
              {{ $t('apply.next') }}
            </el-button>
          </el-row>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { addfriendLinks } from "@/api/article";
const defaultSettings = require("@/settings.js");
export default {
  name: "Apply",
  data() {
    var validateUrl = (rule, value, callback) => {
       if (/^(http[s]?:)/.test(value)) {
            callback();
        } 
        callback(new Error('请求输入正确的博客链接！'));
      };
    return {
      submitLoading: false,
      statement: defaultSettings.statement,
      currStep: 1,
      formLabelAlign: {
        title: "",
        email: "",
        url: "",
        remark: ""
      },
      rules: {
        title: [
          {
            required: true,
            message: "请输入您的昵称",
            trigger: "blur"
          }
        ],
        email: [
          {
            required: true,
            message: "请输入邮箱地址",
            trigger: "blur"
          },
          {
            type: "email",
            message: "请输入正确的邮箱地址",
            trigger: ["blur", "change"]
          }
        ],
        url: [
          {
            required: true,
            message: "请输入博客",
            trigger: "blur"
          },
          {
            validator: validateUrl,
            trigger: ["blur", "change"]
          }
        ],
        remark: [
          {
            required: false,
            message: "请输入额外备注",
            trigger: "blur"
          }
        ]
      }
    };
  },
  methods: {
  
    nextStep() {
      
      if (this.currStep === 2) {
        this.$refs['formLabelAlign'].validate(valid => {
          if (valid) {
            this.submitLoading = true;
            addfriendLinks(this.formLabelAlign).then(r => {
              if (r.success) {
                this.currStep++;
              } else {
                this.$message("添加友链失败！");
              }
              this.submitLoading = true;
            });
          } else {
            return false;
          }
        });
      }else{
        this.currStep++;
      }
    },
    lastStep() {
      this.currStep--;
    }
  }
};
</script>

<style scoped>
.title {
  margin-top: 40px;
}

.statement {
  padding: 20px;
  background-color: #ebeef5;
  margin-top: 20px;
}

.stepContent {
  margin: 30px 0;
}

.stepContent .tips {
  font-size: 18px;
  text-align: center;
}
</style>