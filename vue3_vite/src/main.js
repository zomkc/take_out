import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
// 1. 引入你需要的组件
import { Dialog,Popup,Field,List,Cell} from 'vant';
// 2. 引入组件样式
import 'vant/lib/index.css';
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import axios from 'axios'

axios.defaults.withCredentials = true
axios.c

const app = createApp(App).use(router).use(Dialog).use(Popup).use(Field).use(List).use(Cell)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)}
app.mount('#app')
