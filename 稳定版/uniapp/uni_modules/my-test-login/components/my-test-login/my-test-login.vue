<template>
	<view>
		<button @click="open">打开弹窗</button>
		<uni-popup ref="popup" type="bottom" border-radius="10px 10px 0 0">
			<view class="getnickbox">
<!-- 				<view class="flex_align_center">
					<image :src="project.logo" class="logoimg" mode="aspectFit"></image>
					<view class="name">
						{{project.name}}
						<text>申请</text>
					</view>
				</view> -->
				<view class="m1 mt30">获取你的头像、昵称</view>
				<view class="m2">展示个人信息</view>
				<view class="xin1">
					<button 
					type="default" class="flex_spacebetween buttoncss" open-type="chooseAvatar"  @chooseavatar="chooseavatar">
						<view class="flex_align_center toubox">
							<view class="t1">头像</view>
							<view v-if="avatar == ''" class="avatarbox">
								<!-- <uni-icons type="person" size="30" color="#999"></uni-icons> -->
							</view>
							<image v-else :src="avatar" class="avatarimg"></image>
						</view>
						<uni-icons type="right" size="25" color="#999"></uni-icons>
					</button>
				</view>
				<view class="nick">
					<view class="n1">昵称:</view>
					<input type="nickname" v-model="nickname" placeholder="请输入昵称">
				</view>
				<view class="fixedBtn wan">
					<button size="mini" open-type="getUserInfo"  @click="getUserInfo" :plain='true' class="btn">完成</button>
				</view>
			</view>
		</uni-popup>
	</view>
</template>
<script>
	export default { 
		data() {
			return {
				getnickshow:false,
				project:{
					name :'',
					logo :'',
				},
				avatar:''
			};
		}, 
		onShow() {
		// 获取项目的 logo 和 名称
			uni.getSystemInfo({
				success: (res) =>{
					this.project.name = res.appName
					console.log(res)
				}
			})
			this.project.logo = __wxConfig.accountInfo.icon
		},
		methods: {
			open(){
			        // 通过组件定义的ref调用uni-popup方法 ,如果传入参数 ，type 属性将失效 ，仅支持 ['top','left','bottom','right','center']
			    this.$refs.popup.open('bottom');
			},
			// 选择用户头像， 重点， 得调用下上传图片接口
			chooseavatar(e){
			    uni.uploadFile({
			        url: "https://database.ccjy16.top/UploadAvatarServlet",
			        filePath: e.detail.avatarUrl,
			        name: 'file',
			        formData: {
			            is_wxhead: 1
			        },
			        header: {
			            // 如果需要携带token，可以取消注释
			            // Authorization: uni.getStorageSync("token")
			        },
			        success: r => {
			            let imgData = JSON.parse(r.data)
			            console.log(imgData)
			            if (imgData.code === 0) {
			                // 直接使用返回的完整URL
			                this.avatar = imgData.data.fullurl;
			            } else {
			                uni.showToast({
			                    title: '上传失败：' + imgData.msg,
			                    icon: 'none'
			                });
			            }
			        },
			        fail: err => {
			            console.error('上传失败：', err);
			            uni.showToast({
			                title: '上传失败',
			                icon: 'none'
			            });
			        }
			    });
			},


			async getUserInfo(){
				let that = this;
				if(this.avatar == ""){
					uni.showToast({
						title: '请选择头像',
						icon: 'none'
					})
					return
				}
				if(this.nickname == ""){
					uni.showToast({
						title: '请编辑用户昵称或昵称不能使用特殊字符',
						icon: 'none'
					})
					return
				}
				that.rawData = {
					language: "zh_CN",
					nickName: that.nickname,
					avatarUrl: that.avatar
				}
				that.wxlogin();
			},

		}
	}
</script>
<style lang="scss">
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

</style>
