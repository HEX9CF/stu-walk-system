<template>
  <view>
    <!-- Input section for the user's introduction -->
    <view class="input-container">
      <textarea 
        class="input-textarea" 
        auto-focus="true" 
        maxlength="50" 
        auto-height 
        @input="updateContent" 
        :placeholder="user.introduction"
      />
    </view>

    <!-- Character count and confirm button -->
    <view class="character-count">
      <text class="character-count-text">{{ charCount }}/50</text>
      <view class="confirm-button" @click="confirmChange">
        <text class="confirm-button-text">修改</text>
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
      title: '数据加载中...', // Loading message
    })
  }

  // Response interceptor
  $http.afterRequest = function () {
    uni.hideLoading()
  }

  export default {
    computed: {
      ...mapState('m_user', ['user', 'token']) // Map Vuex state
    },
    data() {
      return {
        charCount: 0, // Character count for the input
        content: '', // The content of the input field
      }
    },
    onLoad() {
      // If user is not logged in, redirect to the mine page
      if (!this.token) {
        uni.switchTab({
          url: '/pages/mine/mine'
        })
      }
    },
    methods: {
      // Vuex mutations for changing the introduction
      ...mapMutations('m_user', ['changeIntroduction']),

      /**
       * Handles the input event and updates the character count.
       * @param {Object} e - The event object
       */
      updateContent(e) {
        this.content = e.detail.value
        this.charCount = this.content.length
      },

      /**
       * Confirms the content change and sends it to the backend.
       * Displays a success or error message based on the response.
       */
      async confirmChange() {
        // Validate that the input is not empty
        if (this.content.length === 0) {
          return uni.showToast({
            title: '输入不能为空！',
            duration: 1000,
            icon: 'none'
          })
        }

        // Send the content to the backend for saving
        const res = await $http.get("/WxChangeIntroductionServlet?id=" + this.user.id + "&introduction=" + this.content)
        
        // Handle the response
        if (res.statusCode !== 200) {
          return uni.showToast({
            title: '数据请求失败！',
            duration: 1500,
            icon: 'none'
          })
        } else {
          // Update the user introduction in Vuex
          this.changeIntroduction(this.content)
          return uni.showToast({
            title: '修改成功！',
            duration: 1500,
            icon: 'none'
          })
        }
      }
    }
  }
</script>

<style lang="scss">
  .input-container {
    top: 10rpx;
    width: 100%;
    padding: 10rpx;
    display: flex;
    flex-direction: column;
  }

  .input-textarea {
    width: 92%;
    height: 320rpx;
    background-color: #f0f0f0;
    border-radius: 10rpx;
    padding: 20rpx;
  }

  .character-count {
    text-align: right;
    align-items: center;
    display: flex;
    padding: 20rpx;
    padding-top: 20rpx;
  }

  .character-count-text {
    font-size: 24rpx;
    color: #646464;
  }

  .confirm-button {
    border-radius: 40rpx;
    background-color: #ADD8E6;
    display: flex;
    align-items: center;
    padding: 20rpx;
    padding-top: 10rpx;
    padding-bottom: 10rpx;
    position: absolute;
    right: 15rpx;
  }

  .confirm-button-text {
    color: white;
    font-size: 15px;
    font-family: 黑体, STSong, serif;
  }
</style>
