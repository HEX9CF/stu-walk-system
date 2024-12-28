<template>
  <view>
    <view class="head_pic">
      <image 
        :src="floor_pic[floor_pic_index]" 
        @click="preview()" 
        mode="widthFix" />
    </view>
    
    <view class="headtext">
      <text class="headtext3">(请点击您想选择的地点)</text>

      <view 
        v-if="st_floor == 1" 
        class="headtext4_box">
        <text class="headtext4">现在正在选择起点楼层...</text>
      </view>

      <view 
        v-else-if="st_classroom == 1" 
        class="headtext4_box">
        <text class="headtext4">现在正在选择起点教室...</text>
      </view>

      <view 
        v-else-if="ed_floor == 1" 
        class="headtext4_box">
        <text class="headtext4">现在正在选择终点楼层...</text>
      </view>

      <view 
        v-else-if="ed_classroom == 1" 
        class="headtext4_box">
        <text class="headtext4">现在正在选择终点教室...</text>
      </view>

      <text class="headtext3">您的起点：{{ st_floor_text }}{{ st_classroom_text }}\n</text>
      <text class="headtext3">您的终点：{{ ed_floor_text }}{{ ed_classroom_text }}</text>
    </view>

    <text class="itemheadtext">楼层</text>
    
    <view class="nav_list">
      <view 
        class="nav_item" 
        v-for="(item, i) in navList1" 
        :key="i" 
        @click="floor_selected(item)">
        
        <view class="nav_item_h" style="position: relative;">
          <image 
            :src="item.icon" 
            class="nav_pic" 
            mode="heightFix" />
          <view class="nav_text">{{ item.name }}</view>
        </view>

        <view 
          v-if="item.select == 0" 
          class="nav_item_if" />
        
        <view 
          v-else-if="item.select == 1 && (st_classroom == 1 || ed_classroom == 1)" 
          class="nav_item_elseif" />
        
        <view 
          v-else 
          class="nav_item_else" />
      </view>
    </view>

    <view v-if="st_classroom == 1 || ed_classroom == 1">
      <text class="itemheadtext">教室</text>
      
      <view class="nav_list">
        <view 
          class="nav_item" 
          v-for="(item, i) in navList2" 
          :key="i" 
          @click="classroom_selected(item)">
          
          <view class="nav_item_h">
            <image 
              :src="item.icon" 
              class="nav_pic" 
              mode="heightFix" />
            <view class="nav_text">{{ item.name }}</view>
          </view>

          <view 
            v-if="item.select == 0" 
            class="nav_item_if" />
          
          <view 
            v-else 
            class="nav_item_else" />
        </view>
      </view>
    </view>

    <view class="zhanwei" />

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
            name: '1楼',
            icon: '../../static/1楼.png',
            select: 0
          },
          {
            id: 2,
            name: '2楼',
            icon: '../../static/2楼.png',
            select: 0
          },
          {
            id: 3,
            name: '3楼',
            icon: '../../static/3楼.png',
            select: 0
          },
          {
            id: 4,
            name: '4楼',
            icon: '../../static/4楼.png',
            select: 0
          },
          {
            id: 5,
            name: '5楼',
            icon: '../../static/5楼.png',
            select: 0
          }
        ],
        navList2: [],
        floor_pic: [
          'http://mqn.ccjy16.top/%E8%BD%AF%E4%BB%B6%E7%AE%97%E6%B3%95%E7%BB%BC%E5%90%88%E8%AE%BE%E8%AE%A1/flour1.png',
          'http://mqn.ccjy16.top/%E8%BD%AF%E4%BB%B6%E7%AE%97%E6%B3%95%E7%BB%BC%E5%90%88%E8%AE%BE%E8%AE%A1/flour2.png',
          'http://mqn.ccjy16.top/%E8%BD%AF%E4%BB%B6%E7%AE%97%E6%B3%95%E7%BB%BC%E5%90%88%E8%AE%BE%E8%AE%A1/flour3.png',
          'http://mqn.ccjy16.top/%E8%BD%AF%E4%BB%B6%E7%AE%97%E6%B3%95%E7%BB%BC%E5%90%88%E8%AE%BE%E8%AE%A1/flour4.png',
          'http://mqn.ccjy16.top/%E8%BD%AF%E4%BB%B6%E7%AE%97%E6%B3%95%E7%BB%BC%E5%90%88%E8%AE%BE%E8%AE%A1/flour5.png'
        ],
        floor_pic_index: 0,
        st_floor: 1,
        st_classroom: 0,
        ed_floor: 0,
        ed_classroom: 0,
        st_floor_text: '',
        st_classroom_text: '',
        ed_floor_text: '',
        ed_classroom_text: '',
        st_floor_select: 0,
        st_classroom_select: 0,
        ed_floor_select: 0,
        ed_classroom_select: 0,
        result: []
      }
    },
    onLoad() {
      const sysInfo = uni.getSystemInfoSync()
      this.wh = sysInfo.windowHeight + 10
      this.ww = sysInfo.windowWidth
    },
    methods: {
      /**
       * Search for the route between the selected start and end classrooms
       */
      async goSearch() {
        uni.showLoading({
          title: '正在计算路线',
          mask: true
        })

        let st = this.st_classroom_select
        let ed = this.ed_classroom_select

        // Adjust classroom selection
        this.adjustClassroomSelection('st_classroom_select')
        this.adjustClassroomSelection('ed_classroom_select')

        uni.request({
          header: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          url: 'https://database.ccjy16.top/E_walkSystemServlet',
          method: 'POST',
          data: {
            st: this.st_classroom_select,
            ed: this.ed_classroom_select
          },
          dataType: 'json',
          success: (res) => {
            if (res.statusCode !== 200) {
              return uni.showToast({
                title: '数据请求失败！',
                duration: 1500,
                icon: 'none'
              })
            }

            uni.hideLoading()
            this.result = res.data
            this.result.push(st)
            this.result.push(ed)
            uni.navigateTo({
              url: '/pages/Eresult/Eresult?data=' + JSON.stringify(this.result)
            })
          }
        })
      },

      /**
       * Adjust classroom selection based on ID
       * @param {string} selection - The selection string for start or end classroom
       */
      adjustClassroomSelection(selection) {
        let selectionValue = this[selection]
        if (selectionValue < 200) {
          this[selection] = selectionValue - 100
        } else if (selectionValue < 300) {
          this[selection] = selectionValue - 200 + 121
        } else if (selectionValue < 400) {
          this[selection] = selectionValue - 300 + 243
        } else if (selectionValue < 500) {
          this[selection] = selectionValue - 400 + 365
        } else {
          this[selection] = selectionValue - 500 + 462
        }
      },

      /**
       * Preview the selected floor image
       */
      preview() {
        let imgsArray = [this.floor_pic[this.floor_pic_index]]
        uni.previewImage({
          current: 0,
          urls: imgsArray
        })
      },

      /**
       * Select a classroom
       * @param {object} item - The classroom item
       */
      classroom_selected(item) {
        if (this.st_classroom === 1) {
          this.clearSelection(this.navList2)
          this.navList2[item.id - 1].select = 1
          this.st_classroom_select = item.name
          this.st_classroom_text = item.name
        }

        if (this.ed_classroom === 1) {
          this.clearSelection(this.navList2)
          this.navList2[item.id - 1].select = 1
          this.ed_classroom_select = item.name
          this.ed_classroom_text = item.name
        }
      },

      /**
       * Select a floor
       * @param {object} item - The floor item
       */
      floor_selected(item) {
        if (this.st_floor === 1) {
          this.clearSelection(this.navList1)
          this.navList1[item.id - 1].select = 1
          this.st_floor_select = item.id
          this.floor_pic_index = item.id - 1
          this.st_floor_text = item.name
        }

        if (this.ed_floor === 1) {
          this.clearSelection(this.navList1)
          this.navList1[item.id - 1].select = 1
          this.ed_floor_select = item.id
          this.floor_pic_index = item.id - 1
          this.ed_floor_text = item.name
        }
      },

      /**
       * Clear the selection state for all items in a list
       * @param {array} list - The list of items to clear
       */
      clearSelection(list) {
        list.forEach((item) => {
          item.select = 0
        })
      },

      /**
       * Confirm the selected start and end points
       */
      confirm() {
        if (this.st_floor === 1) {
          if (this.st_floor_select === 0) {
            uni.showToast({
              title: '请输入起点楼层！',
              duration: 1000,
              icon: 'none'
            })
            return
          }
          this.handleFloorSelection('st', this.st_floor_select)
        } else if (this.st_classroom === 1) {
          if (this.st_classroom_select === 0) {
            uni.showToast({
              title: '请输入起点教室！',
              duration: 1000,
              icon: 'none'
            })
            return
          }
          uni.showToast({
            title: '成功选择起点教室',
            duration: 1000,
            icon: 'none'
          })
          this.st_classroom = 0
          this.ed_floor = 1
        } else if (this.ed_floor === 1) {
          if (this.ed_floor_select === 0) {
            uni.showToast({
              title: '请输入终点楼层！',
              duration: 1000,
              icon: 'none'
            })
            return
          }
          this.handleFloorSelection('ed', this.ed_floor_select)
        } else if (this.ed_classroom === 1) {
          if (this.ed_classroom_select === 0) {
            uni.showToast({
              title: '请输入终点教室！',
              duration: 1000,
              icon: 'none'
            })
            return
          }
          this.showConfirmModal()
        }
      },

      /**
       * Handle the floor selection and generate classrooms
       * @param {string} type - Type of floor ('st' for start, 'ed' for end)
       * @param {number} floor - The selected floor
       */
      handleFloorSelection(type, floor) {
        let ed, icon
        if (floor === 1) {
          ed = 135
          icon = '../../static/1楼教室.png'
        } else if (floor === 2) {
          ed = 230
          icon = '../../static/2楼教室.png'
        } else if (floor === 3) {
          ed = 340
          icon = '../../static/3楼教室.png'
        } else if (floor === 4) {
          ed = 435
          icon = '../../static/4楼教室.png'
        } else {
          ed = 536
          icon = '../../static/5楼教室.png'
        }

        floor = floor * 100 + 1
        this.navList2 = []
        let t = 1
        for (let i = floor; i <= ed; i++, t++) {
          this.navList2.push({
            id: t,
            name: i,
            icon: icon,
            select: 0
          })
        }
        uni.showToast({
          title: `成功选择${type === 'st' ? '起点' : '终点'}楼层`,
          duration: 1000,
          icon: 'none'
        })
        this[`${type}_floor`] = 0
        this[`${type}_classroom`] = 1
      },

      /**
       * Show confirmation modal before proceeding
       */
      showConfirmModal() {
        uni.showModal({
          title: '提示',
          content: '您确认已经选择好路线了吗？',
          showCancel: true,
          cancelText: '取消',
          cancelColor: '#000000',
          confirmText: '确定',
          confirmColor: '#ADD8E6',
          success: (res) => {
            if (res.confirm) {
              this.goSearch()
            }
          }
        })
      },

      /**
       * Navigate back to previous step
       */
      goback() {
        if (this.st_classroom === 1) {
          this.st_classroom = 0
          this.st_floor = 1
        } else if (this.ed_floor === 1) {
          this.ed_floor = 0
          this.st_classroom = 1
        } else if (this.ed_classroom === 1) {
          this.ed_classroom = 0
          this.ed_floor = 1
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
			width: 20%;
			height: 180rpx;
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
