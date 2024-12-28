<template>
  <view>
    <!-- Username input section -->
    <view class="shurukuang">
      <textarea 
        class="shurukuang-textarea" 
        auto-focus="true" 
        maxlength="10" 
        auto-height
        @input="inputContent" 
        placeholder="修改你的用户名"
      />
    </view>

    <!-- Word count and confirm button -->
    <view class="zishu">
      <text class="zishu-text">{{ start }}/10</text>
      <view 
        class="zishu-button" 
        @click="confirm"
      >
        <text class="zishu-button-text">修改</text>
      </view>
    </view>
  </view>
</template>

<script>
  import { $http } from '@escook/request-miniprogram'
  import { mapState, mapMutations } from 'vuex'

  uni.$http = $http
  $http.baseUrl = 'https://database.ccjy16.top'

  // Request interceptor
  $http.beforeRequest = function (options) {
    uni.showLoading({
      title: '数据加载中...',
    })
  }

  // Response interceptor
  $http.afterRequest = function () {
    uni.hideLoading()
  }

  export default {
    computed: {
      ...mapState('m_user', ['user', 'token'])
    },
    data() {
      return {
        start: 0,          // Start count for characters
        content: '',       // User input for the username
        suchUser: {}       // Holds data from user search (not currently in use)
      }
    },
    onLoad() {
      // Redirect to login if token is not available
      if (!this.token) {
        uni.switchTab({
          url: '/pages/mine/mine'
        })
      }
    },
    methods: {
      // Vuex mutation to update the username
      ...mapMutations('m_user', ['changeUsername']),

      /**
       * Handles user input and updates character count
       * @param {Object} e - The input event
       */
      inputContent(e) {
        this.content = e.detail.value
        this.start = this.content.length
      },

      /**
       * Submits the new username after validation
       */
      async confirm() {
        // Validate username
        if (this.content.length === 0) {
          return uni.showToast({
            title: '用户名不能为空！',
            duration: 1000,
            icon: 'none'
          })
        }

        // Make a POST request to update the username
        uni.request({
          header: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          url: 'https://database.ccjy16.top/WxChangeUsernameServlet', // Server URL
          method: 'POST',
          data: {
            afterUsername: this.content,
            id: this.user.id
          },
          success: (res) => { // Success callback
            this.changeUsername(this.content)  // Update Vuex state with new username
            return uni.showToast({
              title: '修改成功！',
              duration: 1500,
              icon: 'none'
            })
          },
          fail: (err) => {  // Failure callback
            console.error('服务器请求失败：', err)
          }
        })
      }
    }
  }
</script>

<style lang="scss">
  .shurukuang {
    top: 10rpx;
    width: 100%;
    padding: 10rpx;
    display: flex;
    flex-direction: column;

    .shurukuang-textarea {
      width: 92%;
      height: 320rpx;
      background-color: #f0f0f0;
      border-radius: 10rpx;
      padding: 20rpx;
    }
  }

  .zishu {
    text-align: right;
    align-items: center;
    display: flex;
    padding: 20rpx;
    padding-top: 20rpx;

    .zishu-text {
      font-size: 24rpx;
      color: #646464;
    }

    .zishu-button {
      border-radius: 40rpx;
      background-color: #ADD8E6;
      display: flex;
      align-items: center;
      padding: 20rpx;
      padding-top: 10rpx;
      padding-bottom: 10rpx;
      position: absolute;
      right: 15rpx;

      .zishu-button-text {
        color: white;
        font-size: 15px;
        font-family: 黑体, STSong, serif;
      }
    }
  }
</style>
