<template>
  <view>
    <!-- Chat list display -->
    <view class="chatBox" v-for="(item, i) in chatList" :key="i">
      <view class="head">
        <!-- Head image -->
        <image 
          :src="item.head_pic" 
          mode="widthFix" 
          class="head-image" 
        />
        
        <view class="name_date">
          <view class="name_sex">
            <text class="head-name">{{ item.user_name }}</text>
            <!-- Gender icons based on sex value -->
            <view 
              v-if="item.sex === 1 || item.sex === -1" 
              class="pic"
            >
              <image 
                src="../../static/sexMan.png" 
                style="height: 30rpx; width: 30rpx;" 
              />
            </view>
            <view 
              v-else-if="item.sex === 0" 
              class="pic"
            >
              <image 
                src="../../static/sexFemale.png" 
                style="height: 30rpx; width: 30rpx;" 
              />
            </view>
          </view>
          <text class="head-date">{{ item.date }}</text>
        </view>
      </view>

      <!-- Content -->
      <view class="content">
        {{ item.content }}
      </view>

      <!-- Icon section for comments -->
      <view class="icon">
        <text class="icon-text">{{ item.count }} 评论</text>
        <image 
          src="../../static/comment.png" 
          class="icon-image" 
          @click="gotoSuchTalk(item.id)" 
        />
      </view>
    </view>

    <!-- Empty state if no chat list -->
    <view 
      class="empty-cart" 
      v-if="chatList.length === 0"
    >
      <image 
        src="../../static/mytalk.png" 
        class="empty-img" 
      />
      <text class="tip-text">搜索不到有关信息...</text>
    </view>
  </view>
</template>

<script>
  import { $http } from '@escook/request-miniprogram'

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
    data() {
      return {
        kw: "",
        chatList: [],
      }
    },

    // Load data when page is opened
    onLoad(query) {
      this.kw = JSON.parse(query.data)
      this.getSearchList(this.kw)
    },

    methods: {
      // Get search results based on keyword
      async getSearchList(e) {
        if (e.length === 0) {
          this.chatList = []
          return
        }

        await uni.request({
          header: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          url: 'https://database.ccjy16.top/WxChatSearchServlet',
          method: 'POST',
          data: {
            index: e
          },
          dataType: 'json',
          success: (res) => {
            this.chatList = res.data
          }
        })
      },

      // Navigate to specific talk detail
      gotoSuchTalk(id) {
        uni.navigateTo({
          url: '/subpkg/suchtalk/suchtalk?id=' + id
        })
      }
    }
  }
</script>

<style lang="scss">
  .chatBox {
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

      .name_date {
        display: flex;
        flex-direction: column;
        margin-left: 15rpx;

        .head-date {
          font-size: 10px;
          color: gray;
          margin-top: 15rpx;
        }

        .name_sex {
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

  .empty-cart {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding-top: 400rpx;

    .empty-img {
      width: 300rpx;
      height: 300rpx;
    }

    .tip-text {
      font-size: 12px;
      color: gray;
      margin-top: 15px;
    }
  }
</style>
