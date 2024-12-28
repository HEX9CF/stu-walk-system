<template>
  <view>
    <image 
      src="../../static/talk.png" 
      class="talk" 
      @click="gotoTalk()" />
    <my-search 
      @click="searchBoxHandler" />
    <view class="cart-title">
      <uni-icons 
        type="chatboxes" 
        size="20" />
      <text class="cart-title-text">留言动态</text>
    </view>
    <view 
      class="chat-box" 
      v-for="(item, i) in chatList" 
      :key="i">
      <!-- <view class="chat-box-head"></view>
      <view class="chat-box-content"></view> -->
      <view class="head">
        <image 
          class="head-image" 
          :src="item.head_pic" />
        <view class="name-date">
          <view class="name-sex">
            <text class="head-name">{{ item.user_name }}</text>
            <view 
              v-if="item.sex === 1 || item.sex === -1" 
              class="pic">
              <image 
                src="../../static/sexMan.png" 
                style="height: 30rpx; width: 30rpx;" />
            </view>
            <view 
              v-else-if="item.sex === 0" 
              class="pic">
              <image 
                src="../../static/sexFemale.png" 
                style="height: 30rpx; width: 30rpx;" />
            </view>
          </view>
          <text class="head-date">{{ item.date }}</text>
        </view>
      </view> 
      <view class="content">
        {{ item.content }}
      </view>
      <view class="icon">
        <text class="icon-text">{{ item.count }}评论</text>
        <image 
          src="../../static/comment.png" 
          class="icon-image" 
          @click="gotoSuchTalk(item.id)" />
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
      title: 'Loading data...',
    })
  }

  // Response interceptor
  $http.afterRequest = function () {
    uni.hideLoading()
  }

  export default {
    data() {
      return {
        wh: 0,
        chatList: [],
        data: '',
        isLoading: false,
        pageNum: 0
      };
    },
    computed: {
      ...mapState('m_user', ['user'])
    },
    onLoad() {
      /**
       * Lifecycle hook for page loading
       * Initializes the window height and adjusts it for the top bar.
       */
      const sysInfo = uni.getSystemInfoSync();
      this.wh = sysInfo.windowHeight - 53;
    },
    onReachBottom() {
      /**
       * Lifecycle hook for reaching the bottom of the page.
       * It loads the next page of the chat list.
       */
      this.pageNum += 1
      this.getchatList()
    },
    onPullDownRefresh() {
      /**
       * Lifecycle hook for pull down refresh.
       * Resets the page and fetches the first page of the chat list.
       */
      this.pageNum = 0
      this.chatList = []
      this.getchatList()
      setTimeout(() => {
        // Stop pull down refresh
        uni.stopPullDownRefresh();
      }, 1000);
    },
    onShow() {
      /**
       * Lifecycle hook when the page is shown.
       * Resets the page number and chat list, and fetches the data.
       */
      this.pageNum = 0
      this.chatList = []
      this.getchatList()
    },
    methods: {
      async getchatList() {
        /**
         * Fetches the chat list from the server.
         * Sends a POST request to fetch the data for the current page number.
         */
        this.isLoading = true
        await uni.request({
          header: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          url: 'https://database.ccjy16.top/WxChatListServlet',
          method: 'POST',
          data: {
            pageNum: this.pageNum
          },
          dataType: 'json',
          success: (res) => {
            if (res.statusCode !== 200) {
              return uni.showToast({
                title: 'Request failed!',
                duration: 1500,
                icon: 'none'
              })
            }
            if (res.data.length === 0) {
              return uni.showToast({
                title: 'No more data~',
                duration: 1000,
                icon: 'none'
              })
            }
            this.chatList = [...this.chatList, ...res.data]
          }
        });
        this.isLoading = false
      },
      gotoTalk() {
        /**
         * Navigates to the Talk page.
         */
        uni.navigateTo({
          url: '/subpkg/talk/talk'
        })
      },
      refresh() {
        /**
         * Initiates a pull down refresh if not loading.
         */
        if (this.isLoading) return
        uni.startPullDownRefresh()
      },
      gotoSuchTalk(id) {
        /**
         * Navigates to the specific talk page based on the given ID.
         * @param {number} id - The ID of the specific talk.
         */
        uni.navigateTo({
          url: '/subpkg/suchtalk/suchtalk?id=' + id
        })
      },
      searchBoxHandler() {
        /**
         * Navigates to the search page.
         */
        uni.navigateTo({
          url: "/subpkg/search/search"
        })
      }
    }
  }
</script>

<style lang="scss">
  .cart-title {
    position: sticky;
    top: 0;
    z-index: 999;
    height: 50px;
    display: flex;
    align-items: center;
    font-size: 14px;
    padding-left: 5px;
    border-bottom: 1px solid #efefef;
    margin-top: 0;
    border-top: 2px solid #efefef;
    background-color: white;
    .cart-title-text {
      margin-left: 12px;
    }
  }

  .talk {
    width: 100rpx;
    height: 100rpx;
    position: fixed;
    z-index: 99999;
    bottom: 160rpx;
    right: 60rpx;
  }

  .chat-box {
    display: flex;
    flex-direction: column;
    padding: 15rpx;
    margin-left: 10rpx;
    border-bottom: rgb(247, 247, 247) solid 4rpx;
    position: relative;
    box-sizing: border-box;

    .head {
      display: flex;
      align-items: center;

      .name-date {
        display: flex;
        flex-direction: column;
        margin-left: 15rpx;

        .head-date {
          font-size: 10px;
          color: gray;
          margin-top: 15rpx;
        }

        .name-sex {
          display: flex;

          .head-name {
            font-size: 14px;
          }

          .pic {
            margin-left: 5rpx;
          }
        }
      }

      .head-image {
        border-radius: 50px;
        width: 100rpx;
        height: 100rpx;
        border: solid 2px rgb(247, 247, 247);
        box-sizing: border-box;
      }
    }

    .content {
      margin-top: 20rpx;
      margin-bottom: 20rpx;
      font-size: 15px;
    }

    .icon {
      display: flex;
      justify-content: space-between;
      padding-right: 30rpx;
      margin-top: 10rpx;
      margin-bottom: 10rpx;

      .icon-text {
        font-size: 13px;
        color: gray;
      }

      .icon-image {
        height: 45rpx;
        width: 45rpx;
      }
    }
  }
</style>
