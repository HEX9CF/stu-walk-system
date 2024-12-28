<template>
  <view>
    <!-- Text Box Container -->
    <view class="textBox">
      <!-- Title Box -->
      <view class="titleBox">
        <text class="title">汕头大学东海岸校区</text>
      </view>

      <!-- First Section Text -->
      <text class="first" style="text-indent:30px;">{{ first }}</text>

      <!-- Image Swiper -->
      <swiper :indicator-dots="true" :autoplay="true" :interval="3000" :duration="1000" class="textBox_swiper1">
        <swiper-item v-for="(item, i) in swiperpic" :key="i">
          <image :src="item" @click="preview1(i)" mode="widthFix" class="textBox_swiper1_pic"></image>
        </swiper-item>
      </swiper>

      <!-- Second Section Text -->
      <text class="second" style="text-indent:30px;">{{ second }}</text>

      <!-- Head Image -->
      <view class="head_pic">
        <image
          src="http://mqn.ccjy16.top/%E8%BD%AF%E4%BB%B6%E7%AE%97%E6%B3%95%E7%BB%BC%E5%90%88%E8%AE%BE%E8%AE%A1/%E4%B8%9C%E6%B5%B7%E5%B2%B8%E6%A0%A1%E5%8C%BA%E5%B9%B3%E9%9D%A2%E5%9B%BE.png"
          @click="preview"
          mode="widthFix"
        ></image>
      </view>

      <!-- Third Section Text -->
      <text class="third" style="text-indent: 30px;">{{ third }}</text>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      // Text content for the sections
      first: `2018年12月，汕头市委明确在华侨试验区规划建设汕头大学东校区，项目选址于东海岸新城塔岗围片区东北角（翠峰路东侧）。2019年7月，在省市各级领导的关怀指导和大力支持下，汕头大学东校区正式启动建设。2022年7月，汕头大学东校区正式命名为“汕头大学东海岸校区”。2022年10月1日，汕头大学东海岸校区正式启用。2022年10月7日，汕头大学2022级4500余名新生首批入驻校区。2023年9月，商学院、法学院、长江新闻与传播学院、马克思主义学院、数学与计算机学院等五个学院整建制搬迁入驻校区，目前校区师生规模达9000余人。`,
      second: `东海岸校区建设项目占地共约1571亩，其中一期体育场馆区、二期宿舍区、三期教学实验行政区共占地约1088亩，可满足1万人教学实验及科研、办公需求；另有扩容新增四期发展建设用地面积约483亩，建成后可再满足1万人办学设施需求。目前，二期宿舍区及三期教学实验行政区已交付汕头大学办学使用，一期体育场馆区全力保障汕头大学体育教学需求并更好满足师生文体活动需要。`,
      third: `根据学校发展定位，东海岸校区以交叉学科、新兴工科和应用型人文社科为主体，直接对接区域经济社会发展需求，致力于推动学校同城多校区协同高质量发展，助力高水平大学建设。`,
      
      // List of image URLs for the swiper
      swiperpic: [
        "http://mqn.ccjy16.top/%E8%BD%AF%E4%BB%B6%E7%AE%97%E6%B3%95%E7%BB%BC%E5%90%88%E8%AE%BE%E8%AE%A1/%E6%A0%A1%E5%8C%BA%E5%9B%BE1.png",
        "http://mqn.ccjy16.top/%E8%BD%AF%E4%BB%B6%E7%AE%97%E6%B3%95%E7%BB%BC%E5%90%88%E8%AE%BE%E8%AE%A1/%E6%A0%A1%E5%8C%BA%E5%9B%BE2.jpg",
        "http://mqn.ccjy16.top/%E8%BD%AF%E4%BB%B6%E7%AE%97%E6%B3%95%E7%BB%BC%E5%90%88%E8%AE%BE%E8%AE%A1/%E6%A0%A1%E5%8C%BA%E5%9B%BE3.jpg"
      ]
    };
  },
  methods: {
    /**
     * Preview the head image when clicked
     */
    preview() {
      const imgsArray = [
        "http://mqn.ccjy16.top/%E8%BD%AF%E4%BB%B6%E7%AE%97%E6%B3%95%E7%BB%BC%E5%90%88%E8%AE%BE%E8%AE%A1/%E4%B8%9C%E6%B5%B7%E5%B2%B8%E6%A0%A1%E5%8C%BA%E5%B9%B3%E9%9D%A2%E5%9B%BE.png"
      ];
      uni.previewImage({
        current: 0,
        urls: imgsArray
      });
    },

    /**
     * Preview an image from the swiper when clicked
     * @param {number} i - The index of the clicked image
     */
    preview1(i) {
      uni.previewImage({
        current: 0,
        urls: this.swiperpic
      });
    }
  }
};
</script>

<style lang="scss">
/* Text box styling */
.textBox {
  display: flex;
  flex-direction: column;
  background-color: white;
  width: 80%;
  margin: auto;
  padding: 40rpx;
  border-radius: 40rpx;
  padding-top: 40rpx;
  border: 10rpx double #026B7A;
  padding-bottom: 40rpx;
  margin-top: 20rpx;
  margin-bottom: 20rpx;

  .titleBox {
    text-align: center;
    margin-bottom: 30rpx;

    .title {
      color: #c75f00;
      font-size: large;
      font-weight: bold;
    }
  }

  .first,
  .second,
  .third {
    color: #026b7a;
    font-family: 华文宋体, STSong, serif;
    text-align: left;
    font-weight: bold;
    line-height: 55rpx;
    margin-bottom: 20rpx;
  }

  .textBox_swiper1 {
    height: 330rpx;

    .textBox_swiper1_pic {
      width: 100%;
      height: 100%;
    }
  }

  .head_pic {
    image {
      width: 100%;
      height: 100%;
    }
  }
}

/* Page background color */
page {
  background-color: rgb(247, 247, 247);
}
</style>
