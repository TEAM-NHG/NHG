import { createApp } from 'vue'
import { createPinia } from 'pinia'

import 'bootstrap-icons/font/bootstrap-icons.css';
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap";

import App from './App.vue'
import router from './router'

//Kakao map
import { useKakao } from 'vue3-kakao-maps/@utils';
useKakao('c7667dc0ba9ca1a91d018e85b0a06094');

//Font-Awesome
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

/* add fontawesome core */
import { library } from '@fortawesome/fontawesome-svg-core'

import { fas } from '@fortawesome/free-solid-svg-icons'
library.add(fas)

const app = createApp(App)

app.component('font-awesome-icon', FontAwesomeIcon)
app.use(createPinia())
app.use(router)

app.mount('#app')
