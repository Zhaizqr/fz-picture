<template id="BasicLayout">
  <a-layout>
    <a-layout-header class="header"><HeaderLayout /></a-layout-header>
    <a-layout-content class="content">
      <router-view />
      <div class="footer">
        <a-typography-link href="https://github.com/Zhaizqr" target="_blank">
          power by fangzong
        </a-typography-link>
      </div>
    </a-layout-content>
  </a-layout>
</template>
<script lang="ts" setup>
import HeaderLayout from "./HeaderLayout.vue";
</script>
<style scoped>
#BasicLayout {
  height: 100vh;
  width: 100%;
}

.header {
  height: 4rem;
  background-color: white;
}

.content {
  height: calc(100vh - 4rem);
  overflow: auto;
}

.footer {
  height: 4rem;
  text-align: center;
}
</style>
