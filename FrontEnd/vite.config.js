import { fileURLToPath, URL } from 'node:url';

import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
  server: {
    proxy: {
      '/local': {
        target: 'http://localhost',
        changeOrigin: true,
        secure: false,
        ws: true,
        rewrite: (path) => path.replace(/^\/local/, ''),
      },
      '/openApiMap': {
        target: 'https://apis.data.go.kr/B551011/KorService1',
        changeOrigin: true,
        secure: false,
        ws: true,
        rewrite: (path) => path.replace(/^\/openApiMap/, ''),
      },
    },
  },
  define: {
    global: {},
  },
});
