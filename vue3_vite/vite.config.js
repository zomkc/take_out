import { defineConfig } from 'vite'
// import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
import vue from '@vitejs/plugin-vue';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    //ElementPlus按需引入
    AutoImport({
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      resolvers: [ElementPlusResolver()],
    }),
  ],
  base:'./',
  server:{
    proxy:{
      '/api':{
        target:'http://localhost:8080',
        // target:'http://zomkc.cn:8080',
        changeOrigin:true,  // 允许跨域
        rewrite: (path) => path.replace(/^\/api/,'')
      }
    }
  },
})
