<template>
  <view>
    <!-- Reply section: show textarea or default message -->
    <view 
      class="speak" 
      v-if="active === 0" 
      @click="changeActive"
    >
      <view class="speak-textarea">
        <text class="speak-textarea-text">请友善回复哦</text>
      </view>
      <image 
        src="../../static/commit.png" 
        class="speak-icon" 
        mode="widthFix" 
      />
    </view>

    <!-- Active state: textarea for input -->
    <view class="spoke" v-else>
      <view class="shurukuang">
        <textarea 
          class="shurukuang-textarea" 
          @blur="changeActive2" 
          auto-focus="true" 
          maxlength="150" 
          @input="inputContent" 
          placeholder="请友善回复哦" 
          cursor-spacing="300rpx"
        />
      </view>
      <view class="zishu">
        <text class="zishu-text">{{ start }}/150</text>
        <view 
          class="zishu-button" 
          @click="confirm"
        >
          <text class="zishu-button-text">发送</text>
        </view>
      </view>
    </view>

    <!-- Such chat details -->
    <view class="suchChatBox">
      <view class="head">
        <image 
          class="head-image" 
          :src="suchChat.head_pic" 
        />
        <view class="name_date">
          <view class="name_sex">
            <text class="head-name">{{ suchChat.user_name }}</text>
            <view 
              v-if="suchChat.sex === 1" 
              class="pic"
            >
              <image 
                src="../../static/sexMan.png" 
                style="height: 30rpx; width: 30rpx;" 
              />
            </view>
            <view 
              v-else-if="suchChat.sex === 0" 
              class="pic"
            >
              <image 
                src="../../static/sexFemale.png" 
                style="height: 30rpx; width: 30rpx;" 
              />
            </view>
          </view>
          <text class="head-date">{{ suchChat.date }}</text>
        </view>
      </view>
      <view class="content">
        {{ suchChat.content }}
      </view>
      <view class="icon">
        <text class="icon-text">{{ suchChat.count }} 评论</text>
        <image 
          src="../../static/comment.png" 
          class="icon-image" 
          @click="focusOn"
        />
      </view>
    </view>

    <!-- Second chat list -->
    <view 
      class="suchChatListBox" 
      v-if="suchChat.count !== 0"
    >
      <view 
        class="suchChatList" 
        v-for="(item, i) in suchChatList" 
        :key="i"
      >
        <view class="head">
          <image 
            class="head-image" 
            :src="item.head_pic" 
          />
          <view class="name_date">
            <text class="head-name">{{ item.user_name }}</text>
            <text class="head-date">{{ item.date }}</text>
          </view>
        </view>
        <view class="content">
          {{ item.content }}
        </view>
      </view>
    </view>

    <!-- Empty state when no replies -->
    <view 
      class="empty-cart" 
      v-if="suchChat.count === 0"
    > 
      <image 
        src="../../static/mytalk.png" 
        class="empty-img" 
      />
      <text class="tip-text">还没有回复哦~</text>
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
        suchChat: {},
        suchChatList: [],
        active: 0,
        start: 0,
        content: '',
        comment_id: 0,
      }
    },
    onLoad(e) {
      this.comment_id = e.id
      this.getSuchChat(e.id)
      this.getSecondChatList()
    },
    methods: {
      // Fetch chat details
      async getSuchChat(id) {
        await uni.request({
          header: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          url: 'https://database.ccjy16.top/WxGetSuchChatServlet', 
          method: 'POST',
          data: {
            id: id
          },
          success: (res) => {
            this.suchChat = res.data;
          },
          fail: (err) => {
            console.error('服务器请求失败：', err);
          }
        })
      },

      // Fetch second-level chat list
      async getSecondChatList() {
        await uni.request({
          header: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          url: 'https://database.ccjy16.top/WxSelectSecondChatListServlet',
          method: 'POST',
          data: {
            comment_id: this.comment_id
          },
          success: (res) => {
            this.suchChatList = res.data;
          },
          fail: (err) => {
            console.error('服务器请求失败：', err);
          }
        })
      },

      // Update content length as user types
      inputContent(e) {
        this.content = e.detail.value
        this.start = this.content.length
      },

      // Show the input area
      changeActive() {
        if (!this.token) {
          uni.switchTab({
            url: '/pages/usr/usr'
          })
        }
        this.active = 1
      },

      // Hide the input area if content is empty
      changeActive2() {
        if (this.content.length === 0) {
          this.active = 0 
        }
      },

      // Send the reply
      async confirm() {
        if (this.content.length === 0) {
          return uni.showToast({
            title: '发言不能为空！',
            duration: 1000,
            icon: 'none'
          })
        }
        const date = new Date()
        const year = date.getFullYear()
        let month = date.getMonth() + 1
        let day = date.getDate()
        let hour = date.getHours() < 10 ? "0" + date.getHours() : date.getHours()
        let minute = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes()

        month = month >= 1 && month <= 9 ? "0" + month : month
        day = day >= 0 && day <= 9 ? "0" + day : day

        const timer = `${year}-${month}-${day} ${hour}:${minute}`
        
        await uni.request({
          header: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          url: 'https://database.ccjy16.top/WxAddToSecondChatListServlet',
          method: 'POST',
          data: {
            id: this.user.id,
            comment_id: this.comment_id,
            user_name: this.user.name,
            comment_name: this.suchChat.user_name,
            date: timer,
            content: this.content,
            head_pic: this.user.head_pic
          },
          dataType: 'json',
          success: (res) => {
            uni.showToast({
              title: '发送成功！',
              duration: 1000,
              icon: 'none'
            })
            setTimeout(() => {
              this.suchChatList = []
              this.getSecondChatList()
              this.suchChat = {}
              this.getSuchChat(this.comment_id)
              this.start = 0
              this.active = 0 
              this.content = ''
            }, 1000)
          }
        })
      },

      // Focus on the input field
      focusOn() {
        this.active = 1
      }
    }
  }
