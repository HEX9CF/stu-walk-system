<template>
  <view>
    <!-- Gender selection section -->
    <view class="sex-box">
      <!-- Male selection -->
      <view 
        class="man" 
        @click="selectGender(1)"
      >
        <view class="box">
          <image 
            src="../../static/man.png" 
            class="pic" 
            mode="widthFix"
          ></image>
          <text class="text">男生</text>
        </view>
        <view 
          v-if="isMaleSelected === 1"
        >
          <image 
            src="../../static/true.png" 
            class="pic" 
          ></image>
        </view>
      </view>

      <!-- Female selection -->
      <view 
        class="female" 
        @click="selectGender(0)"
      >
        <view class="box">
          <image 
            src="../../static/female.png" 
            class="pic" 
            mode="widthFix"
          ></image>
          <text class="text">女生</text>
        </view>
        <view 
          v-if="isFemaleSelected === 1"
        >
          <image 
            src="../../static/true.png" 
            class="pic" 
          ></image>
        </view>
      </view>

      <!-- Unknown gender selection (commented out for now) -->
      <!-- <view 
        class="unknown" 
        @click="selectGender(-1)"
      >
        <view class="box">
          <image 
            src="../../static/unknown.png" 
            class="pic" 
            mode="widthFix"
          ></image>
          <text class="text">未知</text>
        </view>
        <view 
          v-if="isUnknownSelected === 1"
        >
          <image 
            src="../../static/true.png" 
            class="pic"
          ></image>
        </view>
      </view> -->
    </view>
  </view>
</template>

<script>
  import { $http } from '@escook/request-miniprogram'
  import { mapState, mapMutations } from 'vuex'

  uni.$http = $http
  $http.baseUrl = 'https://database.ccjy16.top'

  // Request interceptor
  $http.beforeRequest = function (options) {
    uni.showLoading({
      title: '数据加载中...',
    })
  }

  // Response interceptor
  $http.afterRequest = function () {
    uni.hideLoading()
  }

  export default {
    data() {
      return {
        isMaleSelected: 0,    // Track if Male is selected
        isFemaleSelected: 0,  // Track if Female is selected
        isUnknownSelected: 1  // Track if Unknown is selected (initial state)
      };
    },
    computed: {
      ...mapState('m_user', ['user']) // Map Vuex state to access user information
    },
    onLoad() {
      // Set initial gender based on user's existing selection
      if (this.user.sex === 1) {
        this.isMaleSelected = 0
        this.isUnknownSelected = 0
        this.isFemaleSelected = 1
      } else if (this.user.sex === 0) {
        this.isMaleSelected = 0
        this.isUnknownSelected = 0
        this.isFemaleSelected = 1
      } else {
        this.isMaleSelected = 0
        this.isFemaleSelected = 0
        this.isUnknownSelected = 1
      }
    },
    methods: {
      // Vuex mutation for changing the user's sex
      ...mapMutations('m_user', ['changeUserSex']),

      /**
       * Handles gender selection and updates the UI accordingly.
       * @param {number} index - The selected gender (1 for male, 0 for female, -1 for unknown)
       */
      selectGender(index) {
        // If the selected gender is already active, do nothing
        if (index === 1 && this.isMaleSelected === 1) return
        if (index === 0 && this.isFemaleSelected === 1) return
        if (index === -1 && this.isUnknownSelected === 1) return

        // Reset all gender selections
        this.isMaleSelected = 0
        this.isFemaleSelected = 0
        this.isUnknownSelected = 0

        // Activate the selected gender
        if (index === 1) {
          this.isMaleSelected = 1
        } else if (index === 0) {
          this.isFemaleSelected = 1
        } else {
          this.isUnknownSelected = 1
        }

        // Change the user's sex in the database
        this.updateUserSex(index)
      },

      /**
       * Sends a request to the server to update the user's sex.
       * @param {number} sex - The selected sex (1 for male, 0 for female, -1 for unknown)
       */
      async updateUserSex(sex) {
        await $http.get('/WxUserChangeSexServlet?id=' + this.user.id + '&sex=' + sex)
        this.changeUserSex(sex) // Update Vuex state with the new sex
      }
    }
  }
</script>

<style lang="scss">
  .sex-box {
    margin: 10rpx;
    border-radius: 10px;
    box-sizing: border-box;
    background-color: white;
    padding: 20rpx;
    margin-top: 20rpx;
  }

  .pic {
    width: 50rpx;
    height: 50rpx;
  }

  .text {
    margin-left: 15rpx;
  }

  .man, .female, .unknown {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 20rpx;
  }

  .female {
    border-top: solid 1px rgb(247, 247, 247);
    border-bottom: solid 1px rgb(247, 247, 247);
  }

  page {
    background-color: rgb(247, 247, 247);
  }
</style>
