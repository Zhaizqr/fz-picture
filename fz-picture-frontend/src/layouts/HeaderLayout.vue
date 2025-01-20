<template id="HeaderLayout">
  <div class="header-container">
    <!-- Logo 和标题点击跳转首页 -->
    <div class="item-container" @click="gotoHome">
      <div class="logo">
        <img style="width: 2rem" src="/logo.svg" alt="logo" />
      </div>
      <div class="title">fz-picture</div>
    </div>

    <a-menu
      v-model:selectedKeys="current"
      mode="horizontal"
      :items="items"
      @click="handleChangeMenuItem"
      style="flex-grow: 1"
    />

    <!-- 头像部分 -->
    <div class="item-container" @click="showAvatarDrawerVisible">
      <a-avatar>登录</a-avatar>
    </div>

    <!-- 右侧抽屉 -->
    <a-drawer
      v-model:open="avatarDrawerVisible"
      :closable="false"
      width="250px"
      style="border-radius: 0.5rem 0 0 0.5rem"
      placement="right"
    >
      <div class="drawer-userinfo-container" @click="goToSettings">
        <a-avatar style="margin-right: 1rem; background-color: #1890ff"
          >登录</a-avatar
        >
        <span style="font-size: 1rem; font-weight: 500">Hi~ 放纵</span>
      </div>
      <a-divider />
      <div class="drawer-content">
        <a-button type="link" block class="drawer-button" @click="goToSettings">
          <UserOutlined />
          个人中心
        </a-button>
        <a-button type="link" block @click="goToSettings" class="drawer-button">
          <UnorderedListOutlined />
          系统设置
        </a-button>
        <a-button type="link" block @click="logout" class="drawer-button">
          <LogoutOutlined />
          退出登录
        </a-button>
      </div>
    </a-drawer>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, ref } from "vue";
import {
  LogoutOutlined,
  UserOutlined,
  UnorderedListOutlined,
} from "@ant-design/icons-vue";
import type { MenuProps } from "ant-design-vue";
import { useRouter } from "vue-router";

const current = ref<string[]>(["/"]);
const router = useRouter();

// 动态菜单项
const items = ref([]);

// 获取路由并生成菜单项
const generateMenuItems = async () => {
  const routes = router.getRoutes();
  items.value = routes
    .filter((route) => route.name && route.path) // 过滤有标题的路由
    .map((route) => ({
      key: route.path,
      label: route.name,
    }));
};

// 菜单点击事件
const handleChangeMenuItem: MenuProps["onClick"] = (menuInfo) => {
  router.push(menuInfo.key);
};

// 跳转到首页
const gotoHome = () => {
  router.push("/");
  current.value = ["/"];
};

// 抽屉的状态和控制
const avatarDrawerVisible = ref<boolean>(false);
const showAvatarDrawerVisible = () => {
  avatarDrawerVisible.value = true;
};

// 其他操作
const goToSettings = () => {
  console.log("Navigating to Settings");
};
const logout = () => {
  console.log("Logged out");
};

// 页面加载时初始化菜单项
onMounted(() => {
  generateMenuItems();
});
</script>

<style scoped>
/* 样式代码保持不变 */
.header-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  padding: 0 2rem;
}

.item-container {
  display: flex;
  align-items: center;
  margin-right: 2rem;
  cursor: pointer;
}

.title {
  margin-left: 0.5rem;
  font-size: 1.5rem;
  font-weight: bold;
}

.logo img {
  width: 2rem;
}

.ant-menu-horizontal {
  border: none;
}

.drawer-content {
  display: flex;
  flex-direction: column;
}

.drawer-button {
  text-align: left;
  font-weight: 500;
  font-size: 1rem;
  display: flex;
  align-items: center;
  padding: 0.5rem 1rem;
  margin: 1rem;
  color: #404040;
  border: none;
  background-color: transparent;
  transition: all 0.3s ease;
}
.drawer-userinfo-container {
  display: flex;
  align-items: center;
  padding: 1rem;
  margin: 1rem;
  background-color: #ffffff;
  border-radius: 0.5rem;
  cursor: pointer;
  transition: all 0.3s ease;
}

.drawer-userinfo-container:hover {
  background-color: #fafafa; /* 悬停时背景色更浅 */
  border-color: #e0e0e0; /* 悬停时边框颜色加深 */
}

.drawer-userinfo-container .ant-avatar {
  background-color: #1890ff;
  color: #ffffff;
  font-size: 1rem;
  font-weight: 500;
}

.drawer-userinfo-container span {
  font-size: 1rem;
  font-weight: 500;
  color: #404040;
}

.drawer-button:hover {
  background-color: #f0f0f0;
  color: #1890ff;
  border-radius: 0.2rem;
}

.drawer-button a-icon {
  margin-right: 1rem;
}
</style>
