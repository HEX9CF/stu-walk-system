<template>
  <view>
    <!-- Header Image with Click Event -->
    <view class="head_pic">
      <image 
        src="http://mqn.ccjy16.top/%E8%BD%AF%E4%BB%B6%E7%AE%97%E6%B3%95%E7%BB%BC%E5%90%88%E8%AE%BE%E8%AE%A1/%E4%B8%9C%E6%B5%B7%E5%B2%B8%E6%A0%A1%E5%8C%BA%E5%B9%B3%E9%9D%A2%E5%9B%BE.png" 
        @click="preview()" 
        mode="widthFix"
      ></image>
    </view>

    <!-- Instructions for Selecting Start or End Point -->
    <view class="headtext">
      <text class="headtext3">(请点击您想选择的地点)</text>

      <!-- Conditional Text Based on Selection State -->
      <view v-if="st == 1" class="headtext4_box">
        <text class="headtext4">现在正在选择起点...</text>
      </view>
      <view v-else-if="ed == 1" class="headtext4_box">
        <text class="headtext4">现在正在选择终点...</text>
      </view>
      <view v-else class="headtext4_box">
        <text class="headtext4">您想途经哪些地方呢？</text>
      </view>

      <!-- Display Selected Start and End Points -->
      <text class="headtext3">您的起点：{{ ac_st_text }}</text>
      <text class="headtext3">您的终点：{{ ac_ed_text }}</text>
    </view>

    <!-- Category Sections -->
    <text class="itemheadtext">图书馆与办公楼</text>
    <view class="nav_list">
      <view 
        class="nav_item" 
        v-for="(item, i) in navList1" 
        :key="i" 
        @click="selected(item)"
      >
        <view class="nav_item_h" style="position: relative;">
          <image :src="item.icon" class="nav_pic" mode="heightFix"></image>
          <view class="nav_text">{{ item.name }}</view>
        </view>

        <!-- Conditional Display Based on Item Selection State -->
        <view v-if="item.id == jst || item.id == jed" class="nav_item_elseif"></view>
        <view v-else-if="item.select == 0" class="nav_item_if"></view>
        <view v-else class="nav_item_else"></view>
      </view>
    </view>

    <text class="itemheadtext">学生宿舍</text>
    <view class="nav_list">
      <view 
        class="nav_item" 
        v-for="(item, i) in navList2" 
        :key="i" 
        @click="selected(item)"
      >
        <view class="nav_item_h">
          <image :src="item.icon" class="nav_pic" mode="heightFix"></image>
          <view class="nav_text">{{ item.name }}</view>
        </view>

        <!-- Conditional Display Based on Item Selection State -->
        <view v-if="item.id == jst || item.id == jed" class="nav_item_elseif"></view>
        <view v-else-if="item.select == 0" class="nav_item_if"></view>
        <view v-else class="nav_item_else"></view>
      </view>
    </view>

    <text class="itemheadtext">食堂</text>
    <view class="nav_list">
      <view 
        class="nav_item" 
        v-for="(item, i) in navList3" 
        :key="i" 
        @click="selected(item)"
      >
        <view class="nav_item_h">
          <image :src="item.icon" class="nav_pic" mode="heightFix"></image>
          <view class="nav_text">{{ item.name }}</view>
        </view>

        <!-- Conditional Display Based on Item Selection State -->
        <view v-if="item.id == jst || item.id == jed" class="nav_item_elseif"></view>
        <view v-else-if="item.select == 0" class="nav_item_if"></view>
        <view v-else class="nav_item_else"></view>
      </view>
    </view>

    <text class="itemheadtext">运动场所</text>
    <view class="nav_list">
      <view 
        class="nav_item" 
        v-for="(item, i) in navList4" 
        :key="i" 
        @click="selected(item)"
      >
        <view class="nav_item_h">
          <image :src="item.icon" class="nav_pic" mode="heightFix"></image>
          <view class="nav_text">{{ item.name }}</view>
        </view>

        <!-- Conditional Display Based on Item Selection State -->
        <view v-if="item.id == jst || item.id == jed" class="nav_item_elseif"></view>
        <view v-else-if="item.select == 0" class="nav_item_if"></view>
        <view v-else class="nav_item_else"></view>
      </view>
    </view>

    <text class="itemheadtext">校门</text>
    <view class="nav_list">
      <view 
        class="nav_item" 
        v-for="(item, i) in navList5" 
        :key="i" 
        @click="selected(item)"
      >
        <view class="nav_item_h">
          <image :src="item.icon" class="nav_pic" mode="heightFix"></image>
          <view class="nav_text">{{ item.name }}</view>
        </view>

        <!-- Conditional Display Based on Item Selection State -->
        <view v-if="item.id == jst || item.id == jed" class="nav_item_elseif"></view>
        <view v-else-if="item.select == 0" class="nav_item_if"></view>
        <view v-else class="nav_item_else"></view>
      </view>
    </view>

    <text class="itemheadtext">其它</text>
    <view class="nav_list">
      <view 
        class="nav_item" 
        v-for="(item, i) in navList6" 
        :key="i" 
        @click="selected(item)"
      >
        <view class="nav_item_h">
          <image :src="item.icon" class="nav_pic" mode="heightFix"></image>
          <view class="nav_text">{{ item.name }}</view>
        </view>

        <!-- Conditional Display Based on Item Selection State -->
        <view v-if="item.id == jst || item.id == jed" class="nav_item_elseif"></view>
        <view v-else-if="item.select == 0" class="nav_item_if"></view>
        <view v-else class="nav_item_else"></view>
      </view>
    </view>

    <!-- Spacer -->
    <view class="zhanwei"></view>

    <!-- Bottom Navigation Buttons -->
    <view class="bottombox">
      <view class="cancel" @click="goback()">
        <text class="cancel_text">回退</text>
      </view>
      <view class="confirm" @click="confirm()">
        <text class="confirm_text">确定</text>
      </view>
    </view>
  </view>
