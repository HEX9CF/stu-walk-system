<template>
  <view>
    <!-- Profile Section -->
    <view class="head" @click="judge()">
      <!-- Conditional rendering of the profile picture -->
      <image
        v-if="token == 1"
        :src="user.head_pic"
        mode="widthFix"
        class="head_hd"
      ></image>
      <image
        v-else
        src="../../static/头像 男孩.png"
        mode="widthFix"
        class="head_hd"
      ></image>

      <!-- Profile Text -->
      <view class="head_box">
        <text v-if="token == 1" class="head_box_text1">{{ user.name }}</text>
        <text v-else class="head_box_text1">还未登录哦</text>
        <text v-if="token == 1" class="head_box_text2">{{ user.introduction }}</text>
        <text v-else class="head_box_text2">还没有简介哦~</text>
      </view>
    </view>

    <!-- Body Section -->
    <view class="body" :style="{ height: 600 + 'px' }">
      <!-- My Messages -->
      <view class="body-geRenXinXi" @click="gotoMytalk">
        <view class="body-geRenXinXi-rightbox">
          <image
            src="../../static/留言.png"
            class="body_pic"
            mode="widthFix"
          ></image>
          <text class="body-geRenXinXi-text">我的留言</text>
        </view>
        <uni-icons
          type="arrowright"
          size="17"
        ></uni-icons>
      </view>

      <!-- My Replies -->
      <view class="body-geRenXinXi" @click="gotoMyReply">
        <view class="body-geRenXinXi-rightbox">
          <image
            src="../../static/消息.png"
            class="body_pic"
            mode="widthFix"
          ></image>
          <text class="body-geRenXinXi-text">我的回复</text>
        </view>
        <uni-icons
          type="arrowright"
          size="17"
        ></uni-icons>
      </view>

      <!-- Account Settings -->
      <view class="body-geRenXinXi" @click="gotoMyMessage">
        <view class="body-geRenXinXi-rightbox">
          <image
            src="../../static/设置.png"
            class="body_pic"
            mode="widthFix"
          ></image>
          <text class="body-geRenXinXi-text">账户设置</text>
        </view>
        <uni-icons
          type="arrowright"
          size="17"
        ></uni-icons>
      </view>
    </view>

    <!-- Popup for Avatar and Nickname -->
    <uni-popup ref="popup" type="bottom" border-radius="10px 10px 0 0">
      <view class="getnickbox">
        <view class="m1 mt30">获取您的头像、昵称</view>
        <view class="m2">展示个人信息</view>

        <!-- Avatar Selection -->
        <view class="xin1">
          <button
            type="default"
            class="flex_spacebetween buttoncss"
            open-type="chooseAvatar"
            @chooseavatar="chooseavatar"
          >
            <view class="flex_align_center toubox">
              <view class="t1">头像</view>
              <view v-if="head_pic == ''" class="avatarbox">
                <image
                  src="../../static/头像 男孩.png"
                  mode="widthFix"
                  class="avatarimg"
                ></image>
              </view>
              <image
                v-else
                :src="head_pic"
                class="avatarimg"
              ></image>
            </view>
            <uni-icons type="right" size="25" color="#999"></uni-icons>
          </button>
        </view>

        <!-- Nickname Input -->
        <view class="nick">
          <view class="n1">昵称:</view>
          <input
            type="nickname"
            v-model="nickname"
            placeholder="请输入昵称"
          />
        </view>

        <!-- WeChat Authorization Button -->
        <view class="confirm" @click="getUserInfo()">
          <text class="confirm-text">微信授权登录</text>
        </view>
      </view>
    </uni-popup>
  </view>
</template>

<script>
import { mapMutations, mapState } from 'vuex';

