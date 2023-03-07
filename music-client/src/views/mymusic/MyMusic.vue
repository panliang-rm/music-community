<template>
    <div class="mymusic">
      <div class="mymusic-info">
        <el-image class="mymusic-img" fit="contain" :src="attachImageUrl(userPic)" @click="dialogTableVisible = true" />
        <div class="mymusic-msg">
          <div class="username">{{ mymusicInfo.username }}</div>
          <div class="introduction">{{ mymusicInfo.introduction }}</div>
        </div>
      </div>
      <div class="mymusic-body">
        <song-list :songList="collectSongList" :show="true" @changeData="changeData"></song-list>
      </div>
    </div>
  </template>
  
  <script lang="ts">
  import { defineComponent, nextTick, ref, computed, watch, reactive } from "vue";
  import { useStore } from "vuex";
  import SongList from "@/components/SongList.vue";
  import mixin from "@/mixins/mixin";
  import { HttpManager } from "@/api";
  import { RouterName } from "@/enums";
  
  export default defineComponent({
    components: {
      SongList,
    },
    setup() {
      const store = useStore();
  
      const { routerManager } = mixin();
  
      const dialogTableVisible = ref(false);
      const collectSongList = ref([]); // 收藏的歌曲
      const mymusicInfo = reactive({
        username: "",
        userSex: "",
        birth: "",
        location: "",
        introduction: "",
      });
      const userId = computed(() => store.getters.userId);
      const userPic = computed(() => store.getters.userPic);
      watch(userPic, () => {
        dialogTableVisible.value = false;
      });
  
      function goPage() {
        routerManager(RouterName.Setting, { path: RouterName.Setting });
      }
      async function getUserInfo(id) {
        const result = (await HttpManager.getUserOfId(id)) as ResponseBody;
        mymusicInfo.username = result.data[0].username;
        mymusicInfo.userSex = result.data[0].sex;
        mymusicInfo.birth = result.data[0].birth;
        mymusicInfo.introduction = result.data[0].introduction;
        mymusicInfo.location = result.data[0].location;
      }
      // 获取收藏的歌曲
      async function getCollection(userId) {
        collectSongList.value = []
        const result = (await HttpManager.getCollectionOfUser(userId)) as ResponseBody;
        const collectIDList = result.data || []; // 存放收藏的歌曲ID
        // 通过歌曲ID获取歌曲信息
        for (const item of collectIDList) {
          const result = (await HttpManager.getSongOfId(item.songId)) as ResponseBody;
          collectSongList.value.push(result.data[0]);
        }
      }
  
      function changeData() {
        getCollection(userId.value);
      }
  
      nextTick(() => {
        getUserInfo(userId.value);
        getCollection(userId.value);
      });
  
      return {
        userPic,
        dialogTableVisible,
        collectSongList,
        mymusicInfo,
        attachImageUrl: HttpManager.attachImageUrl,
        goPage,
        changeData,
      };
    },
  });
  </script>
  
  <style lang="scss" scoped>
  @import "@/assets/css/var.scss";
  
  .mymusic {
    padding-top: $header-height + 150px;
  
    &::before {
      content: "";
      background-color: $color-blue-shallow;
      position: absolute;
      top: 0;
      width: 100%;
      height: $header-height + 150px;
    }
  }
  
  .mymusic-info {
    position: relative;
    margin-bottom: 60px;
    .mymusic-img {
      height: 200px;
      width: 200px;
      border-radius: 50%;
      border: 5px solid $color-white;
      position: absolute;
      top: -180px;
      left: 50px;
      cursor: pointer;
    }
    .mymusic-msg {
      margin-left: 300px;
      position: absolute;
      top: -120px;
  
      .username {
        font-size: 50px;
        font-weight: 600;
      }
  
      .introduction {
        font-size: 20px;
        font-weight: 500;
      }
    }
    .edit-info {
      position: absolute;
      right: 10vw;
      margin-top: -120px;
    }
  }
  
  @media screen and (min-width: $sm) {
    .mymusic-body {
      padding: 0px 100px;
    }
  }
  
  @media screen and (max-width: $sm) {
    .edit-info {
      display: none;
    }
  }
  </style>
  