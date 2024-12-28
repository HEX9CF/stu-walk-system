<template>
  <view>
    <image
      src="http://mqn.ccjy16.top/%E8%BD%AF%E4%BB%B6%E7%AE%97%E6%B3%95%E7%BB%BC%E5%90%88%E8%AE%BE%E8%AE%A1/%E6%A0%A1%E5%8C%BA5too%EF%BC%88%E6%9A%97%E4%BD%8E%EF%BC%89.png"
      mode="heightFix"
      class="background_pic"
      :style="{ height: wh + 90 + 'px' }"
    />
    <view class="headtext_box">
      <text class="headtext1">East Coast Campus</text>
      <text class="headtext2">————</text>
      <text class="headtext3">Shantou University Navigation Optimal Path Query System</text>
    </view>
    <view class="bottom_box">
      <view class="bottom_box_left">
        <image src="../../static/arrow.png" mode="widthFix" class="jiantou" />
        <view class="start" @click="gotoSearch(1)">
          <text class="start_text">East Campus Navigation</text>
        </view>
      </view>
      <view class="bottom_box_right">
        <image src="../../static/arrow.png" mode="widthFix" class="jiantou" />
        <view class="start" @click="gotoSearch(2)">
          <text class="start_text">Teaching Building Navigation</text>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      ww: 0,
      wh: 0,
    };
  },
  onLoad() {
    const sysInfo = uni.getSystemInfoSync();
    this.wh = sysInfo.windowHeight + 10;
    this.ww = sysInfo.windowWidth;
  },
  methods: {
    gotoSearch(n) {
      if (n === 1) {
        uni.navigateTo({
          url: "/pages/want/want",
        });
      } else if (n === 2) {
        uni.navigateTo({
          url: "/pages/Ewant/Ewant",
        });
      }
    },
  },
};
</script>

<style lang="scss">
.background_pic {
  position: absolute;
}
.headtext_box {
  width: 100%;
  position: fixed;
  top: 0;
  z-index: 999;
  padding-left: 90rpx;
  padding-top: 100rpx;
  .headtext1 {
    font-size: 16px;
    z-index: 999;
    color: rgb(247, 247, 247);
  }
  .headtext2 {
    font-size: 15px;
    z-index: 999;
    color: rgb(247, 247, 247);
  }
  .headtext3 {
    font-size: 35px;
    z-index: 999;
    color: rgb(247, 247, 247);
    font-weight: bold;
  }
}
.start {
  width: 100px;
  height: 100px;
  z-index: 999;
  border: solid rgb(247, 247, 247) 2px;
  border-radius: 100px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  text-align: center;
  .start_text {
    color: rgb(247, 247, 247);
  }
}
.bottom_box {
  position: fixed;
  bottom: 0;
  z-index: 999;
  display: flex;
  justify-content: center;
  align-items: center;
  box-sizing: border-box;
  width: 100%;
  height: 500rpx;
  padding-right: 20rpx;
  .jiantou {
    width: 60px;
    margin-bottom: 20rpx;
  }
  .start_text {
    color: rgb(247, 247, 247);
    z-index: 999;
  }
  .bottom_box_left {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    margin-right: 180rpx;
  }
  .bottom_box_right {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
}
</style>