export default {
  computed: {
    // Mapping the Vuex state to the component's computed properties
    ...mapState('m_user', ['user', 'token'])
  },
  
  data() {
    return {
      windowHeight: 0,
      name: "还未登录哦",
      introduction: "还没有简介哦~",
      final_head_pic: "../../static/头像 男孩.png",
      sex: 1,
      getnickshow: false,
      wx_token: "",
      wx_user: {},
      nickname: "",
      head_pic: "",
      t_name: "nihao"
    };
  },
  
  methods: {
    // Mapping Vuex mutations
    ...mapMutations('m_user', ['loginToken', 'logoutToken']),

    /**
     * Navigate to '我的留言' page.
     * If the user is not logged in, show a toast message.
     */
    gotoMytalk() {
      if (this.token !== 1) {
        uni.showToast({
          title: '请先登录！',
          icon: 'none'
        });
        return;
      }
      uni.navigateTo({
        url: '/subpkg/mytalk/mytalk'
      });
    },

    /**
     * Navigate to '我的回复' page.
     * If the user is not logged in, show a toast message.
     */
    gotoMyReply() {
      if (this.token !== 1) {
        uni.showToast({
          title: '请先登录！',
          icon: 'none'
        });
        return;
      }
      uni.navigateTo({
        url: '/subpkg/myReply/myReply'
      });
    },

    /**
     * Navigate to '账户设置' page.
     * If the user is not logged in, show a toast message.
     */
    gotoMyMessage() {
      if (this.token !== 1) {
        uni.showToast({
          title: '请先登录！',
          icon: 'none'
        });
        return;
      }
      uni.navigateTo({
        url: '/subpkg/myMessage/myMessage'
      });
    },

    /**
     * Open the popup to choose avatar and nickname.
     */
    open() {
      this.$refs.popup.open('bottom');
    },

    /**
     * Handle the avatar selection.
     * Upload the selected avatar to the server.
     * @param {Object} e - Event object containing the selected avatar URL.
     */
    chooseavatar(e) {
      uni.uploadFile({
        url: "https://database.ccjy16.top/UploadAvatarServlet",
        filePath: e.detail.avatarUrl,
        name: 'file',
        formData: {
          is_wxhead: 1
        },
        success: (r) => {
          let imgData = JSON.parse(r.data);
          if (imgData.code === 0) {
            // Successfully uploaded, update the avatar
            this.head_pic = imgData.data.fullurl;
          } else {
            uni.showToast({
              title: '上传失败：' + imgData.msg,
              icon: 'none'
            });
          }
        },
        fail: (err) => {
          console.error('上传失败：', err);
          uni.showToast({
            title: '上传失败',
            icon: 'none'
          });
        }
      });
    },

    /**
     * Get user information and submit to the server for login.
     * Display modal for confirmation before submission.
     */
    async getUserInfo() {
      if (this.head_pic === "") {
        uni.showToast({
          title: '请选择头像',
          icon: 'none'
        });
        return;
      }
      if (this.nickname === "") {
        uni.showToast({
          title: '请编辑用户昵称或昵称不能使用特殊字符',
          icon: 'none'
        });
        return;
      }
      uni.showModal({
        title: '提示',
        content: '您确认授权登录吗？',
        showCancel: true,
        cancelText: '取消',
        cancelColor: '#000000',
        confirmText: '确定',
        confirmColor: '#ADD8E6',
        success: (res) => {
          if (res.confirm) {
            uni.request({
              header: {
                'Content-Type': 'application/x-www-form-urlencoded'
              },
              url: 'https://database.ccjy16.top/WxRegisterServlet', // Replace with your server URL
              method: 'POST',
              data: {
                token: this.wx_token,
                name: this.nickname,
                head_pic: this.head_pic
              },
              success: (res) => {
                uni.showToast({
                  title: "微信登录成功！",
                  icon: "none"
                });
                this.wx_user = res.data;
                this.loginToken(this.wx_user);
                this.$refs.popup.close();
                this.final_head_pic = this.head_pic;
                this.name = this.nickname;
              },
              fail: (err) => {
                console.error('服务器请求失败：', err);
              }
            });
          }
        }
      });
    },

    /**
     * Handle WeChat login.
     * Fetch the login token from the server.
     */
    async login() {
      return new Promise((resolve, reject) => {
        uni.login({
          provider: 'weixin',
          success: (loginRes) => {
            uni.request({
              header: {
                'Content-Type': 'application/json'
              },
              url: 'https://database.ccjy16.top/WxLoginServlet',
              method: 'POST',
              data: {
                code: loginRes.code
              },
              success: (res) => {
                if (res.data.token) {
                  this.wx_token = res.data.token;
                  resolve();
                } else {
                  reject(new Error('服务器未返回 token'));
                }
              },
              fail: (err) => {
                console.error('服务器请求失败：', err);
                reject(err);
              }
            });
          },
          fail: (err) => {
            console.error('登录失败：', err);
            reject(err);
          }
        });
      });
    },

    /**
     * Judge whether the user is logged in and authorize them if needed.
     */
    async judge() {
      if (this.token === 1) {
        return;
      }
      try {
        await this.login(); // Ensure login is complete
        uni.request({
          header: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          url: 'https://database.ccjy16.top/WxSeekOpenidServlet',
          method: 'POST',
          data: {
            token: this.wx_token
          },
          success: (res) => {
            this.wx_user = res.data;
            if (this.wx_user === null) {
              this.open();
            } else {
              uni.showModal({
                title: '提示',
                content: '您确认授权微信登录吗？',
                showCancel: true,
                cancelText: '取消',
                cancelColor: '#000000',
                confirmText: '确定',
                confirmColor: '#ADD8E6',
                success: (res) => {
                  if (res.confirm) {
                    uni.showToast({
                      title: "微信登录成功！",
                      icon: "none"
                    });
                    this.loginToken(this.wx_user);
                    this.final_head_pic = this.wx_user.head_pic;
                    this.name = this.wx_user.name;
                    this.introduction = this.wx_user.introduction;
                    this.sex = this.wx_user.sex;
                  }
                }
              });
            }
          },
          fail: (err) => {
            console.error('服务器请求失败：', err);
          }
        });
      } catch (err) {
        console.error('登录或请求失败：', err);
      }
    }
  },

  onLoad() {
    if (this.token === 1) {
      this.final_head_pic = this.user.head_pic;
      this.name = this.user.name;
      this.introduction = this.user.introduction;
      this.sex = this.user.sex;
    }
  }
};
</script>