</script>

<style lang="scss">
	.suchChatBox{
		display: flex;
		flex-direction: column;
		padding: 15rpx;
		margin-left: 10rpx;
		box-sizing: border-box;
		.head{
			display: flex;
			align-items: center;
			.name_date{
				display: flex;
				flex-direction: column;
				margin-left: 15rpx;
				.head-name{
					font-size: 14px;
				}
				.head-date{
					font-size: 10px;
					color: gray;
					margin-top: 15rpx;
				}
			}
			.head-image{
				border-radius: 50px;
				width:100rpx;
				height:100rpx;
				border: solid 2px rgb(247, 247, 247);
				box-sizing: border-box;
			}
		}
		.content{
			margin-top: 20rpx;
			margin-bottom: 20rpx;
			font-size: 15px;
		}
		.icon{
			display: flex;
			justify-content: space-between;
			padding-right: 30rpx;
			margin-top: 20rpx;
			margin-bottom: 10rpx;
			.icon-text{
				font-size: 13px;
				color: gray;
			}
			.icon-image{
				height: 45rpx;
				width: 45rpx;
			}
		}
	}
	.name_sex{
		display: flex;
		.head-name{
			font-size: 14px;
		}
		.pic{
			margin-left: 5rpx;
		}
	} 
	.suchChatListBox{
		display: flex;
		flex-direction: column;
		padding: 15rpx;
		margin-left: 10rpx;
		padding-bottom: 360rpx;
		.head{
			display: flex;
			align-items: center;
			margin-top: 25rpx;
			.name_date{
				display: flex;
				flex-direction: column;
				margin-left: 15rpx;
				.head-date{
					font-size: 10px;
					color: gray;
					margin-top: 15rpx;
				}
			}
			.head-image{
				border-radius: 50px;
				width:80rpx;
				height:80rpx;
				border: solid 2px rgb(247, 247, 247);
				box-sizing: border-box;
			}
		}
		.content{
			margin-top: 20rpx;
			margin-bottom: 20rpx;
			font-size: 15px;
			padding-left: 94rpx;
		}
	}
	.speak{
		position: fixed;
		border-top: solid 1px rgb(247, 247, 247);
		bottom: 0;
		z-index: 99;
		width: 100%;
		padding: 20rpx;
		background-color: white;
		height: 120rpx;
		box-sizing: border-box;
		display: flex;
		.speak-textarea{
			background-color: rgb(247, 247, 247);
			width: 100%;
			padding: 20rpx;
			border-radius: 16px;
			
		}
		.speak-icon{
			width: 80rpx;
			margin-left: 10rpx;
		}
	}
	.spoke{
		position: fixed;
		bottom: 0;
		width: 100%;
		background-color: white;
		padding: 10rpx;
		box-sizing: border-box;
		.shurukuang{
			width: 100%;
			padding: 10rpx;
			display: flex;
			flex-direction: column;
			.shurukuang-textarea{
				width: 92%;
				height: 200rpx;
				background-color: #f0f0f0;
				border-radius: 10rpx;
				padding: 20rpx;
			}
		}
		.zishu{
			text-align: right;
			align-items: center;
			display: flex;
			padding: 20rpx;
			padding-top: 20rpx;
			.zishu-text{
				font-size:24rpx;
				color: #646464;
			}
			.zishu-button{
				border-radius: 40rpx;
				background-color: #ADD8E6;
				display: flex;
				align-items: center;
				padding: 20rpx;
				padding-top: 10rpx;
				padding-bottom: 10rpx;
				position: absolute;
				right: 15rpx;
				.zishu-button-text{
					color: white;
					font-size: 15px;
					font-family: 黑体, STSong, serif;
				}
			}
		}
	}
	.empty-cart{
		display: flex;
		flex-direction: column;
		align-items: center;
		padding-top: 200rpx;
		.empty-img{
			width: 300rpx;
			height: 300rpx;
		}
		.tip-text{
			font-size: 12px;
			color: gray;
			margin-top: 15px;
		}
	}
</style>
