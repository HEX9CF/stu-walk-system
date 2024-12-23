<template>
  <view>
    <view v-if="chatList.length !== 0">
      <view class="chat-box" v-for="(item, index) in chatList" :key="index">
        <view class="head">
          <image class="head-image" :src="user.head_pic" />
          <view class="name-date">
            <text class="head-name">{{ item.user_name }} -> {{ item.comment_name }}</text>
            <text class="head-date">{{ item.date }}</text>
          </view>
        </view>
        <view class="content">{{ item.content }}</view>
        <view class="bottom-icon">
          <text class="icon-text">&ensp;</text>
          <view class="icon">
            <image
              src="../../static/comment.png"
              class="goto"
              @click="gotoSuchTalk(item.comment_id)"
            />
            <image
              src="/static/delete.png"
              class="delete-icon"
              @click="deleteMyTalk(item.unique_id, item.comment_id)"
            />
          </view>
        </view>
      </view>
    </view>
    <view class="empty-cart" v-else>
      <image src="../../static/mytalk.png" class="empty-img" />
      <text class="tip-text">还没有回复别人哦~</text>
    </view>
  </view>
</template>

<script>
import { $http } from '@escook/request-miniprogram';
import { mapState } from 'vuex';

uni.$http = $http;
$http.baseUrl = 'https://database.ccjy16.top';

// 请求拦截器
$http.beforeRequest = function () {
  uni.showLoading({
    title: '数据加载中...',
  });
};

// 响应拦截器
$http.afterRequest = function () {
  uni.hideLoading();
};

export default {
  data() {
    return {
      chatList: [],
    };
  },
  computed: {
    ...mapState('m_user', ['user']),
  },
  onLoad() {
    this.getChatList();
  },
  methods: {
    /**
     * 获取聊天列表
     */
    async getChatList() {
      uni.request({
        header: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        url: 'https://database.ccjy16.top/selectOwnReplyServlet',
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
            });
          }
          this.chatList = res.data;
          this.chatList.reverse();
        },
      });
    },

    /**
     * 删除自己的回复
     * @param {string} unique_id - 唯一标识
     * @param {string} comment_id - 评论 ID
     */
    async deleteMyTalk(unique_id, comment_id) {
      return uni.showModal({
        title: '删除',
        content: '确认要删除这条发言吗？',
        success: async (res) => {
          if (res.confirm) {
            await $http.get(
              `/deleteOwnReplyServlet?unique_id=${unique_id}&comment_id=${comment_id}`
            );
            this.chatList = [];
            this.getChatList();
          }
        },
      });
    },

    /**
     * 跳转到指定的评论页面
     * @param {string} id - 评论 ID
     */
    gotoSuchTalk(id) {
      uni.navigateTo({
        url: `/subpkg/suchTalk/suchTalk?id=${id}`,
      });
    },
  },
};
</script>

<style lang="scss">
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

      .head-name {
        font-size: 14px;
      }

      .head-date {
        font-size: 10px;
        color: gray;
        margin-top: 15rpx;
      }
    }

    .head-image {
      border-radius: 50%;
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
}

.empty-cart {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-top: 300rpx;

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

.bottom-icon {
  display: flex;
  justify-content: space-between;
  padding-right: 30rpx;
  margin-top: 10rpx;
  margin-bottom: 10rpx;

  .icon-text {
    font-size: 13px;
    color: gray;
  }

  .icon {
    .delete-icon {
      width: 32rpx;
      height: 32rpx;
    }

    .goto {
      width: 32rpx;
      height: 32rpx;
      margin-right: 50rpx;
    }
  }
}
</style>
