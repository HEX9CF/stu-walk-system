<template>
  <view>
    <!-- 评论列表 -->
    <view v-if="chat_list.length !== 0">
      <view class="chat_box" v-for="(item, i) in chat_list" :key="i">
        <view class="head">
          <image class="head_image" :src="user.head_pic" />
          <view class="name_date">
            <text class="head_name">{{ item.user_name }}</text>
            <text class="head_date">{{ item.date }}</text>
          </view>
        </view>
        <view class="content">
          {{ item.content }}
        </view>
        <view class="bottom_icon">
          <text class="icon_text">{{ item.count }} 评论</text>
          <view class="icon">
            <image
              src="../../static/comment.png"
              class="goto"
              @click="goto_such_talk(item.id)"
            />
            <image
              src="/static/delete.png"
              class="delete_icon"
              @click="delete_my_talk(item.id)"
            />
          </view>
        </view>
      </view>
    </view>

    <!-- 空评论提示 -->
    <view class="empty_cart" v-else>
      <image src="../../static/mytalk.png" class="empty_img" />
      <text class="tip_text">还没有发言哦~</text>
    </view>
  </view>
</template>

<script>
  import { $http } from "@escook/request-miniprogram";
  import { mapState } from "vuex";

  uni.$http = $http;
  $http.baseUrl = "https://database.ccjy16.top";

  // 请求拦截器
  $http.beforeRequest = function (options) {
    uni.showLoading({
      title: "数据加载中...",
    });
  };

  // 响应拦截器
  $http.afterRequest = function () {
    uni.hideLoading();
  };

  export default {
    data() {
      return {
        chat_list: [], // 评论列表
      };
    },

    computed: {
      ...mapState("m_user", ["user"]), // 获取用户信息
    },

    onLoad() {
      this.get_chat_list(); // 页面加载时获取评论列表
    },

    methods: {
      /**
       * 获取评论列表
       * @returns {Promise<void>}
       */
      async get_chat_list() {
        uni.request({
          header: {
            "Content-Type": "application/x-www-form-urlencoded",
          },
          url: "https://database.ccjy16.top/myTalkServlet",
          method: "POST",
          data: {
            id: this.user.id,
          },
          dataType: "json",
          success: (res) => {
            if (res.statusCode !== 200) {
              return uni.showToast({
                title: "数据请求失败！",
                duration: 1500,
                icon: "none",
              });
            }
            this.chat_list = res.data;
            this.chat_list.reverse();
          },
        });
      },

      /**
       * 删除评论
       * @param {number} id - 评论ID
       * @returns {Promise<void>}
       */
      async delete_my_talk(id) {
        return uni.showModal({
          title: "删除",
          content: "确认要删除这条发言吗？",
          success: async function (res) {
            if (res.confirm) {
              await $http.get(
                `/deleteMyTalkServlet?id=${id}`
              );
              this.chat_list = []; // 清空评论列表
              this.get_chat_list(); // 重新获取评论
            }
          }.bind(this),
        });
      },

      /**
       * 跳转到评论详情页面
       * @param {number} id - 评论ID
       */
      goto_such_talk(id) {
        uni.navigateTo({
          url: `/subpkg/suchtalk/suchtalk?id=${id}`,
        });
      },
    },
  };
</script>

<style lang="scss">
  .chat_box {
    display: flex;
    flex-direction: column;
    padding: 15rpx;
    margin-left: 10rpx;
    border-bottom: 4rpx solid rgb(247, 247, 247);
    position: relative;
    box-sizing: border-box;
  }

  .head {
    display: flex;
    align-items: center;

    .name_date {
      display: flex;
      flex-direction: column;
      margin-left: 15rpx;

      .head_name {
        font-size: 14px;
      }

      .head_date {
        font-size: 10px;
        color: gray;
        margin-top: 15rpx;
      }
    }

    .head_image {
      border-radius: 50%;
      width: 100rpx;
      height: 100rpx;
      border: 2rpx solid rgb(247, 247, 247);
      box-sizing: border-box;
    }
  }

  .content {
    margin-top: 20rpx;
    margin-bottom: 20rpx;
    font-size: 15px;
  }

  .empty_cart {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding-top: 300rpx;

    .empty_img {
      width: 300rpx;
      height: 300rpx;
    }

    .tip_text {
      font-size: 12px;
      color: gray;
      margin-top: 15rpx;
    }
  }

  .bottom_icon {
    display: flex;
    justify-content: space-between;
    padding-right: 30rpx;
    margin-top: 10rpx;
    margin-bottom: 10rpx;

    .icon_text {
      font-size: 13px;
      color: gray;
    }

    .icon {
      .delete_icon {
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
