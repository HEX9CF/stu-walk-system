<template>
  <view>
    <!-- Textarea for user input -->
    <view class="shurukuang">
      <textarea 
        class="shurukuang-textarea" 
        auto-focus="true" 
        maxlength="200" 
        auto-height 
        @input="inputContent" 
        placeholder="请友善发言哦"
      />
    </view>

    <!-- Character count and send button -->
    <view class="zishu">
      <text class="zishu-text">{{ start }}/200</text>
      <view 
        class="zishu-button" 
        @click="confirm"
      >
        <text class="zishu-button-text">发送</text>
      </view>
    </view>
  </view>
</template>

<script>
  import { $http } from '@escook/request-miniprogram'
  import { mapState } from 'vuex'

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
      ...mapState('m_user', ['user', 'token']),
    },
    data() {
      return {
        start: 0,       // Character count
        content: ''     // User input content
      }
    },
    onLoad() {
      // Redirect to login page if not logged in
      if (!this.token) {
        uni.switchTab({
          url: '/pages/usr/usr'
        })
      }
    },
    methods: {
      // Update character count and store content
      inputContent(e) {
        this.content = e.detail.value
        this.start = this.content.length
      },

      // Send message to server
      async confirm() {
        // Validate input content
        if (this.content.length == 0) {
          return uni.showToast({
            title: '发言不能为空！',
            duration: 1000,
            icon: 'none'
          })
        }

        // Format current date and time
        var date = new Date()
        var year = date.getFullYear()
        var month = date.getMonth() + 1
        var day = date.getDate()
        var hour = date.getHours() < 10 ? "0" + date.getHours() : date.getHours()
        var minute = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes()

        // Ensure month and day are two digits
        month = month >= 1 && month <= 9 ? "0" + month : month
        day = day >= 0 && day <= 9 ? "0" + day : day

        var timer = year + '-' + month + '-' + day + ' ' + hour + ':' + minute

        // Send data to server
        uni.request({
          header: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          url: 'https://database.ccjy16.top/WxTalkServlet',
          method: 'POST',
          data: {
            user_id: this.user.id,
            user_name: this.user.name,
            content: this.content,
            date: timer,
            sex: this.user.sex,
            head_pic: this.user.head_pic
          },
          dataType: 'json',
          success: (res) => {
            uni.showToast({
              title: '发送成功！',
              duration: 1000,
              icon: 'none'
            })

            // Redirect to chat page after sending
            setTimeout(() => {
              uni.switchTab({
                url: '/pages/chat/chat'
              })
            }, 1000)
          }
        })
      }
    }
  }
</script>

<style lang="scss">
  /* Textarea container */
  .shurukuang {
    top: 10rpx;
    width: 100%;
    padding: 10rpx;
    display: flex;
    flex-direction: column;

    /* Textarea style */
    .shurukuang-textarea {
      width: 92%;
      height: 320rpx;
      background-color: #f0f0f0;
      border-radius: 10rpx;
      padding: 20rpx;
    }
  }

  /* Character count and send button container */
  .zishu {
    text-align: right;
    align-items: center;
    display: flex;
    padding: 20rpx;
    padding-top: 20rpx;

    /* Character count text style */
    .zishu-text {
      font-size: 24rpx;
      color: #646464;
    }

    /* Send button style */
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

      /* Button text style */
      .zishu-button-text {
        color: white;
        font-size: 15px;
        font-family: 黑体, STSong, serif;
      }
    }
  }
</style>
