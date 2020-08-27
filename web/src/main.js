import Vue from 'vue'
import App from './App.vue'
import antd from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css'
import axios from 'axios'

Vue.use(antd, axios)
axios.defaults.baseURL = process.env.NODE_ENV === 'production'
  ? '/cabinet-server/'
  : '/'
Vue.prototype.$axios = axios

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
}).$mount('#app')
