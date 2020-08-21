import Vue from 'vue'
import App from './App.vue'
import antd from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css';

Vue.use(antd)
// Vue.component(Card.name, Card)
// Vue.component(Row.name, Row)
// Vue.component(Col.name, Col)
// Vue.component(List.name, List.Item.Meta, List.Item, List)
// Vue.component(Icon.name, Icon)
// Vue.component(Avatar.name, Avatar)
// Vue.component(Statistic.name, Statistic)

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
}).$mount('#app')
