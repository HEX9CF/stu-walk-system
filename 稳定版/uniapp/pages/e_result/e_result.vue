<template>
  <view>
    <view class="resultBox">
      <view class="start">
        <text class="start_text">======起点======</text>
        <text class="start_text">{{ st }}</text>
      </view>

      <view 
        v-for="(item, i) in route" 
        :key="i" 
        class="resultBox_item" 
        @click="preview(item)">
        <view class="route_image_box">
          <image 
            :src="item" 
            class="route_image" 
            mode="widthFix" />
        </view>
        
        <view 
          v-if="i < route.length - 1" 
          class="resultBox_if">
          <image 
            src="../../static/长箭头.png" 
            class="jiantou" 
            mode="heightFix" />
        </view>
      </view>

      <view class="end">
        <text class="end_text">{{ ed }}</text>
        <text class="end_text">======终点======</text>
      </view>
    </view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        route: [
          // "https://database.ccjy16.top/data/floor1_path3090.png",
          // "https://database.ccjy16.top/data/floor5_path3090.png"
        ], 
        st: "",
        ed: ""
      };
    }, 
    
    onLoad(query) {
      let t = JSON.parse(query.data);
      for (let i = 0; i < t.length - 2; i++) {
        this.route.push(t[i]);
      }
      this.st = t[t.length - 2];
      this.ed = t[t.length - 1];
    },

    methods: {
      /**
       * Preview the image in full screen
       * @param {string} item - The image URL to preview
       */
      preview(item) {
        let imgsArray = [];
        imgsArray = [item];
        uni.previewImage({ 
          current: 0,
          urls: imgsArray
        });
      },
    },
  }
</script>

<style lang="scss">
  .headtext {
    position: absolute;
    top: 30rpx;
    left: 50rpx;
    font-weight: bold;
  }

  .route_image_box {
    border-radius: 20px;
    box-sizing: border-box;
    margin-top: 10rpx;
    padding-right: 40rpx;
    
    .route_image {
      border-radius: 20px;
      width: 100%;
      height: 100%;
    }
  }

  .resultBox {
    margin: auto;
    text-align: center;
    padding: 40rpx;
    border-radius: 40rpx;
    padding-top: 40rpx;
    border: 10rpx double #026B7A;
    padding-bottom: 40rpx;
    margin-top: 20rpx; 
    margin-bottom: 20rpx;
    
    .resultBox_item {
      .result_text {
        color: #c75f00;
        font-family: 华文宋体, STSong, serif;
        font-size: 20px;
      }
      
      .resultBox_if {
        .jiantou {
          height: 100rpx;
        }
      }
    }

    .start {
      margin-bottom: 10rpx;
      display: flex;
      flex-direction: column;
      justify-content: center; /* 水平居中 */
      align-items: center; /* 垂直居中 */
      
      .start_text {
        font-weight: bold;
        color: #FFCB00;
      }
    }

    .end {
      margin-top: 10rpx;
      display: flex;
      flex-direction: column;
      justify-content: center; /* 水平居中 */
      align-items: center; /* 垂直居中 */
      
      .end_text {
        font-weight: bold;
        color: #0F4FA8;
      }
    }

    .result_dist {
      margin-bottom: 20rpx;
      
      .result_dist_text {
        font-weight: bold;
      }
    }
  }
</style>
