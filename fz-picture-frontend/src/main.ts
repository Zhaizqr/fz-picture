import { createApp } from "vue";
import "./style.css";
import App from "./App.vue";
import router from "./router"; // 导入路由配置
import Antd from "ant-design-vue";
import "ant-design-vue/dist/reset.css";

const app = createApp(App);

app.use(router); // 使用路由
app.use(Antd); // 使用路由

app.mount("#app");