</template>

<script>
	import { $http } from '@escook/request-miniprogram'
	uni.$http = $http
	$http.baseUrl = 'https://database.ccjy16.top'
	export default {
		data() {
			return {
				ww: 0,
				wh: 0,
				navList1: [
					{
						id: 1,
						"name": "A栋图书馆",
						"icon": "../../static/A栋图书馆.png",
						select: 0
					},
					{
						id: 2,
						"name": "B栋综合楼",
						"icon": "../../static/B栋综合楼.png",
						select: 0
					},
					{
						id: 3,
						"name": "C栋行政楼",
						"icon": "../../static/C栋行政楼.png",
						select: 0
					},
					{
						id: 4,
						"name": "D栋实验楼",
						"icon": "../../static/D栋实验楼.png",
						select: 0
					},
					{
						id: 5,
						"name": "E栋教学楼",
						"icon": "../../static/E栋教学楼.png",
						select: 0
					}
				],
				navList2: [
					{
						id: 6,
						"name": "学生宿舍A座",
						"icon": "../../static/学生宿舍A座.png",
						select: 0
					},
					{
						id: 7,
						"name": "学生宿舍B座",
						"icon": "../../static/学生宿舍B座.png",
						select: 0
					},
					{
						id: 8,
						"name": "学生宿舍C座",
						"icon": "../../static/学生宿舍C座.png",
						select: 0
					},
					{
						id: 9,
						"name": "学生宿舍D座",
						"icon": "../../static/学生宿舍D座.png",
						select: 0
					},
					{
						id: 10,
						"name": "学生宿舍E座",
						"icon": "../../static/学生宿舍E座.png",
						select: 0
					},
					{
						id: 11,
						"name": "学生宿舍F座",
						"icon": "../../static/学生宿舍F座.png",
						select: 0
					},
					{
						id: 12,
						"name": "学生宿舍G座",
						"icon": "../../static/学生宿舍G座.png",
						select: 0
					},
					{
						id: 13,
						"name": "学生宿舍H座",
						"icon": "../../static/学生宿舍H座.png",
						select: 0
					},
					{
						id: 14,
						"name": "学生宿舍I座",
						"icon": "../../static/学生宿舍I座.png",
						select: 0
					},
					{
						id: 15,
						"name": "学生宿舍J座",
						"icon": "../../static/学生宿舍J座.png",
						select: 0
					}
				],
				navList3: [
					{
						id: 16,
						"name": "第一食堂",
						"icon": "../../static/第一食堂.png",
						select: 0
					},
					{
						id: 17,
						"name": "第二食堂",
						"icon": "../../static/第二食堂.png",
						select: 0
					},
					{
						id: 18,
						"name": "第三食堂",
						"icon": "../../static/第三食堂.png",
						select: 0
					}
				],
				navList4: [
					{
						id: 19,
						"name": "室外运动场1",
						"icon": "../../static/室外运动场1.png",
						select: 0
					},
					{
						id: 20,
						"name": "室外运动场2",
						"icon": "../../static/室外运动场2.png",
						select: 0
					},
					{
						id: 21,
						"name": "田径训练场",
						"icon": "../../static/田径训练场.png",
						select: 0
					},
					{
						id: 22,
						"name": "汕头体育中心",
						"icon": "../../static/汕头体育中心.png",
						select: 0
					}
				],
				navList5: [
					{
						id: 23,
						"name": "北门",
						"icon": "../../static/北门.png",
						select: 0
					},
					{
						id: 24,
						"name": "主校门",
						"icon": "../../static/主校门.png",
						select: 0
					},
					{
						id: 25,
						"name": "学海路大门",
						"icon": "../../static/学海路大门.png",
						select: 0
					}
				],
				navList6: [
					{
						id: 26,
						"name": "钟楼",
						"icon": "../../static/钟楼.png",
						select: 0
					},
					{
						id: 27,
						"name": "医务室",
						"icon": "../../static/医务室.png",
						select: 0
					},
					{
						id: 28,
						"name": "南广场",
						"icon": "../../static/南广场.png",
						select: 0
					},
					{
						id: 29,
						"name": "景观水池",
						"icon": "../../static/景观水池.png",
						select: 0
					},
					{
						id: 30,
						"name": "校友林",
						"icon": "../../static/校友林.png",
						select: 0
					}
				],
				st: 1,
				st_j: 0,
				ed: 0,
				all: 0,
				t: {},
				ac_st: 0,
				ac_st_text: "",
				ac_ed: 0,
				ac_ed_text: "",
				jst: 0,
				jed: 0,
				wantgo: [],
				result: [],
			}
		},
		// onLoad() {
		// 	const sysInfo = uni.getSystemInfoSync()
		// 	this.wh = sysInfo.windowHeight + 10;
		// 	this.ww = sysInfo.windowWidth;
		// }, 
		methods: {
			/**
			* Initiates the search for the route by sending a request to the server.
			* Displays loading indicator while waiting for the result.
			*/
			async goSearch() {
				uni.showLoading({
				title: '正在计算路线', // Loading message
				mask: true // Prevent touch interactions during loading
				});
				
				// Send POST request to get the route calculation
				uni.request({
				header: {
					'Content-Type': 'application/x-www-form-urlencoded'  
				}, 
				url: 'https://database.ccjy16.top/walkSystemServlet', 
				method: 'POST',
				data: {
					st: this.ac_st, // Start point
					ed: this.ac_ed, // End point
					wantgo: JSON.stringify(this.wantgo) // Intermediate points
				},
				dataType: 'json',
				success: (res) => {
					if (res.statusCode !== 200) {
					return uni.showToast({
						title: '数据请求失败！', 
						duration: 1500,
						icon: 'none' 
					});  
					}
					
					// Hide loading and store the result
					uni.hideLoading();
					this.result = res.data;
					
					// Navigate to the result page with the calculated route data
					uni.navigateTo({
					url: '/pages/result/result?data=' + JSON.stringify(this.result)
					});
				}
				}); 
			},

			/**
			* Previews the campus map when the image is clicked.
			*/
			preview() {
				let imgsArray = ["http://mqn.ccjy16.top/%E8%BD%AF%E4%BB%B6%E7%AE%97%E6%B3%95%E7%BB%BC%E5%90%88%E8%AE%BE%E8%AE%A1/%E4%B8%9C%E6%B5%B7%E5%B2%B8%E6%A0%A1%E5%8C%BA%E5%B9%B3%E9%9D%A2%E5%9B%BE.png"];
				uni.previewImage({ 
				current: 0, // Index of the image to be displayed first
				urls: imgsArray // List of images to preview
				});
			},

			/**
			* Handles the selection of a starting point (st) or ending point (ed).
			* Updates the state and the selected point.
			* @param {Object} item - The item selected from the navigation list.
			*/
			navSelect_sted(item) {
				if (item.id < 6) {  
				this.t = this.navList1[item.id - 1];
				} else if (item.id > 5 && item.id < 16) {
				this.t = this.navList2[item.id - 1 - 5];
				} else if (item.id > 15 && item.id < 19) {
				this.t = this.navList3[item.id - 1 - 15];
				} else if (item.id > 18 && item.id < 23) {
				this.t = this.navList4[item.id - 1 - 18];
				} else if (item.id > 22 && item.id < 26) {
				this.t = this.navList5[item.id - 1 - 22];
				} else if (item.id > 25 && item.id < 31) {
				this.t = this.navList6[item.id - 1 - 25];
				}
				this.t.select = 1; // Mark the item as selected
			},

			/**
			* Handles the selection of any point from the navigation list.
			* Adds/removes the item to/from the wantgo list.
			* @param {Object} item - The item selected from the navigation list.
			*/
			navSelect_all(item) {
				if (item.id == this.jst || item.id == this.jed) {
				return; // Do not allow selecting already selected start or end point
				}

				let list = [];
				if (item.id < 6) {
				list = this.navList1;
				} else if (item.id > 5 && item.id < 16) {
				list = this.navList2;
				} else if (item.id > 15 && item.id < 19) {
				list = this.navList3;
				} else if (item.id > 18 && item.id < 23) {
				list = this.navList4;
				} else if (item.id > 22 && item.id < 26) {
				list = this.navList5;
				} else if (item.id > 25 && item.id < 31) {
				list = this.navList6;
				}

				let selectedItem = list[item.id - 1 - (item.id < 6 ? 0 : (item.id < 16 ? 5 : (item.id < 19 ? 15 : (item.id < 23 ? 18 : (item.id < 26 ? 22 : 25)))))]
				if (selectedItem.select == 0) {
				selectedItem.select = 1;
				this.wantgo.push(item.id);
				} else {
				selectedItem.select = 0;
				this.wantgo = this.wantgo.filter(id => id !== item.id); // Remove the item from the selection list
				}
			},

			/**
			* Handles the selection of an item (either start, end, or intermediate points).
			* @param {Object} item - The item selected from the navigation list.
			*/
			selected(item) {
				if (this.st == 1) {
				if (this.st_j == 0) {
					this.navSelect_sted(item);
					this.st_j = 1; 
					this.ac_st = this.t.id;
					this.ac_st_text = this.t.name;
				} else if (this.st_j == 1) {
					this.t.select = 0;
					this.navSelect_sted(item);
					this.ac_st = this.t.id;
					this.ac_st_text = this.t.name;
				}
				}
				if (this.ed == 1) {  
				this.t.select = 0;
				this.navSelect_sted(item);
				this.ac_ed = this.t.id;
				this.ac_ed_text = this.t.name;
				}
				if (this.all == 1) {
				this.t.select = 0;
				this.navSelect_all(item);
				}
			},

			/**
			* Confirms the selection of start, end, and intermediate points and proceeds accordingly.
			*/
			confirm() {
				if (this.st == 1) {
				if (this.ac_st == 0) {
					uni.showToast({
					title: '请输入起点！',
					duration: 1000,
					icon: 'none'
					});
					return;
				}
				uni.showToast({
					title: '成功选择起点',
					duration: 1000,
					icon: 'none'
				});
				this.st = 0;
				this.ed = 1;
				} else if (this.ed == 1) {
				if (this.ac_ed == 0) {
					uni.showToast({
					title: '请输入终点！',
					duration: 1000,
					icon: 'none'
					});
					return;
				}
				uni.showToast({
					title: '成功选择终点',
					duration: 1000,
					icon: 'none'
				});
				this.ed = 0;
				this.jst = this.ac_st;
				this.jed = this.ac_ed;
				this.all = 1;
				} else if (this.all == 1) {
				uni.showModal({
					title: '提示',
					content: '您确认已经选择好地点了吗？',
					showCancel: true,
					cancelText: '取消',
					cancelColor: '#000000',
					confirmText: '确定',
					confirmColor: '#ADD8E6',
					success: (res) => {
					if (res.confirm) {
						this.goSearch();
					}
					}
				});
				}
			},

			/**
			* Handles going back to the previous step of the selection process.
			*/
			goback() {
				if (this.ed == 1) {
				this.ed = 0;
				this.st = 1;
				} else if (this.all == 1) {
				this.all = 0;
				this.ed = 1;
				this.jst = 0;
				this.jed = 0;
				this.wantgo.forEach(id => {
					if (id < 6) {
					this.navList1[id - 1].select = 0;
					} else if (id > 5 && id < 16) {
					this.navList2[id - 1 - 5].select = 0;
					} else if (id > 15 && id < 19) {
					this.navList3[id - 1 - 15].select = 0;
					} else if (id > 18 && id < 23) {
					this.navList4[id - 1 - 18].select = 0;
					} else if (id > 22 && id < 26) {
					this.navList5[id - 1 - 22].select = 0;
					} else if (id > 25 && id < 31) {
					this.navList6[id - 1 - 25].select = 0;
					}
				});
				this.wantgo = [];
				}
			}
		}
	}