<style lang="scss">
	page{
		background-color: #F8F8FA;
	}
	.head{
		display: flex;
		width: 89%;
		margin: auto;
		background-color: white;
		margin-top: 50rpx;
		height: 250rpx;
		border-radius: 15px;
		// box-shadow: 4px 5px 8px rgba(0, 0, 0, 0.2);
		align-items: center;
		
		.head_hd{
			width: 140rpx;
			margin-left: 40rpx;
			border-radius: 10px;
		}
		.head_box{ 
			display: flex;
			flex-direction: column;
			margin-left: 40rpx;
			.head_box_text1{
				font-family: "黑体", SimSun, "Arial", sans-serif;
				font-weight: bold;
				font-size: 22px;
				// color: #55493B;
			}
			.head_box_text2{
				margin-top: 15rpx;
				font-size: 11px;
				color: #696969;
			}
		}
	}
	.body{
		// position: absolute;
		margin: auto;
		margin-top: 50rpx;
		padding: 25rpx;
		padding-left: 55rpx;
		padding-right: 55rpx;
		padding-top: 40rpx;
		// z-index: 999;
		// border: 1px black solid;
		// left: 20px;
		border-radius: 15px;
		width: 89%;
		box-sizing: border-box;
		background-color: white;
		// box-shadow: 4px 5px 8px rgba(0, 0, 0, 0.2);
		.body-geRenXinXi{
			display: flex;
			justify-content: space-between;
			padding: 10rpx;
			border-bottom: 2px solid #F8F8FA;
			height: 80rpx;
			align-items: center;
			margin-bottom: 25rpx;
			// z-index: 999;
			.body-geRenXinXi-rightbox{
				display: flex;
				justify-content: center; 
				align-items: center; 
				.body-geRenXinXi-text{
					font-size: 18px;
					margin-left: 20rpx;
					// z-index: 999;
					// color: #55493B;
					// font-weight: bold;
					font-family: "黑体", SimSun, "Arial", sans-serif;
				}
				.body_pic{
					width: 60rpx;
					// z-index: 999; 
				}
			}
		}
	}
	
	.getnickbox{
		padding: 30rpx 60rpx;
		background-color: white;
		border-radius: 20px 20px 0 0;
		.logoimg{
			width:50rpx;
			height:50rpx;
		}
		.name{
			margin-left:20rpx;
			text{
				margin-left:10rpx;
			}
		}
		.m1{
			font-weight: 500;
		}
		.m2{
			padding-bottom: 30rpx;
			border-bottom: 1rpx solid #f2f2f2;
			color:#999;
			padding-top: 10rpx;
			font-size: 24rpx;
		}
		.xin1{
				border-bottom: 1rpx solid #f2f2f2;
				margin-bottom: 30rpx;
				.toubox{
					padding: 10rpx 0;
					display: flex;
					align-items: center;
					justify-content: center;
					.t1{
						font-size: 28rpx;
						margin-right: 30rpx;
					}
					.avatarbox{
						width: 100rpx;
						height: 100rpx;
						border-radius: 20rpx;
					}
					.avatarimg{
						width: 100rpx;
						height: 100rpx;
						border-radius: 20rpx;
					}
				}
		}
		.buttoncss{
			background-color: #fff;
			border-radius: 0px;
			border: 0rpx solid transparent;
			display: flex;
			align-items: center;
			justify-content: space-between;
		}
		button::after{
			    border: 0px solid rgba(0,0,0,.2);
		}
		.nick{
			padding-bottom: 30rpx;
			border-bottom:2rpx solid #f2f2f2;
			display: flex;
			align-items: center;
			padding-left: 14px;
			padding-right: 14px;
			.n1{
				font-size: 28rpx;
				margin-right: 30rpx;
			}
		}
		.wan{
			position: relative;
			height: 100rpx;
			box-shadow: 0rpx 0rpx 0rpx 0rpx;
		  margin-top: 30rpx;
		}
		
		.mt30{
			margin-top: 30rpx;
		}
	}
	.flex_align_center{
			display: flex;
			justify-content: center; /* 水平居中 */
			align-items: center; /* 垂直居中 */
			// border: solid red 1px;
	}
	.confirm{
		display: flex;
		justify-content: center; /* 水平居中 */
		align-items: center; /* 垂直居中 */
		background-color: #ADD8E6;
		width: 90%;
		border-radius: 30px;
		box-sizing: border-box;
		height: 100rpx;
		margin: auto;
		margin-top: 30rpx;
		.confirm-text{
			color: white;
		}
	}
</style>
