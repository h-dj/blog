<template>
  <div>
    <div id="contentEditor" />
  </div>
</template>

<script>
import { uploadImage } from '@/api/upload'
import Vditor from 'vditor'
import "vditor/dist/index.css"
import defaultOptions from './default.option'
import { formatFileSize } from '@/utils/index'
export default {
  name: 'MarkdownEditor',
  model: {
    prop: 'content',
    event: 'onContentChange'
  },
  props: {
    height: {
      type: String,
      required: false,
      default: '300px'
    },
    content: {
      type: String,
      default: ''
    },
    autoSave: {
      type: Boolean,
      required: false,
      default: true
    }
  },
  data() {
    return {
      vditor: null,
      taskId: null,
      btnList: [
        {
          hotkey: '⌘-s',
          name: 'save',
          tipPosition: 'ne',
          tip: '保存',
          icon: '<svg t="1578152936216" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1889" width="200" height="200"><path d="M426.666667 128h-149.333334v234.453333c0 12.074667 9.450667 21.546667 21.205334 21.546667h298.922666c11.626667 0 21.205333-9.6 21.205334-21.546667V128h-64v149.504c0 23.466667-19.157333 42.496-42.624 42.496h-42.752A42.666667 42.666667 0 0 1 426.666667 277.504V128zM192 896V661.546667C192 602.474667 239.786667 554.666667 298.837333 554.666667h426.325334A106.709333 106.709333 0 0 1 832 661.546667V896h42.517333A21.312 21.312 0 0 0 896 874.752V273.664L750.336 128H704v234.453333c0 58.965333-47.701333 106.88-106.538667 106.88H298.538667A106.56 106.56 0 0 1 192 362.453333V128H149.248A21.269333 21.269333 0 0 0 128 149.482667v725.034666C128 886.421333 137.578667 896 149.482667 896H192zM42.666667 149.482667A106.602667 106.602667 0 0 1 149.248 42.666667H768a42.666667 42.666667 0 0 1 30.165333 12.501333l170.666667 170.666667A42.666667 42.666667 0 0 1 981.333333 256v618.752A106.645333 106.645333 0 0 1 874.517333 981.333333H149.482667A106.752 106.752 0 0 1 42.666667 874.517333V149.482667z m704 512.042666c0-12.010667-9.536-21.525333-21.504-21.525333H298.837333C286.933333 640 277.333333 649.6 277.333333 661.546667V896h469.333334V661.546667z" p-id="1890"></path></svg>',
          click: this.submit
        },
        {
          name: 'setting',
          tipPosition: 'ne',
          tip: '文章设置',
          icon: '<svg t="1578207724107" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1917" width="200" height="200"><path d="M512 429.056A82.944 82.944 0 0 0 429.056 512 82.944 82.944 0 0 0 512 594.7904 82.944 82.944 0 0 0 594.8928 512a82.944 82.944 0 0 0-82.944-82.8928z m0 248.6784A165.9904 165.9904 0 0 1 346.2144 512 165.9904 165.9904 0 0 1 512 346.1632 165.9904 165.9904 0 0 1 677.7856 512 165.9904 165.9904 0 0 1 512 677.7344z m-0.1536 103.6288c53.76 0 103.168 21.76 136.8576 58.4704l78.848-45.568a184.2688 184.2688 0 0 1 17.2544-147.0976c26.7776-46.7456 70.5024-78.848 119.5008-89.7024V466.432a183.3984 183.3984 0 0 1-118.784-88.576c-27.0336-46.592-32.9216-100.5056-17.92-148.2752l-78.848-45.568a183.808 183.808 0 0 1-136.6016 58.4704c-53.76 0-103.168-21.7088-136.9088-58.4192l-78.848 45.568a184.32 184.32 0 0 1-17.2544 147.0976c-26.7264 46.7456-70.4512 78.848-119.4496 89.7024v90.9824a183.3984 183.3984 0 0 1 118.784 88.5248c27.0336 46.6432 32.9216 100.5568 17.92 148.3264l78.848 45.5168a183.808 183.808 0 0 1 135.5264-58.4704h1.0752z m-124.9792 154.4704a41.3696 41.3696 0 0 1-20.6848-5.5296l-143.5648-82.8928a41.5744 41.5744 0 0 1-15.104-56.7808l2.2016-3.7888c16.9984-29.4912 16.1792-66.2528-2.304-98.1504-19.0976-33.3824-50.432-52.3776-84.6848-52.3776H118.272a41.472 41.472 0 0 1-41.472-41.472V429.056a41.472 41.472 0 0 1 41.472-41.472h4.4544c34.2528 0 65.5872-18.944 83.8144-50.7904 0.0512-0.1536 0.9728-1.6896 1.024-1.8944 18.3296-31.5904 19.1488-68.352 2.048-97.9456l-2.0992-3.6864a41.3696 41.3696 0 0 1 15.104-56.7808l143.5648-82.8928a41.472 41.472 0 0 1 56.6272 15.1552l2.2528 3.9936c16.9984 29.3888 48.9984 46.8992 85.6576 46.8992h0.7168c38.5024 0 70.5024-17.5104 87.4496-46.8992l2.304-3.9424a41.472 41.472 0 0 1 56.576-15.2064l143.616 82.944a41.472 41.472 0 0 1 15.1552 56.6784l-2.2528 3.7888c-17.0496 29.5424-16.1792 66.304 2.304 98.2528 19.0976 33.3824 50.432 52.3776 84.6848 52.3776h4.4544a41.472 41.472 0 0 1 41.472 41.472v165.7344a41.472 41.472 0 0 1-41.472 41.472h-4.4544c-34.2528 0-65.5872 18.9952-83.8144 50.8416l-1.0752 1.8432c-18.2784 31.5904-19.1488 68.352-2.048 97.9456l2.2016 3.7888a41.5232 41.5232 0 0 1-15.1552 56.6784l-143.5648 82.944a41.5744 41.5744 0 0 1-56.6272-15.2576l-2.304-3.9424c-16.9472-29.3888-48.9472-46.8992-85.6576-46.8992h-0.6656c-38.5024 0-70.5024 17.5104-87.5008 46.8992l-2.2528 3.9936a41.472 41.472 0 0 1-35.9424 20.6848z" fill="#000000" p-id="1918"></path></svg>',
          click: this.showSetting
        }
      ]
    }
  },
  mounted() {
    this.initVditor()
    this.startAutoSave()
  },
  destroyed() {
    window.clearInterval(this.taskId)
  },
  methods: {
    initVditor() {
      defaultOptions.toolbar.push(...this.btnList)
      defaultOptions.upload.handler = this.handler
      defaultOptions.blur = this.syncContentEvent
      defaultOptions.input = this.syncContentEvent
      this.vditor = new Vditor('contentEditor', defaultOptions)
      this.vditor.clearCache()
      this.vditor.focus()
    },
    handler(files) {
      if (files && files.length > 1) {
        this.vditor.tip('只能上传单个文件', 3000)
        return
      }
      const file = files[0]
      const type = file.type.toLowerCase()
      const size = file.size
      if (!defaultOptions.upload.accept.includes(type)) {
        this.vditor.tip('不支持该类型', 3000)
        return
      }
      if (defaultOptions.upload.max < size) {
        this.vditor.tip(
          '上传文件不能超过' + formatFileSize(defaultOptions.upload.max),
          3000
        )
        return
      }
      const formData = new FormData()
      formData.append('file', file)
      uploadImage(formData).then(r => {
        this.vditor.insertValue(this.insertImage(r.data.url))
      })
    },
    setValue(mdContent) {
      this.vditor.setValue(mdContent)
    },
    submit(e) {
      this.$emit('submit')
    },
    syncContentEvent(md) {
      this.$emit('onContentChange', md)
    },
    insertImage(url) {
      return `\n![](${url})`
    },
    showSetting() {
      this.$emit('openSetting')
    },
    startAutoSave() {
      if (this.autoSave) {
        this.taskId = setInterval(() => {
          if (this.vditor.getValue()) {
            this.$emit('autoSubmit')
          }
        }, 10000)
      }
    }
  }
}
</script>
<style lang="scss">
div#contentEditor {
  height: 480px;
  background-color: #f6f8fa;
}
.vditor--fullscreen {
  z-index: 2000 !important;
}
</style>