</script>

<style lang="scss">
	.headtext{
		width: 100%;
		margin-bottom: 10rpx;
		border: #ADD8E6 10rpx double;
		box-sizing: border-box;
		border-radius: 10px;
		padding-top: 20rpx;
		padding-bottom: 20rpx;
		position: fixed;
		top: 0;
		background-color: white;
		z-index: 999;
		.headtext3{
			margin-left: 40rpx;
			font-family: 黑体, STSong, serif;
		}
		.headtext4_box{
			width: 100%;
			margin-top: 10rpx;
			margin-bottom: 10rpx;
			.headtext4{
				margin-left: 40rpx;
				font-family: 黑体, STSong, serif;
				font-weight: bold;
				font-size: 20px;
			}
		}
	}
	.headtext1{
		position: absolute;
		font-size: 16px;
		z-index: 999;
		top: 70rpx;
		left: 100rpx;
		color: black;
	}
	.headtext2{
		position: absolute;
		font-size: 15px;
		z-index: 999;
		top: 100rpx;
		left: 100rpx;
		color: black;
	}
	
	.nav_list{
		margin-top: 20rpx;
		margin-bottom: 20rpx;
		display: flex;
		flex-wrap: wrap;
		border-top: 1rpx solid #efefef;
		border-left: 1rpx solid #efefef;
		.nav_item{
			.nav_item_h{
				height: 100%;
				width: 100%;
				display: flex;
				flex-direction: column;
				align-items: center;
				justify-content: center;
			}
			.nav_item_if{
				height: 20rpx;
				width: 20rpx;
				border: 1px solid #ADD8E6;
				margin-bottom: 30rpx;
				border-radius: 100px;
			}
			.nav_item_elseif{
				height: 20rpx;
				width: 20rpx;
				margin-bottom: 30rpx;
				border-radius: 100px;
				background-color: #0D56A6;
				border: solid 1px #ADD8E6;
				display: flex;
				flex-direction: column;
				align-items: center;
				justify-content: center;
			}
			.nav_item_else{
				height: 20rpx;
				width: 20rpx;
				margin-bottom: 30rpx;
				border-radius: 100px;
				background-color: #ADD8E6;
				border: solid 1px #ADD8E6;
				display: flex;
				flex-direction: column;
				align-items: center;
				justify-content: center;
			}
			width: 33.3%;
			height: 200rpx;
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;
			border-right: 1rpx solid #efefef;
			border-bottom: 1rpx solid #efefef;
			box-sizing: border-box;
			.nav_pic{
				height: 60rpx;
			}
			.nav_text{
				font-size: 20rpx;
			}
		}
	}
	swiper{
		height: 420rpx;
		.swiper_pic{
			width: 100%;
			height: 100%;
			filter: blur(1px);
		}
	}
	.itemheadtext{
		margin-left: 40rpx;
		font-family: 黑体, STSong, serif;
	}
	.head_pic{
		margin-bottom: 20rpx;
		margin-top: 250rpx;
		image{
			width: 100%;
			height: 100%;
		}
	}
	.zhanwei{
		height: 110rpx;
	}
	.bottombox{
		position: fixed;
		bottom: 0;
		padding-bottom: 10rpx;
		padding-top: 10rpx;
		display: flex;
		height: 100rpx;
		width: 100%;
		justify-content: space-around;
		background-color: white;
		z-index: 999;
		.cancel{
			border: #ADD8E6 solid 1px;
			width: 48%;
			box-sizing: border-box;
			border-radius: 10px;
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;
		}
		.confirm{
			border: #ADD8E6 solid 1px;
			background-color: #ADD8E6;
			width: 48%;
			box-sizing: border-box;
			border-radius: 10px;
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;
		}
	}
</style>
