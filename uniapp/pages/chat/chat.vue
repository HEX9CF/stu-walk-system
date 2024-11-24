<template>
  <view>
    <image 
      src="../../static/talk.png" 
      class="talk" 
      @click="gotoTalk"
    />
    <my-search @click="searchBoxHandler" />
    <view class="cart-title">
      <uni-icons type="chatboxes" size="20" />
      <text class="cart-title-text">留言动态</text>
    </view>
    <view 
      class="chat-box" 
      v-for="(item, index) in chatList" 
      :key="index"
    >
      <view class="head">
        <image 
          v-if="item.sex === 1" 
          src="../../static/头像 男孩.png" 
          mode="widthFix" 
          class="head-image"
        />
        <image 
          v-else 
          src="../../static/头像 女孩.png" 
          mode="widthFix" 
          class="head-image"
        />
        <view class="name-date">
          <view class="name-sex">
            <text class="head-name">{{ item.user_name }}</text>
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
      <view class="content">
        {{ item.content }}
      </view>
      <view class="icon">
        <text class="icon-text">{{ item.count }}评论</text>
        <image 
          src="../../static/comment.png" 
          class="icon-image" 
          @click="gotoSuchTalk(item.id)"
        />
      </view>
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
      wh: 0,
      chatList: [],
      isLoading: false,
      pageNum: 0,
    };
  },
  computed: {
    ...mapState('m_user', ['user']),
  },
  onLoad() {
    this.getChatList();
    const sysInfo = uni.getSystemInfoSync();
    this.wh = sysInfo.windowHeight - 53;
  },
  onReachBottom() {
    this.pageNum += 1;
    this.getChatList();
  },
  onPullDownRefresh() {
    this.pageNum = 0;
    this.chatList = [];
    this.getChatList();
    setTimeout(() => {
      uni.stopPullDownRefresh();
    }, 1000);
  },
  onShow() {
    this.pageNum = 0;
    this.chatList = [];
    this.getChatList();
  },
  methods: {
    /**
     * 获取聊天列表
     */
    async getChatList() {
      this.isLoading = true;
      uni.request({
        header: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        url: 'https://database.ccjy16.top/chatListServlet',
        method: 'POST',
        data: {
          pageNum: this.pageNum,
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
          if (res.data.length === 0) {
            return uni.showToast({
              title: '已经到底了~',
              duration: 1000,
              icon: 'none',
            });
          }
          this.chatList = [...this.chatList, ...res.data];
        },
      });
      this.isLoading = false;
    },
    /**
     * 跳转到留言页面
     */
    gotoTalk() {
      uni.navigateTo({
        url: '/subpkg/talk/talk',
      });
    },
    /**
     * 跳转到具体留言页面
     * @param {number} id - 留言 ID
     */
    gotoSuchTalk(id) {
      uni.navigateTo({
        url: `/subpkg/suchtalk/suchtalk?id=${id}`,
      });
    },
    /**
     * 搜索框点击事件
     */
    searchBoxHandler() {
      uni.navigateTo({
        url: '/subpkg/search/search',
      });
    },
  },
};
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
