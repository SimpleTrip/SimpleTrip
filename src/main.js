import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap';
import './assets/css/nucleo-icons.css';
import './assets/css/nucleo-svg.css';
import { createApp } from 'vue';
import { createPinia } from 'pinia';

import App from './App.vue';
import router from './router';

import materialKit from './material-kit';

const app = createApp(App);

app.use(createPinia());
app.use(router);
app.use(materialKit);

app.mount('#app');
