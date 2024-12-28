<template>
  <view>
    <!-- Chat list display -->
    <view v-if="chatList.length !== 0">
      <view 
        class="chatBox" 
        v-for="(item, i) in chatList" 
        :key="i"
      >
        <!-- Header with user image and name -->
        <view class="head">
          <image 
            class="head-image" 
            :src="user.head_pic"
          />
          <view class="name_date">
            <text class="head-name">
              {{ item.user_name }}
            </text>
            <text class="head-date">
              {{ item.date }}
            </text>
          </view>
        </view>
        <!-- Chat content -->
        <view class="content">
          {{ item.content }}
        </view>
        <!-- Bottom icon options -->
        <view class="bottomIcon">
          <text class="icon-text">
            {{ item.count }} 评论
          </text>
          <view class="Icon">
            <image 
              src="../../static/comment.png" 
              class="goto" 
              @click="gotoSuchTalk(item.id)"
            />
            <image 
              src="/static/delete.png" 
              class="deleteIcon" 
              @click="deleteMyTalk(item.id)"
            />
          </view>
        </view>
      </view>
    </view>

    <!-- Empty state when no chat exists -->
    <view class="empty-cart" v-else>
      <image 
        src="../../static/mytalk.png" 
        class="empty-img"
      />
      <text class="tip-text">还没有发言哦~</text>
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
    data() {
      return {
        chatList: [],
      }
    },
    computed: {
      ...mapState('m_user', ['user']),
    },
    onLoad() {
      this.getChatList()
    },
    methods: {
      // Get chat list from server
      async getChatList() {
        uni.request({
          header: {
            'Content-Type': 'application/x-www-form-urlencoded',
          },
          url: 'https://database.ccjy16.top/WxMyTalkServlet',
          method: 'POST',
          data: {
            id: this.user.id,
          },
          dataType: 'json',
          success: (res) => {
            if (res.statusCode !== 200) {
              return uni.showToast({
                title: '数据请求失败！',
                duration: 1500,
                icon: 'none',
              })
            }
            this.chatList = res.data
            this.chatList.reverse()
          },
        })
      },

      // Delete a specific chat message
      async deleteMyTalk(id) {
        return uni.showModal({
          title: '删除',
          content: '确认要删除这条发言吗？',
          success: async function (res) {
            if (res.confirm) {
              await $http.get('/WxDeleteMyTalkServlet?id=' + id)
              this.chatList = []
              this.getChatList()
            }
          }.bind(this),
        })
      },

      // Navigate to specific talk page
      gotoSuchTalk(id) {
        uni.navigateTo({
          url: '/subpkg/suchtalk/suchtalk?id=' + id,
        })
      },
    },
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
  }

  .head {
    display: flex;
    align-items: center;
  }

  .name_date {
    display: flex;
    flex-direction: column;
    margin-left: 15rpx;
  }

  .head-name {
    font-size: 14px;
  }

  .head-date {
    font-size: 10px;
    color: gray;
    margin-top: 15rpx;
  }

  .head-image {
    border-radius: 50px;
    width: 100rpx;
    height: 100rpx;
    border: solid 2px rgb(247, 247, 247);
    box-sizing: border-box;
  }

  .content {
    margin-top: 20rpx;
    margin-bottom: 20rpx;
    font-size: 15px;
  }

  .empty-cart {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding-top: 300rpx;
  }

  .empty-img {
    width: 300rpx;
    height: 300rpx;
  }

  .tip-text {
    font-size: 12px;
    color: gray;
    margin-top: 15px;
  }

  .bottomIcon {
    display: flex;
    justify-content: space-between;
    padding-right: 30rpx;
    margin-top: 10rpx;
    margin-bottom: 10rpx;
  }

  .icon-text {
    font-size: 13px;
    color: gray;
  }

  .Icon {
    .deleteIcon {
      width: 32rpx;
      height: 32rpx;
    }
    .goto {
      width: 32rpx;
      height: 32rpx;
      margin-right: 50rpx;
    }
  }
</style>
