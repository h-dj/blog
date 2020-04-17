import Vue from 'vue'
import SvgIcon from '@/components/SvgIcon' // svg component

// register globally
Vue.component('svg-icon', SvgIcon)

const req = require.context('./svg', false, /\.svg$/)

const requireAll = requireContext => requireContext.keys().map(requireContext)

const iconList = requireAll(req)

export function fetchIconList() {
  return iconList.map(item => item.default.id.replace('icon-', ''))
}

