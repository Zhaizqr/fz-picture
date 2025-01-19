<template id="HeaderLayout">
  <a-menu v-model:selectedKeys="current" mode="horizontal" :items="items" />
</template>
<script lang="ts" setup>
import { h, ref } from "vue";
import {
  MailOutlined,
  AppstoreOutlined,
  SettingOutlined,
} from "@ant-design/icons-vue";
import { MenuProps } from "ant-design-vue";
const current = ref<string[]>(["Home"]);
const items = ref<MenuProps["items"]>([
  {
    key: "Home",
    icon: () => h(MailOutlined),
    label: "Home",
    title: "Home",
  },
  {
    key: "About",
    icon: () => h(MailOutlined),
    label: "About",
    title: "About",
  },
]);
</script>
