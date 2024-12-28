<template>
	<view>
		<view class="head">
			<!-- <image src="../../../../static/头像 男孩.png" mode="widthFix" class="head_hd"></image> -->
			<image v-if="user.sex == 1" src="../../../../static/头像 男孩.png" mode="widthFix" class="head_hd"></image>
			<image v-else src="../../../../static/头像 女孩.png" mode="widthFix" class="head_hd"></image>
			<view class="head_box">
				<text class="head_box_text1">{{user.username}}</text>
				<text class="head_box_text2">{{user.introduction}}</text>
			</view>
		</view>
		<view class="body" :style="{height: 600 + 'px'}">
			<view class="body-geRenXinXi" @click="gotoMytalk">
				<view class="body-geRenXinXi-rightbox">
					<image src="../../../../static/留言.png" class="body_pic" mode="widthFix"></image>
					<text class="body-geRenXinXi-text">我的留言</text>
				</view>
				<uni-icons type="arrowright" size="17"></uni-icons>
			</view>
			<view class="body-geRenXinXi" @click="gotoMyReply">
				<view class="body-geRenXinXi-rightbox">
					<image src="../../../../static/消息.png" class="body_pic" mode="widthFix"></image>
					<text class="body-geRenXinXi-text">我的回复</text>
				</view>
				<uni-icons type="arrowright" size="17"></uni-icons>
			</view>
			<view class="body-geRenXinXi" @click="gotoMyMessage">
				<view class="body-geRenXinXi-rightbox">
					<image src="../../../../static/设置.png" class="body_pic" mode="widthFix"></image>
					<text class="body-geRenXinXi-text">账户设置</text>
				</view>
				<uni-icons type="arrowright" size="17"></uni-icons>
			</view>
		</view>
	</view>
</template>

<script>
	import {mapState} from 'vuex'
	export default {
		computed: {
			...mapState('m_user', ['token', 'user'])
		},
		data() {
			return {
				windowHeight: 0,
			};
		},
		methods:{ 
			gotoMytalk(){
				uni.navigateTo({
					url: '/subpkg/mytalk/mytalk'
				})
			},
			gotoMyReply(){
				uni.navigateTo({
					url: '/subpkg/myReply/myReply'
				})
			},
			gotoMyMessage(){
				uni.navigateTo({
					url: '/subpkg/myMessage/myMessage'
				})
			},
		},
		onLoad() {
			let t = uni.getSystemInfoSync();
			this.windowHeight = t.windowHeight;
		}
	}
</script>

<style lang="scss">
	page{
		background-color: #F8F8FA;
	}
	// .head_bk{
	// 	margin-top: 30rpx;
	// 	width: 96%;
	// 	height: 300rpx;
	// 	position: absolute;
	// }
	// .head_hd{
	// 	width: 160rpx;
	// 	position: absolute;
	// 	top: 110rpx;
	// 	left: 100rpx;
	// }
	// .head_text{
	// 	color: black;
		// font-family: "黑体", SimSun, "Arial", sans-serif;
		// font-weight: bold;
	// 	position: absolute;
	// 	font-size: 22px;
	// 	top: 130rpx;
	// 	left: 270rpx;
	// }
	// .head_hello{
	// 	color: black;
	// 	font-size: 11px;
	// 	position: absolute;
	// 	top: 200rpx;
	// 	left: 270rpx;
	// 	color: black;
	// }
	// .bottom_bk{
	// 	width: 96%;
	// 	position: absolute;
	// 	top: 240rpx;
	// 	border-radius: 40px;
	// }
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
		z-index: 999;
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
			z-index: 999;
			.body-geRenXinXi-rightbox{
				display: flex;
				justify-content: center; 
				align-items: center; 
				.body-geRenXinXi-text{
					font-size: 18px;
					margin-left: 20rpx;
					z-index: 999;
					// color: #55493B;
					// font-weight: bold;
					font-family: "黑体", SimSun, "Arial", sans-serif;
				}
				.body_pic{
					width: 60rpx;
					z-index: 999; 
				}
			}
		}
	}
</style>
