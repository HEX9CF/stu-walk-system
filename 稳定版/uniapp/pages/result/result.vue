<template>
  <view>
    <!-- Header Text -->
    <text class="headtext">路线图</text>

    <!-- Route Image with Click Event to Preview -->
    <view class="routeimage" @click="preview()">
      <image
        :src="r_pic"
        class="route_image"
        mode="widthFix"
      ></image>
    </view>

    <!-- Route Details Box -->
    <view class="resultBox">
      <!-- Route Distance -->
      <view class="result_dist">
        <text class="result_dist_text">全长{{ r_dist }}米</text>
      </view>

      <!-- Start Point Text -->
      <view class="start">
        <text class="start_text">======起点======</text>
      </view>

      <!-- Route Points Loop -->
      <view 
        v-for="(item, i) in r_route" 
        :key="i" 
        class="resultBox_item"
      >
        <!-- Display Route Point Name -->
        <text class="result_text">{{ points[item - 1].name }}</text>

        <!-- Arrow between Route Points -->
        <view v-if="i < r_route.length - 1" class="resultBox_if">
          <image 
            src="../../static/长箭头.png" 
            class="jiantou" 
            mode="heightFix"
          ></image>
        </view>
      </view>

      <!-- End Point Text -->
      <view class="end">
        <text class="end_text">======终点======</text>
      </view>
    </view>
  </view>
</template>

<script>
	export default {
		data() {
			return {
				route: [], 
				r_pic: "",
				r_route: [],
				r_dist: "",
				points: [
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
					},
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
					},
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
					},
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
					},
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
					},
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
			};
		}, 
		/**
		* onLoad lifecycle hook
		* This method runs when the page is loaded and processes the incoming query parameters.
		* 
		* @param {Object} query - The query parameters passed during navigation
		*/
		onLoad(query) {
			// Parse the route data passed in the query
			this.route = JSON.parse(query.data);
			
			// Set the route image and distance from the first and last elements
			this.r_pic = this.route[0];
			this.r_dist = this.route[this.route.length - 1];

			// Push the intermediate route points into the r_route array
			for (let i = 1; i < this.route.length - 1; i++) {
			this.r_route.push(this.route[i]);
			}
		},

		methods: {
			/**
			* Preview the route image when clicked
			* This method triggers the image preview functionality.
			*/
			preview() {
			let imgsArray = [this.route[0]]; // Only preview the first route image
			uni.previewImage({
				current: 0, // Set the first image as the current image
				urls: imgsArray // Pass the image array for preview
			});
			},
		}
	}
</script>

<style lang="scss">
	.headtext{
		position: absolute;
		top: 30rpx;
		left: 50rpx;
		font-weight: bold;
	}
	.routeimage{
		border: ridge 5px rgb(247, 247, 247);
		border-radius: 20px;
		box-sizing: border-box;
		margin-top: 10rpx;
		.route_image{
			border-radius: 20px;
			width: 100%;
			height: 100%;
		}
	}
	.resultBox{
		margin: auto;
		text-align: center;
		padding: 40rpx;
		border-radius: 40rpx;
		padding-top: 40rpx;
		border: 10rpx double #026B7A;
		padding-bottom: 40rpx;
		margin-top: 20rpx; 
		margin-bottom: 20rpx;
		.resultBox_item{
			.result_text{
				color: #c75f00;
				font-family: 华文宋体, STSong, serif;
				font-size: 20px;
			}
			.resultBox_if{
				.jiantou{
					height: 100rpx;
				}
			}
		}
		.start{
			margin-bottom: 10rpx;
			.start_text{
				font-weight: bold;
				color: #FFCB00;
			}
		}
		.end{
			margin-top: 10rpx;
			.end_text{
				font-weight: bold;
				color: #0F4FA8;
			}
		}
		.result_dist{
			margin-bottom: 20rpx;
			.result_dist_text{
				font-weight: bold;
			}
		}
	}
</style>
