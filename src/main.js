import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'


const app = createApp(App)
app.config.compilerOptions.isCustomElement = (tag) => {
  return tag === 'ec-canvas' // ECharts相关兼容配置
}
app.use(ElementPlus)
app.mount('#app')