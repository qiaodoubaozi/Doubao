<template>
  <div>
    <van-nav-bar
      title="标题"
      left-text="返回"
      right-text="按钮"
      left-arrow
      @click-left="onClickLeft"
      @click-right="onClickRight"
    >
      <template #right>
        <van-icon name="search" size="18" />
      </template>
    </van-nav-bar>

    <div id="content">
      <template v-if="active === 'index'">
        <Index />
      </template>
      <template v-if="active === 'team'">
        <Team />
      </template>
    </div>

    <van-tabbar v-model="active" @change="onChange">
      <van-tabbar-item icon="home-o" name="index">主页</van-tabbar-item>
      <van-tabbar-item icon="search" name="team">队伍</van-tabbar-item>
      <van-tabbar-item icon="friends-o" name="user">个人</van-tabbar-item>
    </van-tabbar>

  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { showToast } from "vant";
import Index from "../views/IndexPage.vue";
import "vant/es/toast/style";

const active = ref("index");

const onClickLeft = () => history.back();
const onClickRight = () => showToast("按钮");
const onChange = (index) => showToast(`标签 ${index}`);

</script>

<style scoped></style>
