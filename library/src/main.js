// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store/index'

//引入所需插件
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'
import VDistpicker from 'v-distpicker'
import vcharts from 'v-charts/lib'

Vue.config.productionTip = false

//使用
Vue.use(ElementUI)
Vue.prototype.axios = axios
Vue.component('v-distpicker',VDistpicker)
Vue.use(vcharts)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
