import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap';
import './assets/css/nucleo-icons.css';
import './assets/css/nucleo-svg.css';
import { createPinia } from 'pinia';
import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import materialKit from './material-kit';
import VueCookies from 'vue3-cookies';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';

const app = createApp(App);

const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);
app.use(pinia);

app.use(router);
app.use(materialKit);
app.use(VueCookies);

app.mount('#app');
