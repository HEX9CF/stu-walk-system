<template>
  <view>
    <!-- Header with user profile picture and name -->
    <view class="head">
      <image 
        :src="user.head_pic" 
        mode="widthFix" 
        class="headpic"
      />
      <text class="headtext">{{ user.name }}</text>
    </view>

    <!-- Message box with user information options -->
    <view class="messageBox">
      <!-- Username section -->
      <view 
        class="username-all" 
        @click="gotoChangeUsername"
      >
        <view class="all-both">
          <text class="all-both-first">用户名</text>
          <text class="all-both-second">{{ user.name }}</text>
        </view>
        <uni-icons 
          type="arrowright" 
          size="16"
        />
      </view>

      <!-- Sex section -->
      <view 
        class="sex-all" 
        @click="gotoChangeSex"
      >
        <view class="all-both">
          <text class="all-both-first">性别</text>
          <view 
            class="all-both-second" 
            v-if="user.sex === 1"
          >
            男生
          </view>
          <view 
            class="all-both-second" 
            v-else-if="user.sex === 0"
          >
            女生
          </view>
          <view 
            class="all-both-second" 
            v-else
          >
            未知
          </view>
        </view>
        <uni-icons 
          type="arrowright" 
          size="16"
        />
      </view>

      <!-- Introduction section -->
      <view 
        class="jianjie-all" 
        @click="gotoChangeIntroduction"
      >
        <view class="all-both">
          <text class="all-both-first">简介</text>
          <text class="all-both-second">{{ user.introduction }}</text>
        </view>
        <uni-icons 
          type="arrowright" 
          size="16"
        />
      </view>
    </view>

    <!-- Bottom logout section -->
    <view class="bottombox">
      <view 
        class="exitbox" 
        @click="exit"
      >
        <text class="exit_text">退出登录</text>
      </view>
    </view>
  </view>
</template>

<script>
  import { mapState, mapMutations } from 'vuex'

  export default {
    computed: {
      ...mapState('m_user', ['user'])
    },
    data() {
      return {}
    },
    methods: {
      ...mapMutations('m_user', ['logoutToken']),

      // Navigate to the username change page
      gotoChangeUsername() {
        uni.navigateTo({
          url: '/subpkg/ChangeUsername/ChangeUsername'
        })
      },

      // Navigate to the sex change page
      gotoChangeSex() {
        uni.navigateTo({
          url: '/subpkg/changeSex/changeSex'
        })
      },

      // Navigate to the introduction change page
      gotoChangeIntroduction() {
        uni.navigateTo({
          url: '/subpkg/changeIntroduction/changeIntroduction'
        })
      },

      // Handle logout
      exit() {
        return uni.showModal({
          title: '登出',
          content: '确认要退出登录吗？',
          success: function (res) {
            if (res.confirm) {
              this.logoutToken()
              uni.switchTab({
                url: '/pages/usr/usr'
              })
            } else {
              // User canceled logout
            }
          }.bind(this)
        })
      }
    }
  }
</script>

<style lang="scss">
  .head {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100%;
    padding: 20rpx;
    padding-top: 100rpx;
    box-sizing: border-box;
  }

  .headpic {
    width: 200rpx;
    height: 200rpx;
    border-radius: 100px;
    margin: auto;
  }

  .headtext {
    font-size: 15px;
    margin-top: 20rpx;
    color: gray;
  }

  .messageBox {
    background-color: white;
    padding: 20rpx;
    margin: 20rpx;
    margin-top: 80rpx;
    box-sizing: border-box;
    border-radius: 10px;
  }

  .username-all, .sex-all, .jianjie-all {
    display: flex;
    padding: 20rpx;
    justify-content: space-between;
    border-bottom: solid 1px rgb(247, 247, 247);
    box-sizing: border-box;
  }

  .username-all {
    padding-bottom: 30rpx;
  }

  .sex-all, .jianjie-all {
    padding-top: 30rpx;
    padding-bottom: 30rpx;
  }

  .all-both {
    display: flex;
    align-items: center;
  }

  .all-both-first {
    margin-right: 45rpx;
  }

  .all-both-second {
    font-size: 13px;
    color: gray;
  }

  .bottombox {
    position: fixed;
    bottom: 0;
    width: 100%;
    height: 200rpx;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .exitbox {
    background-color: #ADD8E6;
    width: 260rpx;
    height: 50rpx;
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 15px;
    padding: 8rpx;
  }

  .exit_text {
    font-size: 16px;
    color: white;
  }

  page {
    background-color: rgb(247, 247, 247);
  }
</style>
