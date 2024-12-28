<template>
	<view class="login-container">
		<!-- <uni-icons type="contact-filled" size="100" color="#afafaf"></uni-icons> -->
		<image src="../../../../static/头像.png" mode="widthFix" class="head_pic"></image>
		<!-- <button type="primary" class="btn-login" open-type="getUserInfo" @getuserinfo="getUserInfo">一键登录</button> -->
		<view class="login_border">
			<input class="input_username" type="text" placeholder="请输入用户名" name="name" @input="inputUsername">
			<input class="input_password" type="password" placeholder="请输入密码" name="name" @input="inputPassword"/>
		</view>
		<button type="primary" class="btn-login" @click="t_login()">一键登录</button>
		<view class="tips-text" @click="switchRegister">没有账号？点此注册</view>
	</view>
</template>
<script>
	import { $http } from '@escook/request-miniprogram'
	import {mapMutations} from 'vuex'
	uni.$http = $http
	$http.baseUrl = 'https://database.ccjy16.top'
	export default{
		name: 'login',
		data(){
			return {
				username: "",
				password: "",
				user:{}
			};
		},
		methods: {
			...mapMutations('m_user', ['loginToken', 'logoutToken']),
			// getUserInfo(e){
			// 	console.log(e)
			// },
			// wxlogin(){
			// 	uni.login({
			// 	  provider: 'wechat',
			// 	  success: function (res) {
			// 	    console.log('微信登录成功', res)
			// 	  },
			// 	  fail: function (err) {
			// 	    console.log('微信登录失败', err)
			// 	  }
			// 	})

			// },
			inputUsername(e){
				clearTimeout(this.timer)
				this.timer = setTimeout(() => {
					this.username = e.detail.value
				}, 200)
			},
			inputPassword(e){
				clearTimeout(this.timer)
				this.timer = setTimeout(() => {
					this.password = e.detail.value
				}, 200)
			},
			switchRegister(){
				uni.navigateTo({
					url:"/subpkg/register/register"
				}) 
			},
			async t_login(){
				uni.login({
				  provider: 'weixin',
				  success: function (loginRes) {
				    console.log('登录code:', loginRes.code);
				    // 将loginRes.code发送到服务器
				    uni.request({
					  header: {
					 	      'Content-Type': 'application/json'  
					 	   }, 
				      url: 'https://database.ccjy16.top/loginServlet', // 替换为您的服务器地址
				      method: 'POST',
				      data: {
				        code: loginRes.code
				      },
				      success: function (res) {
				        // 处理服务器返回的数据，例如存储token
				        uni.setStorageSync('t_token', res.data.token);
						console.log(res.data.token)
				      },
				      fail: function (err) {
				        console.error('服务器请求失败：', err);
				      }
				    });
				  },
				  fail: function (err) {
				    console.error('登录失败：', err);
				  }
				});
				
				uni.getUserProfile({
				  desc: '用于完善会员资料',
				  success: function (infoRes) {
				    console.log('用户信息：', infoRes.userInfo);
				    // 将用户信息发送到服务器
				    // uni.request({
				    //   url: 'https://yourserver.com/userInfoServlet',
				    //   method: 'POST',
				    //   header: {
				    //     'Authorization': 'Bearer ' + uni.getStorageSync('t_token')
				    //   },
				    //   data: infoRes.userInfo,
				    //   success: function (res) {
				    //     console.log('用户信息已保存');
				    //   }
				    // });
				  },
				  fail: function (err) {
				    console.error('获取用户信息失败：', err);
				  }
				});

			},
			async login(){
				if(!this.username){
					return uni.showToast({
						title:'请输入用户名！',
						duration: 1000,
						icon:'none'
					})
				}else if(!this.password){
					return uni.showToast({
						title:'请输入密码！',
						duration: 1000,
						icon:'none'
					})
				}
                uni.request({
				    header: {
				     	      'Content-Type': 'application/x-www-form-urlencoded'  
				     	   }, 
				    url: 'https://database.ccjy16.top/usersServlet', 
				     method: 'POST',
				         data: {
				             username: this.username,
				             password: this.password
				         },
				     dataType:'json',
				     success: (res) => {
							if(res.statusCode !== 200){
								return uni.showToast({
									title:'数据请求失败！', 
									duration: 1500,
									icon: 'none' 
								})  
							}       
							this.user = res.data
							if(Object.keys(this.user).length === 0){
								return uni.showToast({
									title:'用户名或密码错误！',
									duration: 1500,
									icon:'none'
								})
							}else{
								this.loginToken(this.user)
							}
				         } 
				     }) 				 
			}
		}
	} 
</script>
<style lang="scss">
	.head_pic{
		width: 150rpx;
		margin-bottom: 35rpx;
	}
	.login-container{
		height: 1000rpx;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		background-color: #f8f8f8;
		position: relative;
		overflow: hidden;
		
		// &::after{
		// 	content: ' ';
		// 	display: block;
		// 	width: 100%;
		// 	height: 40px;
		// 	background-color: white;
		// 	position: absolute;
		// 	bottom: 0;
		// 	left: 0;
		// 	border-radius: 100%;
		// 	transform: translateY(50%);
		// }
		
		.btn-login{
			width: 90%;
			border-radius: 100px;
			margin: 15px 0;
			background-color: #ADD8E6;
		}
		.tips-text{
			font-size: 12px;
			color: gray;
		}
		
		.login_border{
			margin: 30rpx;
			.input_username{
				margin-bottom: 30rpx;
				border: gainsboro solid 1px;
				border-radius: 10rpx;
				width: 500rpx;
				padding: 10rpx;
			}
			.input_password{
				border: gainsboro solid 1px;
				border-radius: 10rpx;
				width: 500rpx;
				padding: 10rpx;
			}
		}
	}
</style>
