<template>
  <view>
    <text class="head-text">路线图</text>
    <view class="route-image" @click="preview()">
      <image :src="r_pic" class="route-image" mode="widthFix">
    </view>
    <view class="result-box">
      <view class="result-dist">
        <text class="result-dist-text">全长{{r_dist}}米</text>
      </view>
      <view class="start-point">
        <text class="start-text">======起点======</text>
      </view>
      <view v-for="(item,i) in r_route" :key="i" class="result-box-item">
        <text class="result-text">{{points[item - 1].name}}</text>
        <view v-if="i < r_route.length - 1" class="resultBox_if">
          <image src="../../static/长箭头.png" class="jiantou" mode="heightFix">
        </view>
      </view>
      <view class="end">
        <text class="end_text">======终点======</text>
      </view>
    </view>
  </view>
</template>

<script>
/**
 * 页面功能说明：
 * 本页面用于展示路线图及其详细信息，包括图片预览、全长显示和路线点信息。
 */

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
    onLoad(query){
      this.route = JSON.parse(query.data);
      this.r_pic = this.route[0];
      this.r_dist = this.route[this.route.length - 1];
      for(let i = 1; i < this.route.length - 1; i++){
        this.r_route.push(this.route[i]);
      }
    },
    methods: {
      preview(){
        let imgsArray = [];
        imgsArray = [this.route[0]];
        uni.previewImage({ 
          current: 0,
          urls: imgsArray
        })
      },
    },
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
